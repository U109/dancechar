package com.litian.dancechar.biz.sysmgr.sysdict.dto;

import com.litian.dancechar.common.common.dto.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class SysDictDataReqDTO implements Serializable {

    @ApiModelProperty(value = "数据字典类型id", name = "typeId", required = false, example = "")
    @NotNull(message = "数据字典类型id不能为空" , groups = BaseParam.addUpdateRequired.class)
    private Long typeId;
    @ApiModelProperty(value = "数据字典类型对应的值", name = "sysDictDataDTOList", required = false, example = "")
    private List<SysDictDataDTO> sysDictDataDTOList;

}
