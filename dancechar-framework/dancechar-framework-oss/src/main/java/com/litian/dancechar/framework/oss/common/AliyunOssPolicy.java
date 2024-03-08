package com.litian.dancechar.framework.oss.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 阿里云OSS相关访问信息
 */
@Data
@ApiModel(value = "阿里云OSS相关访问信息")
public class AliyunOssPolicy {

    @ApiModelProperty("访问Id")
    private String accessId;

    @ApiModelProperty("访问策略")
    private String policy;

    @ApiModelProperty("访问签名")
    private String signature;

    @ApiModelProperty("过期时间（s）")
    private String expire;

    @ApiModelProperty("目录")
    private String dir;

    @ApiModelProperty("url")
    private String host;

}
