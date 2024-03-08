package com.litian.dancechar.biz.core.scaffold.dto;

import io.swagger.annotations.ApiModel;
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
@ApiModel("ScaffoldMultiTableDbDTO")
public class ScaffoldMultiTableDbDTO implements Serializable {

    @ApiModelProperty(name="primaryDb",value="是否主库(0-否 1-是)")
    private Integer primaryDb;

    @ApiModelProperty(name="systemDbId",value="数据库id")
    private Long systemDbId;

    @ApiModelProperty(name="scaffoldGenDbExampleInfoList",value="表信息")
    private List<ScaffoldGenDbExampleInfoDTO> scaffoldGenDbExampleInfoList;

    @ApiModelProperty(name="originGenInfoDTO",value="原工程信息")
    private OriginGenInfoDTO originGenInfoDTO;

}