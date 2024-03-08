package com.litian.dancechar.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * 限流配置
 *
 * @author tojson
 * @date 2021/6/22 09:51
 */
@Configuration
public class RateLimitConfig {

    /**
     * 功能: 请求地址的uri限流
     *//*
    @Bean(value = "apiKeyResolver")
    KeyResolver apiKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getPath().value());
    }*/

    /**
     * 功能: 根据用户id限流
     */
    /*@Bean(value = "userKeyResolver")
    KeyResolver userKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("userId"));
    }*/

    /**
     * 功能: 根据IP限流
     */
    @Bean(value = "remoteAddrKeyResolver")
    public KeyResolver remoteAddrKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest()
                .getRemoteAddress().getAddress().getHostAddress());
    }
}