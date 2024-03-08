package com.litian.dancechar.biz.core.componentpage.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GenFileMultiTableDTO implements Serializable {

    @ApiModelProperty(name="ids",value="工程id集合")
    private List<Long> ids;

    @ApiModelProperty(name="previewFileDTO",value="预览信息")
    private GenFileInfoQueryReqDTO previewFileDTO;
}
