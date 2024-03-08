package com.litian.dancechar.biz.core.tabledetailinfo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.litian.dancechar.framework.common.base.BasePage;
import com.litian.dancechar.framework.common.util.YYYYMMDDHHMMSSDateAdapter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
    
/**
 * 类描述: 代码生成-表关联字段信息表查询请求DTO
 *
 * @author 853523
 * @date 2021-09-22 10:30:53
 */
@Data
@ApiModel("代码生成-表关联字段信息表查询请求DTO")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TableDetailInfoQueryReqDTO extends BasePage {

    @ApiModelProperty(value = "主键", name = "id", required = false, example = "")
    private Long id;
    @ApiModelProperty(value = "脚手架工程生成-基础信息Id", name = "scaffoldGenInfoId", required = false, example = "")
    private Long scaffoldGenInfoId;
    @ApiModelProperty(value = "数据库字段名", name="columnName")
    private String columnName;
    @ApiModelProperty(value = "数据库中类型（物理类型）", name="dataType")
    public String dataType;
    @ApiModelProperty(value = "首字母大写名称（用于代码生成get set方法）", name="columnKeyName")
    public String columnKeyName;
    @ApiModelProperty(value = "主外键", name="columnKey")
    public String columnKey;
    @ApiModelProperty(value = "java字段", name = "javaColumns", required = false, example = "")
    private String javaColumns;
    @ApiModelProperty(value = "java类型", name = "javaType", required = false, example = "")
    private String javaType;
    @ApiModelProperty(value = "字段描述", name = "columnComment", required = false, example = "")
    private String columnComment;
    @ApiModelProperty(value = "显示类型", name = "showType", required = false, example = "")
    private String showType;
    @ApiModelProperty(value = "是否在查询列表显示 0:否 1:是", name = "queryListShow", required = false, example = "")
    private Integer queryListShow;
    @ApiModelProperty(value = "查询列表显示的顺序号", name = "queryListNo", required = false, example = "")
    private Integer queryListNo;
    @ApiModelProperty(value = "是否查询条件 0:否 1:是", name = "queryCondition", required = false, example = "")
    private Integer queryCondition;
    @ApiModelProperty(value = "查询条件显示的顺序号", name = "queryConditionNo", required = false, example = "")
    private Integer queryConditionNo;
    @ApiModelProperty(value = "查询字段是否模糊 0:否 1:是", name = "queryConditionLike", required = false, example = "")
    private Integer queryConditionLike;
    @ApiModelProperty(value = "是否在增/改页面显示 0:否 1:是", name = "showAddUpdate", required = false, example = "")
    private Integer showAddUpdate;
    @ApiModelProperty(value = "增/改页面显示的顺序号", name = "addUpdateNo", required = false, example = "")
    private Integer addUpdateNo;
    @ApiModelProperty(value = "增/改页面是否必填 0:否 1:是", name = "addUpdateRequire", required = false, example = "")
    private Integer addUpdateRequire;
    @ApiModelProperty(value = "增/改页面必填提示的内容", name = "addUpdateValidateTips", required = false, example = "")
    private String addUpdateValidateTips;
    @ApiModelProperty(value = "创建时间", name = "createDate", required = false, dataType = "date", example = "2021-09-22 10:30:53")
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    private Date createDate;
    @ApiModelProperty(value = "创建人", name = "createUser", required = false, example = "")
    private String createUser;
    @ApiModelProperty(value = "删除标识-0: 未删除 1-已删除", name = "deleteFlag", required = false, example = "")
    private Integer deleteFlag;
    @ApiModelProperty(value = "修改时间", name = "updateDate", required = false, dataType = "date", example = "2021-09-22 10:30:53")
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    private Date updateDate;
    @ApiModelProperty(value = "更新人", name = "updateUser", required = false, example = "")
    private String updateUser;
}