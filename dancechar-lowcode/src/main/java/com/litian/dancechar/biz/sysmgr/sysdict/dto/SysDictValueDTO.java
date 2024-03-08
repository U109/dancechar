package com.litian.dancechar.biz.sysmgr.sysdict.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysDictValueDTO implements Serializable {

    @ApiModelProperty(value = "请求方式 get post", name = "requestType", required = false, example = "")
    private String requestType;
    @ApiModelProperty(value = "请求参数 ", name = "requestParams", required = false, example = "")
    private String requestParams;
    @ApiModelProperty(value = "请求字段名称", name = "requestName", required = false, example = "")
    private String requestName;
    @ApiModelProperty(value = "请求字段编码 ", name = "requestCode", required = false, example = "")
    private String requestCode;
    @ApiModelProperty(value = "字典值", name = "value", required = false, example = "")
    private String value;
    @ApiModelProperty(value = "状态 0:禁用 1:启用 ", name = "status", required = false, example = "")
    private Integer status;
    @ApiModelProperty(value = "排序", name = "showNo", required = false, example = "")
    private Integer showNo;
    @ApiModelProperty(value = "备注 ", name = "remark", required = false, example = "")
    private String remark;

}
