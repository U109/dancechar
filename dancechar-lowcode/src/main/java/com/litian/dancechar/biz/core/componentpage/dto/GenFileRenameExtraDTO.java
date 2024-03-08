package com.litian.dancechar.biz.core.componentpage.dto;

import com.litian.dancechar.common.common.dto.BaseDTO;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 类描述: 实例DTO
 *
 * @author fcoder
 * @date 2021-06-30 16:42:34
 */
@Data
@ApiModel("实例DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenFileRenameExtraDTO extends BaseDTO {

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

    @Override
    public int hashCode() {
        return this.genDbExampleId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        GenFileRenameExtraDTO genFileRenameExtraDTO = (GenFileRenameExtraDTO) obj;
        if(DCObjectUtil.equals(this.genInfoId, genFileRenameExtraDTO.getGenInfoId())
                && DCObjectUtil.equals(this.genDbId, genFileRenameExtraDTO.getGenDbId())
                && DCObjectUtil.equals(this.genDbExampleId, genFileRenameExtraDTO.getGenDbExampleId())){
            return true;
        }
        return false;
    }
}