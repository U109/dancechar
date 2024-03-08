package com.litian.dancechar.biz.core.scaffold.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ScaffoldMultiTableReqDTO implements Serializable {

    @ApiModelProperty(name="ids" , value="工程id集合")
    private List<Long> ids;

    @ApiModelProperty(name="genInfoList" , value="页面工程信息集合")
    private List<ScaffoldGenInfoDTO> genInfoList;


}
