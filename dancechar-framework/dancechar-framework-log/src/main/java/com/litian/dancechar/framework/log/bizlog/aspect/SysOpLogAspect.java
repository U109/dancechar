package com.litian.dancechar.framework.log.bizlog.aspect;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.context.HttpContext;
import com.litian.dancechar.framework.common.eventbus.EventBusFactory;
import com.litian.dancechar.framework.common.util.IPUtil;
import com.litian.dancechar.framework.log.bizlog.annotation.SysOpLogAnnotation;
import com.litian.dancechar.framework.log.bizlog.dto.SysOpLogReqDTO;
import com.litian.dancechar.framework.log.bizlog.eventbus.SysOpLogEvent;
import com.litian.dancechar.framework.log.bizlog.eventbus.SysOpLogEventBusListener;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Objects;

/**
 * 系统操作日志切面
 *
 * @author tojson
 * @date 2021/6/14 21:15
 */
@Slf4j
@Aspect
@Configuration
public class SysOpLogAspect {
    private static ExpressionParser expressionParser = new SpelExpressionParser();
    private static LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();


    @Pointcut(value = "@annotation(com.litian.dancechar.framework.log.bizlog.annotation.SysOpLogAnnotation)")
    public void aroundBefore(){
        log.info("log aspect start....");
    }

    @AfterReturning(value = "aroundBefore()", returning = "respResult")
    public void aroundAfter(JoinPoint joinPoint, Object respResult){
        try {
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature =  (MethodSignature)signature;
            Method method = methodSignature.getMethod();
            if(method.isAnnotationPresent(SysOpLogAnnotation.class)){
                ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder
                        .getRequestAttributes();
                HttpServletRequest request = attributes.getRequest();
                SysOpLogAnnotation sysOpLogAnnotation = method.getAnnotation(SysOpLogAnnotation.class);
                SysOpLogReqDTO sysOpLogReqDTO = buildSysOpLogReqDTO(sysOpLogAnnotation, request, joinPoint,
                        respResult, signature, method);
                // 发布异步事件
                EventBusFactory.build().registerAsyncEvent(SysOpLogEventBusListener.class);
                EventBusFactory.build().postAsyncEvent(SysOpLogEvent.builder()
                        .sysOpLogReqDTO(sysOpLogReqDTO).eventName("后台操作日志发布事件").build());
            }
        }catch (Throwable e){
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 构建请求参数
     */
    private SysOpLogReqDTO buildSysOpLogReqDTO(SysOpLogAnnotation sysOpLogAnnotation,HttpServletRequest request,
                                              JoinPoint joinPoint, Object respResult, Signature signature,
                                              Method method){
        SysOpLogReqDTO sysOpLogReqDTO = new SysOpLogReqDTO();
        sysOpLogReqDTO.setMenuName(sysOpLogAnnotation.menuName());
        sysOpLogReqDTO.setMenuBtn(sysOpLogAnnotation.menuBtn());
        sysOpLogReqDTO.setUrl(request.getRequestURI());
        sysOpLogReqDTO.setClassName(signature.getDeclaringTypeName());
        sysOpLogReqDTO.setMethodName(method.getName());
        sysOpLogReqDTO.setReqType(request.getMethod());
        StringBuilder builder = new StringBuilder();
        Object[] args = joinPoint.getArgs();
        sysOpLogReqDTO.setOpContent(sysOpLogAnnotation.opContent());
        if(args != null && args.length > 0){
            for (Object obj : args){
                builder.append(JSONUtil.toJsonStr(obj));
            }
            if(StrUtil.isNotEmpty(sysOpLogAnnotation.reqParam())){
                sysOpLogReqDTO.setOpContent(sysOpLogReqDTO.getOpContent()+"#" + parse(sysOpLogAnnotation.reqParam(),
                        method, args));
            }
        }
        sysOpLogReqDTO.setParams(builder.toString());
        if(respResult instanceof RespResult){
            RespResult result = (RespResult)respResult;
            sysOpLogReqDTO.setSuccess(result.isOk());
        }
        sysOpLogReqDTO.setResult(JSONUtil.toJsonStr(respResult));
        sysOpLogReqDTO.setOpAccount(HttpContext.getMobile());
        sysOpLogReqDTO.setOpTime(new Date());
        sysOpLogReqDTO.setOpIp(IPUtil.getIpAddress(request));
        return sysOpLogReqDTO;
    }

    public static String parse(String key ,Method method, Object[] args){
        String[] params = discoverer.getParameterNames(method);
        if(params == null || Objects.equals("default", key)){
            return key;
        }
        EvaluationContext context = new StandardEvaluationContext();
        for(int i=0; i < params.length;i++){
            context.setVariable(params[i], args[i]);
        }
        String[] keys = key.split(",");
        StringBuilder result = new StringBuilder();
        for(String k : keys){
            result.append(expressionParser.parseExpression(k).getValue(context ,String.class));
            result.append(":");
        }
        return result.deleteCharAt(result.length() -1).toString();
    }
}
