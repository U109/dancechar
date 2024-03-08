package com.litian.dancechar.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 网关启动类
 *
 * @author tojson
 * @date 2021/6/22 09:52
 */
@SpringCloudApplication
@EnableFeignClients(basePackages = "com.litian.dancechar")
@Slf4j
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
        log.warn("网关启动成功......");
    }
}