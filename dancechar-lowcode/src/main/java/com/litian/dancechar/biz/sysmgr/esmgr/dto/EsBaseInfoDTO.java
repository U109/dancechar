package com.litian.dancechar.biz.sysmgr.esmgr.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 类描述: es连接信息管理DTO
 *
 * @author 01406831
 * @date 2021-11-04 15:28:33
 */
@Data
@ApiModel("es连接信息管理实例DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EsBaseInfoDTO implements Serializable {

    @ApiModelProperty(value = "主键", name = "id", required = false, example = "")
    private Long id;

    @ApiModelProperty(value = "es版本(5.x或7.x)", name = "esVersion", required = false, example = "")
    private String esVersion;

    @ApiModelProperty(value = "es名称", name = "name", required = false, example = "")
    private String name;

    @ApiModelProperty(value = "系统名称", name = "clusterName", required = false, example = "")
    private String clusterName;

    @ApiModelProperty(value = "es地址(类似ip:port)", name = "esAddr", required = false, example = "")
    private String esAddr;

    @ApiModelProperty(value = "es用户名(7.x以后需要)", name = "esUserName", required = false, example = "")
    private String esUserName;

    @ApiModelProperty(value = "es用户密码(7.x以后需要)", name = "esUserPwd", required = false, example = "")
    private String esUserPwd;

    @ApiModelProperty(value = "备注", name = "remark", required = false, example = "")
    private String remark;

}
