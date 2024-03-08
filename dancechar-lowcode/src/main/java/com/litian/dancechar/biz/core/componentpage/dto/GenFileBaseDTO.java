package com.litian.dancechar.biz.core.componentpage.dto;

import com.litian.dancechar.common.common.dto.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 类描述: genFileInfo查询请求DTO
 *
 * @author fcoder
 * @date 2021-06-29 14:24:32
 */
@Data
public class GenFileBaseDTO implements Serializable {

    @ApiModelProperty(value = "关联生成主表gen_info的id")
    @NotNull(message = "genInfoId不能为空", groups = {BaseParam.previewGen.class})
    private Long genInfoId;
    @ApiModelProperty(value = "关联表genDbInfoId主键id")
    @NotNull(message = "genDbId不能为空", groups = {BaseParam.previewGen.class})
    private Long genDbId;
    @ApiModelProperty(value = "关联表gen_db_example_info主键id")
    @NotNull(message = "filePathName不能为空", groups = {BaseParam.previewGen.class})
    private Long genDbExampleId;
    @ApiModelProperty(value = "原始文件路径未替换")
    @NotNull(message = "filePathName不能为空", groups = {BaseParam.previewGen.class})
    private String filePathName;

}