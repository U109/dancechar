package com.litian.dancechar.biz.core.scaffold.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述：所有方法响应对象
 *
 * @author 01410001
 * @date 2021/09/12 15:43
 */
@Data
public class FuncMethodListRespDTO implements Serializable {

    @ApiModelProperty(value = "功能描述",name = "genFunctions")
    private String genFunctions;

    @ApiModelProperty(value = "功能包名",name = "functionDir")
    private String functionDir;

    @ApiModelProperty(value = "方法列表",name = "methodList")
    private List<FuncMethodRespDTO> methodList;
}
