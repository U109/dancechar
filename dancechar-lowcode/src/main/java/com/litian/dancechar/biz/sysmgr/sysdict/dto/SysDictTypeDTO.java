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
 * 类描述: 数据字典类型DTO
 *
 * @author 01407390
 * @date 2021-09-28 15:47:15
 */
@Data
@ApiModel("低代码平台-数据字典类型表实例DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysDictTypeDTO implements Serializable {

    @ApiModelProperty(value = "主键，自增长", name = "id", required = false, example = "")
    private Long id;

    @ApiModelProperty(value = "类型名称", name = "typeName", required = false, example = "")
    @NotEmpty(message = "类型名称不能为空" , groups = BaseParam.addUpdateRequired.class)
    private String typeName;

    @ApiModelProperty(value = "类型编码", name = "typeCode", required = false, example = "")
    @NotEmpty(message = "类型编码不能为空" , groups = BaseParam.addUpdateRequired.class)
    private String typeCode;

    @ApiModelProperty(value = "来源 1:固定 2:接口", name = "typeSource", required = false, example = "")
    @NotNull(message = "来源不能为空" , groups = BaseParam.addUpdateRequired.class)
    private Integer typeSource;

    @ApiModelProperty(value = "请求url", name = "requestUrl", required = false, example = "")
    private String requestUrl;

    @ApiModelProperty(value = "请求方式 get post", name = "requestType", required = false, example = "")
    private String requestType;

    @ApiModelProperty(value = "请求参数 ", name = "requestParams", required = false, example = "")
    private String requestParams;

    @ApiModelProperty(value = "请求字段名称", name = "requestName", required = false, example = "")
    private String requestName;

    @ApiModelProperty(value = "请求字段编码 ", name = "requestCode", required = false, example = "")
    private String requestCode;

    @ApiModelProperty(value = "状态 0:禁用 1:启用 ", name = "status", required = false, example = "")
    @NotNull(message = "状态不能为空" , groups = BaseParam.addUpdateRequired.class)
    private Integer status;

    @ApiModelProperty(value = "排序", name = "showNo", required = false, example = "")
    @NotNull(message = "排序不能为空" , groups = BaseParam.addUpdateRequired.class)
    private Integer showNo;

    @ApiModelProperty(value = "备注 ", name = "remark", required = false, example = "")
    private String remark;

    @ApiModelProperty(value = "创建时间", name = "createDate", required = false, dataType = "date", example = "2021-09-28 15:47:15")
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    private Date createDate;

    @ApiModelProperty(value = "创建人", name = "createUser", required = false, example = "")
    private String createUser;

    @ApiModelProperty(value = "删除标识-0: 未删除 1-已删除", name = "deleteFlag", required = false, example = "")
    private Integer deleteFlag;

    @ApiModelProperty(value = "修改时间", name = "updateDate", required = false, dataType = "date", example = "2021-09-28 15:47:15")
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    private Date updateDate;

    @ApiModelProperty(value = "更新人", name = "updateUser", required = false, example = "")
    private String updateUser;
}
