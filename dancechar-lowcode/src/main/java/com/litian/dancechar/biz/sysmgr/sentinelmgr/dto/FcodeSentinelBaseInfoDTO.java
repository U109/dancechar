package com.litian.dancechar.biz.sysmgr.sentinelmgr.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 类描述: sentinel配置信息DTO
 *
 * @author 01410001
 * @date 2021-10-12 14:08:05
 */
@Data
@ApiModel("sentinel配置信息实例DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FcodeSentinelBaseInfoDTO implements Serializable {

    @ApiModelProperty(value = "主键", name = "id", required = false, example = "")
    private Long id;

    @ApiModelProperty(value = "sentinel名称", name = "sentinelName", required = false, example = "")
    private String sentinelName;

    @ApiModelProperty(value = "控制台地址", name = "consoleUrl", required = false, example = "")
    private String consoleUrl;

    @ApiModelProperty(value = "zk地址", name = "zkUrl", required = false, example = "")
    private String zkUrl;
}
