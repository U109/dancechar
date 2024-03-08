package com.litian.dancechar.biz.core.componentpage.dto;

import com.litian.dancechar.common.common.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 类描述: GenFileInfoDTO
 *
 * @author fcoder
 * @date 2021-06-29 14:24:32
 */
@Data
@ApiModel("实例DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenFileInfoDTO extends BaseDTO {

    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "关联生成主表gen_info的id")
    private Long genInfoId;
    @ApiModelProperty(value = "关联表genDbInfoId主键id")
    private Long genDbId;
    @ApiModelProperty(value = "关联表gen_db_example_info主键id")
    private Long genDbExampleId;
    @ApiModelProperty(value = "原始文件路径未替换")
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
    // 文件后缀
    private String fileSuffix;
    // 用于展示属性结构的路径 剔除了
    private String showRealPathName;

}