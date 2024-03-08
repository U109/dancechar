package com.litian.dancechar.gateway.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 系统参数配置
 *
 * @author tojson
 * @date 2021/6/22 09:51
 */
@Configuration
@Data
@RefreshScope
public class SystemConfig {
    /**
     * 是否启动鉴权
     */
    @Value("${auth.enable:true}")
    private Boolean authEnable;

    /**
     * 排除不鉴权的url
     */
    @Value("${auth.ignore.urls:'/login/test'}")
    private String[] authIgnoreUrls;

    /**
     * 是否放行swagger ui
     */
    @Value("${auth.enableSwagger:true}")
    private Boolean enableSwagger;

    /**
     * 禁止访问的url
     */
    @Value("${auth.forbidden.urls:'/nologin'}")
    private String[] forbiddenUrls;
}