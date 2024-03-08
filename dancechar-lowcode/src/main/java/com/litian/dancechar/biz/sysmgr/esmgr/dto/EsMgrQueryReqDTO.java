package com.litian.dancechar.biz.sysmgr.esmgr.dto;

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
 * 类描述: ES连接信息管理查询请求DTO
 *
 * @author 01396106
 * @date 2021-10-18 13:39:03
 */
@Data
@ApiModel("ES连接信息管理查询请求DTO")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class EsMgrQueryReqDTO extends BasePage {

    @ApiModelProperty(value = "主键", name = "id", required = false, example = "")
    private Long id;

    @ApiModelProperty(value = "名称", name = "name", required = false, example = "")
    private String name;

    @ApiModelProperty(value = "集群名", name = "clusterName", required = false, example = "")
    private String clusterName;

    @ApiModelProperty(value = "es地址", name = "esAddr", required = false, example = "")
    private String esAddr;

    @ApiModelProperty(value = "备注", name = "remark", required = false, example = "")
    private String remark;

    @ApiModelProperty(value = "创建时间", name = "createDate", required = false, dataType = "date", example = "2021-10-18 13:39:03")
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    private Date createDate;

    @ApiModelProperty(value = "修改时间", name = "updateDate", required = false, dataType = "date", example = "2021-10-18 13:39:03")
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    private Date updateDate;

    @ApiModelProperty(value = "删除标识-0: 未删除 1-已删除", name = "deleteFlag", required = false, example = "")
    private Integer deleteFlag;

    @ApiModelProperty(value = "创建人", name = "createUser", required = false, example = "")
    private String createUser;

    @ApiModelProperty(value = "更新人", name = "updateUser", required = false, example = "")
    private String updateUser;

}