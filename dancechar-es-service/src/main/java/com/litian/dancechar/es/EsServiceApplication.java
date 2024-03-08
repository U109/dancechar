package com.litian.dancechar.es;

import cn.easyes.starter.register.EsMapperScan;
import com.alibaba.cloud.nacos.ConditionalOnNacosDiscoveryEnabled;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * es服务启动类
 * @author tojson
 * @date 2023/11/29 21:15
 */
@SpringBootApplication(scanBasePackages = {"com.litian.dancechar"})
@EnableFeignClients(basePackages = "com.litian.dancechar")
@EnableDiscoveryClient
@ConditionalOnNacosDiscoveryEnabled
@EsMapperScan("com.litian.dancechar.es")
@Slf4j
public class EsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsServiceApplication.class, args);
        log.warn("es服务启动成功......");
    }
}