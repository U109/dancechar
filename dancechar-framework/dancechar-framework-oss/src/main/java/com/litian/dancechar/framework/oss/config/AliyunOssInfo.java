package com.litian.dancechar.framework.oss.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 阿里云OSS配置相关，供前端直连阿里云；
 * 目前是直接将相关秘钥告知前端，但这样不安全，需要研究阿里云的临时访问策略STS
 */
@Data
public class AliyunOssInfo {
    @ApiModelProperty("访问key")
    private String key;

    @ApiModelProperty("访问秘钥")
    private String secret;

    @ApiModelProperty("资源桶标识")
    private String bucket;

    @ApiModelProperty("所属域")
    private String domain;

    @ApiModelProperty("目录")
    private String dir;

    @ApiModelProperty("url")
    private String host;
}
