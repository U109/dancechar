package com.litian.dancechar.framework.es.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * es配置类
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Configuration
@Data
public class EsConfig {
    @Value("${es.host:localhost}")
    private String esHost;

    @Value("${es.port:9200}")
    private Integer esPort;
}
