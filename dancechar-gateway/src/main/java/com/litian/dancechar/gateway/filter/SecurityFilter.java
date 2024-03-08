package com.litian.dancechar.gateway.filter;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.context.BodyReaderRequestWrapper;
import com.litian.dancechar.framework.common.context.HttpConstants;
import com.litian.dancechar.framework.common.context.ReqGetBody;
import com.litian.dancechar.framework.common.context.RequestParamsUtil;
import com.litian.dancechar.framework.common.token.TokenHelper;
import com.litian.dancechar.framework.common.trace.Trace;
import com.litian.dancechar.framework.common.trace.TraceHelper;
import com.litian.dancechar.framework.common.trace.TraceHttpHeaderEnum;
import com.litian.dancechar.gateway.config.SwaggerProviderConfig;
import com.litian.dancechar.gateway.config.SystemConfig;
import com.litian.dancechar.gateway.feign.system.client.UserClient;
import com.litian.dancechar.gateway.feign.system.dto.SystemUserReqDTO;
import com.litian.dancechar.gateway.service.BlackListService;
import com.litian.dancechar.gateway.service.WhiteListService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.SortedMap;
import java.util.function.Consumer;

/**
 * 权限过滤器(traceId处理、黑白名单、jwt token鉴权)
 *
 * @author tojson
 * @date 2022/05/28 09:51
 */
@Slf4j
@Component
public class SecurityFilter implements GlobalFilter, Ordered {
    @Resource
    private SystemConfig gatewayConfigCenterConfig;
    @Resource
    private WhiteListService whiteListService;
    @Resource
    private BlackListService blackListService;

    @Resource
    private UserClient userClient;

    private AntPathMatcher pathMatcher = new AntPathMatcher();
    private static final String START_TIME = "startTime";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(START_TIME, System.currentTimeMillis());
        ServerWebExchange build = buildGlobalTraceId(exchange);
        ServerHttpRequest request = exchange.getRequest();
        String clientIp = getClientIp(request);
        String url = request.getURI().getPath();
        HttpHeaders httpHeaders = request.getHeaders();
        httpHeaders = HttpHeaders.writableHttpHeaders(httpHeaders);
        log.info("来自客户端的请求信息-{},method:{},ip:{}", url, request.getMethodValue(), clientIp);
        log.info("来自客户端的header信息:{}", JSONUtil.toJsonStr(httpHeaders));
        if (!gatewayConfigCenterConfig.getAuthEnable()) {
            log.info("系统不启用鉴权,所有请求直接放行.");
            return filter(chain, build, exchange, url);
        }
        if (checkForbiddenUrlsFromConfigCenter(url)) {
            log.error("配置中心禁止访问的url:{}", url);
            return responseErrMsg(exchange, 403, "forbidden request url!");
        }
        if (checkNotNeedAuthUrlsFromConfigCenter(url)) {
            log.info("配置中心不需要验证的url:{}", url);
            return filter(chain, build, exchange, url);
        }
        if (whiteListService.isWhiteList(url)) {
            log.info("白名单请求直接放行.");
            return filter(chain, build, exchange, url);
        }
        if (blackListService.isBlackList(url)) {
            log.error("黑名单请求url:{}", url);
            return responseErrMsg(exchange, 403, "forbidden request url!");
        }
        // 从header Authorization中获取
        String authorization = httpHeaders.getFirst(HttpConstants.Header.AUTHORIZATION);
        if (StrUtil.isEmpty(authorization)) {
            log.error("invalid token or not login! authorization:{}", authorization);
            return responseErrMsg(exchange, 401, "invalid token or not login");
        }
        // 截取bearer:后面的token
        String jwt = authorization.substring(HttpConstants.Header.JWT_LEASE_LENGTH);
        Map<String,String> verifyMap = TokenHelper.verify(jwt);
        if ("0".equals(verifyMap.get("isPass"))) {
            if("1".equals(verifyMap.get("expired"))){
                log.error("token expired! token:{}", jwt);
                return responseErrMsg(exchange, 200002, "token expired");
            }else {
                log.error("token invalid! token:{}", jwt);
                return responseErrMsg(exchange, 401, "token invalid");
            }
        }
        String mobile = TokenHelper.getMobile(jwt), userId = TokenHelper.getUserId(jwt);
        SystemUserReqDTO systemUserReqDTO = new SystemUserReqDTO();
        systemUserReqDTO.setId(userId);
        RespResult<Boolean> isBlackListR =  userClient.isBlackList(systemUserReqDTO);
        if(isBlackListR.isOk() && isBlackListR.getData()){
            log.error("userId 为黑名单用户！userId:{}", userId);
            return responseErrMsg(exchange, 401, "token invalid");
        }
        String userAccessInfo = httpHeaders.getFirst(HttpConstants.Header.USER_ACCESS_INFO);
        setHeaderInfo(build, request, mobile, userId, clientIp, userAccessInfo, httpHeaders);
        return filter(chain, build, exchange, url);
    }

    /**
     * 功能: 插入全局的traceId
     */
    private ServerWebExchange buildGlobalTraceId(ServerWebExchange exchange) {
        TraceHelper.clearCurrentTrace();
        TraceHelper.getCurrentTrace();
        String[] headerArray = {MDC.get(Trace.TRACE)};
        ServerHttpRequest req = exchange.getRequest().mutate().header(TraceHttpHeaderEnum.HEADER_TRACE_ID.getCode(),
                headerArray).build();
        return exchange.mutate().request(req).build();
    }

    private Mono<Void> filter(GatewayFilterChain chain, ServerWebExchange build, ServerWebExchange exchange,String url){
        return chain.filter(build).then(Mono.fromRunnable(() -> {
            Long startTime = exchange.getAttribute(START_TIME);
            if (startTime != null) {
                Long executeTime = (System.currentTimeMillis() - startTime);
                log.info("请求结束:{},总耗时:{}ms", url, executeTime);
            }
        }));
    }

    private String getRequestBody(ServerHttpRequest request){
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        try{
            String contentType = httpServletRequest.getContentType();
            SortedMap<String, Object> paramResult = Maps.newTreeMap();
            if (StrUtil.containsIgnoreCase(contentType, "json")) {
                BodyReaderRequestWrapper requestWrapper = new BodyReaderRequestWrapper(httpServletRequest);
                paramResult.putAll(new Gson().fromJson(ReqGetBody.getBody(requestWrapper), Map.class));
            } else if (StrUtil.containsIgnoreCase(contentType, "form")) {
                paramResult.putAll(RequestParamsUtil.getFormParams(httpServletRequest));
            }
            return new Gson().toJson(paramResult);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 从配置中心检查被禁止的url
     */
    private boolean checkForbiddenUrlsFromConfigCenter(String url) {
        String[] forbiddenUrls = gatewayConfigCenterConfig.getForbiddenUrls();
        if (ArrayUtil.isNotEmpty(forbiddenUrls)) {
            for (String forbiddenUrl : forbiddenUrls) {
                if (pathMatcher.match(forbiddenUrl, url)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 从配置中心检查不需要验证的url
     */
    private boolean checkNotNeedAuthUrlsFromConfigCenter(String url) {
        if (StrUtil.contains(url, SwaggerProviderConfig.API_URI)
                && gatewayConfigCenterConfig.getEnableSwagger()) {
            return true;
        }
        String[] authIgnoreUrls = gatewayConfigCenterConfig.getAuthIgnoreUrls();
        if (ArrayUtil.isNotEmpty(authIgnoreUrls)) {
            for (String authIgnoreUrl : authIgnoreUrls) {
                if (pathMatcher.match(authIgnoreUrl, url)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 返回错误的信息
     */
    private Mono<Void> responseErrMsg(ServerWebExchange exchange, Integer code, String errMsg) {
        JSONObject jo = new JSONObject();
        jo.put("code", code);
        jo.put("message", errMsg);
        DataBuffer buffer = responseMsg(exchange, jo);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }

    /**
     * 设置返回信息
     */
    private DataBuffer responseMsg(ServerWebExchange exchange, JSONObject msg) {
        ServerHttpResponse origin = exchange.getResponse();
        origin.setStatusCode(HttpStatus.OK);
        origin.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json;charset=utf-8");
        byte[] response = msg.toString().getBytes(StandardCharsets.UTF_8);
        return origin.bufferFactory().wrap(response);
    }

    /**
     * 设置header信息
     */
    private void setHeaderInfo(ServerWebExchange build, ServerHttpRequest request, String mobile,
                               String userId, String clientIp, String userAccessInfo,
                               HttpHeaders headers) {
        Consumer<HttpHeaders> httpHeadersConsumer = x -> {
            headers.set(HttpConstants.Header.MOBILE, mobile);
            headers.set(HttpConstants.Header.USER_ID, userId);
            headers.set(HttpConstants.Header.CLIENT_IP, clientIp);
            if(StrUtil.isNotEmpty(userAccessInfo)){
                headers.set(HttpConstants.Header.USER_ACCESS_INFO, userAccessInfo);
            }
        };
        build.mutate().request(request.mutate().headers(httpHeadersConsumer).build()).build();
    }

    /**
     * 获取客户端ip
     */
    private String getClientIp(ServerHttpRequest request) {
        String unknown = "unknown";
        char split = ',';
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !unknown.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(split) != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = headers.getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = headers.getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = headers.getFirst("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            if (request.getRemoteAddress() != null) {
                ip = request.getRemoteAddress().getAddress().getHostAddress();
            }
        }
        return ip;
    }

    @Override
    public int getOrder() {
        return -100;
    }
}