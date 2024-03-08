package com.litian.dancechar.biz.sysmgr.sysdict.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.litian.dancechar.common.common.dto.BaseParam;
import com.litian.dancechar.framework.common.util.YYYYMMDDHHMMSSDateAdapter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Date;

/**
 * 类描述: 数据字典数据DTO
 *
 * @author 01407390
 * @date 2021-09-28 15:16:56
 */
@Data
@ApiModel("数据字典数据实例DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysDictDataDTO implements Serializable {

    @ApiModelProperty(value = "主键，自增长", name = "id", required = false, example = "")
    private Long id;

    @ApiModelProperty(value = "数据字典类型id", name = "typeId", required = false, example = "1")
    @NotNull(message = "数据字典类型id不能为空", groups = BaseParam.addUpdateRequired.class)
    private Long typeId;

    @ApiModelProperty(value = "字典code", name = "code", required = false, example = "1")
    @NotEmpty(message = "字典code不能为空", groups = BaseParam.addUpdateRequired.class)
    private String code;

    @ApiModelProperty(value = "字典值", name = "value", required = false, example = "男")
    @NotEmpty(message = "字典值不能为空", groups = BaseParam.addUpdateRequired.class)
    private String value;

    @ApiModelProperty(value = "状态 0:禁用 1:启用 ", name = "status", required = false, example = "1")
    private Integer status;

    @ApiModelProperty(value = "排序", name = "showNo", required = false, example = "1")
    private Integer showNo;

    @ApiModelProperty(value = "备注 ", name = "remark", required = false, example = "")
    private String remark;

    @ApiModelProperty(value = "创建时间", name = "createDate", required = false, dataType = "date", example = "2021-09-28 15:16:56")
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    private Date createDate;

    @ApiModelProperty(value = "创建人", name = "createUser", required = false, example = "")
    private String createUser;

    @ApiModelProperty(value = "删除标识-0: 未删除 1-已删除", name = "deleteFlag", required = false, example = "")
    private Integer deleteFlag;

    @ApiModelProperty(value = "修改时间", name = "updateDate", required = false, dataType = "date", example = "2021-09-28 15:16:56")
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    private Date updateDate;

    @ApiModelProperty(value = "更新人", name = "updateUser", required = false, example = "")
    private String updateUser;
}
