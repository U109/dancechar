package com.litian.dancechar.framework.oss.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 访问阿里云oss需要的STS凭证
 */
@Data
public class AliyunOssStsPolicy {

    @ApiModelProperty("Android/iOS移动应用初始化OSSClient获取的 AccessKeyId")
    private String accessKeyId;

    @ApiModelProperty("Android/iOS移动应用初始化OSSClient获取AccessKeySecret")
    private String accessKeySecret;

    @ApiModelProperty("Android/iOS移动应用初始化的Token")
    private String securityToken;

    @ApiModelProperty("该Token失效的时间。Android SDK会自动判断Token是否失效，如果失效，则自动获取Token。")
    private String expiration;

    @ApiModelProperty("资源桶标识")
    private String bucket;

    @ApiModelProperty("所属域")
    private String domain;

    @ApiModelProperty("目录")
    private String dir;

    @ApiModelProperty("url")
    private String host;
}
