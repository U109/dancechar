package com.litian.dancechar.system;

import com.alibaba.cloud.nacos.ConditionalOnNacosDiscoveryEnabled;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 系统服务启动类
 * @author tojson
 * @date 2021/6/19 21:15
 */
@SpringBootApplication(scanBasePackages = {"com.litian.dancechar"})
@EnableFeignClients(basePackages = "com.litian.dancechar")
@EnableDiscoveryClient
@ConditionalOnNacosDiscoveryEnabled
@Slf4j
public class SystemServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemServiceApplication.class, args);
        log.warn("系统服务启动成功......");
    }
}