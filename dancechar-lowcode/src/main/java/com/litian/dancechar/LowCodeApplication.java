package com.litian.dancechar;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 低代码服务
 *
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "com.litian.dancechar", exclude = {DataSourceAutoConfiguration.class})
@EnableAsync
@ImportResource(locations = {"classpath:spring-restful-service.xml"})
@PropertySources({
        @PropertySource(value = "disconf.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "system.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "application.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "sys.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "kafka.properties", ignoreResourceNotFound = true)}
)
public class LowCodeApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(LowCodeApplication.class, args);
        log.warn("LowCodeApplication-低代码服务启动成功......");
    }
}
