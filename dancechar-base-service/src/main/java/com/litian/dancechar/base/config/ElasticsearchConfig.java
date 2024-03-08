package com.litian.dancechar.base.config;

import com.litian.dancechar.framework.es.config.EsConfig;
import com.litian.dancechar.framework.es.util.ElasticsearchUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * es配置
 *
 * @author tojson
 * @date 2022/8/28 23:25
 */
@Slf4j
@Configuration
public class ElasticsearchConfig {
    @Resource
    private EsConfig esConfig;

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        return new RestHighLevelClient(RestClient.builder(new HttpHost( esConfig.getEsHost(),
                esConfig.getEsPort(), "http")));
    }

    @Bean
    public ElasticsearchUtil elasticsearchUtil() {
        return new ElasticsearchUtil();
    }
}
