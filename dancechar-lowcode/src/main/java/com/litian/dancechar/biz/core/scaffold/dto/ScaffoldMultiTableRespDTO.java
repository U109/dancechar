package com.litian.dancechar.biz.core.scaffold.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 脚手架生成-数据库信息(ScaffoldGenDbInfo)DTO
 *
 * @author 01406831
 * @since 2021-06-21 14:33:21
 */
@Data
public class ScaffoldMultiTableRespDTO implements Serializable {
    private Long id;
    @ApiModelProperty(name="scaffoldGenInfoId",value = "脚手架生成-基础信息Id")
    private Long scaffoldGenInfoId;
    @ApiModelProperty(name="systemDbId",value = "数据库id")
    private Long systemDbId;
    @ApiModelProperty(name="primaryDb",value = "是否主库(0-否 1-是)")
    private Integer primaryDb;
    @ApiModelProperty(name="scaffoldGenDbExampleInfoList",value = "表信息")
    private List<ScaffoldGenDbExampleInfoDTO> scaffoldGenDbExampleInfoList;
    @ApiModelProperty(name="dirNames",value = "目录名称(多个使用#分隔),比如service、manager、repository")
    private String dirNames;
    @ApiModelProperty(name="genSqlDTO",value = "预览信息")
    private FunctionGenSqlDTO genSqlDTO;
    @ApiModelProperty(name="genFunctions",value = "生成功能")
    private String genFunctions;

}