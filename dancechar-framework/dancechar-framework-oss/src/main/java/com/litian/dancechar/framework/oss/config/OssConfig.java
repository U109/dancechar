package com.litian.dancechar.framework.oss.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * oss配置信息
 *
 * @author tojson
 * @date 2021/2/14 21:15
 */
@Configuration
@Data
public class OssConfig {
    @Value("${oss.key:LTAI4GJ1w9zbhgNrULfEaGgX}")
    private String ossKey;

    @Value("${oss.secret:r1hhzy4OsUOhsRtd2BObErTCZhO3Lq}")
    private String ossSecret;

    @Value("${oss.bucket:zenu-oss}")
    private String ossBucket;

    @Value("${oss.endpoint:oss-cn-shenzhen.aliyuncs.com}")
    private String ossDomain;

    @Value("${oss.cdnUrl:cdnzeus.xfb.com}")
    private String cdnUrl;

    @Value("${oss.enableCDN:false}")
    private String enableCDN;

    @Value("${oss.env:online}")
    private String env;
}
