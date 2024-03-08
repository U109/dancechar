package com.litian.dancechar.biz.core.componentpage.dto;

import com.litian.dancechar.common.common.dto.BaseParam;
import com.litian.dancechar.framework.common.base.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 类描述: genFileInfo查询请求DTO
 *
 * @author fcoder
 * @date 2021-06-29 14:24:32
 */
@Data
@ApiModel("genFileInfo查询请求DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenFileInfoQueryReqDTO extends BasePage {

    @ApiModelProperty(value = "主键")
    private Long id;
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
    @ApiModelProperty(value = "替换后的文件路径")
    private String realPathName;
    @ApiModelProperty(value = "文件类包含的方法列表")
    private String methodListExtra;
    @ApiModelProperty(value = "文件及类名用于重命名")
    private String className;
    @ApiModelProperty(value = "创建人")
    private String createUser;
    @ApiModelProperty(value = "创建时间")
    private Date createDate;
    @ApiModelProperty(value = "更新人")
    private String updateUser;
    @ApiModelProperty(value = "更新时间")
    private Date updateDate;
    @ApiModelProperty(value = "是否删除")
    private Integer deleteFlag;
}