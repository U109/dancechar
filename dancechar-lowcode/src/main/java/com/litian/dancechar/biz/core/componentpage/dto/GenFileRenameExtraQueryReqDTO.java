package com.litian.dancechar.biz.core.componentpage.dto;

import com.litian.dancechar.framework.common.base.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * 类描述: genFileRenameExtra查询请求DTO
 *
 * @author fcoder
 * @date 2021-06-30 16:42:34
 */
@Data
@ApiModel("genFileRenameExtra查询请求DTO")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenFileRenameExtraQueryReqDTO extends BasePage {

    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "关联生成主表gen_info的id")
    private Long genInfoId;
    @ApiModelProperty(value = "关联表gen_db_info主键id")
    private Long genDbId;
    @ApiModelProperty(value = "关联表gen_db_example_info主键id")
    private Long genDbExampleId;
    @ApiModelProperty(value = "固定好所有的文件名默认先用classname")
    private String renameExtra;
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