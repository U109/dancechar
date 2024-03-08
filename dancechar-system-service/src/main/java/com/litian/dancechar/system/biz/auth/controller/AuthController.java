package com.litian.dancechar.system.biz.auth.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.token.TokenHelper;
import com.litian.dancechar.system.biz.auth.dto.TokenDTO;
import com.litian.dancechar.system.biz.user.dao.entity.SystemUserDO;
import com.litian.dancechar.system.biz.user.dto.SystemUserAuthRespDTO;
import com.litian.dancechar.system.biz.user.dto.SystemUserReqDTO;
import com.litian.dancechar.system.biz.user.service.SystemUserService;
import com.litian.dancechar.system.enums.SystemRespResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 鉴权业务处理
 *
 * @author tojson
 * @date 2022/7/9 06:26
 */
@Api(tags = "鉴权相关api")
@RestController
@Slf4j
@RequestMapping(value = "/auth/")
public class AuthController {
    @Resource
    private SystemUserService systemUserService;

    @ApiOperation(value = "登录验证账号密码是否正确", notes = "登录验证账号密码是否正确")
    @PostMapping("loginValidAccountNo")
    public RespResult<SystemUserAuthRespDTO> loginValidAccountNo(@RequestBody SystemUserReqDTO req) {
        return systemUserService.validAccountNo(req.getAccountNo(), req.getPwd());
    }

    @ApiOperation(value = "刷新accessToken", notes = "刷新accessToken")
    @PostMapping("refreshToken")
    public RespResult<TokenDTO> refreshToken(@RequestBody TokenDTO req) {
        if(StrUtil.isEmpty(req.getUserId())){
            return RespResult.error("userId不能为空");
        }
        if(StrUtil.isEmpty(req.getRefreshToken())){
            return RespResult.error("refresh token不能为空");
        }
        Map<String,String> verifyMap = TokenHelper.verify(req.getRefreshToken());
        if("0".equals(verifyMap.get("isPass"))) {
            return RespResult.error("refresh token不合法");
        }
        if ("0".equals(verifyMap.get("isPass"))) {
            if("1".equals(verifyMap.get("expired"))){
                log.error("refresh token expired! token:{}", req.getRefreshToken());
                // refresh token过期，前端直接提示用户重新登录
                return RespResult.error(SystemRespResultCode.REFRESH_TOKEN_EXPIRED);
            }else {
                log.error("refresh token invalid! token:{}", req.getRefreshToken());
                return RespResult.error("refresh token不合法");
            }
        }
        TokenDTO tokenDTO = new TokenDTO();
        SystemUserDO systemUserDO = systemUserService.findByIdFromCache(req.getUserId());
        if(ObjectUtil.isNotNull(systemUserDO)){
            tokenDTO.setAccessToken(TokenHelper.buildAdminToken(req.getUserId(), systemUserDO.getAccountNo()));
            tokenDTO.setRefreshToken(TokenHelper.buildAdminRefreshToken(req.getUserId(),
                    systemUserDO.getAccountNo()));
            tokenDTO.setUserId(req.getUserId());
            return RespResult.success(tokenDTO);
        }
        tokenDTO.setUserId(req.getUserId());
        return RespResult.success(tokenDTO);
    }

    @ApiOperation(value = "验证token是否过期", notes = "验证token是否过期")
    @PostMapping("validTokenExpire")
    public RespResult<Void> validTokenExpire(@RequestBody TokenDTO req) {
        log.info("accessToken is expire:{}", !TokenHelper.checkExp(req.getAccessToken()));
        log.info("refreshToken is expire:{}", TokenHelper.checkExp(req.getRefreshToken()));
        return RespResult.success();
    }
}