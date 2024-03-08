package com.litian.dancechar.biz.sysmgr.esmgr.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.litian.dancechar.framework.common.util.YYYYMMDDHHMMSSDateAdapter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Date;

import static com.litian.dancechar.framework.common.util.DCDateUtil.YYYY_MM_DD_HH_MM_SS;
/**
 * 类描述: es连接信息管理DTO
 *
 * @author 01406831
 * @date 2021-11-04 15:28:33
 */
@Data
@ApiModel("es连接信息管理实例DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EsInfoDTO implements Serializable {

    @ApiModelProperty(value = "主键", name = "id", required = false, example = "")
    private Long id;

    @ApiModelProperty(value = "es版本(5.x或7.x)", name = "esVersion", required = false, example = "")
    private String esVersion;

    @ApiModelProperty(value = "es名称", name = "name", required = false, example = "")
    private String name;

    @ApiModelProperty(value = "系统名称", name = "clusterName", required = false, example = "")
    private String clusterName;

    @ApiModelProperty(value = "es地址(类似ip:port)", name = "esAddr", required = false, example = "")
    private String esAddr;

    @ApiModelProperty(value = "es用户名(7.x以后需要)", name = "esUserName", required = false, example = "")
    private String esUserName;

    @ApiModelProperty(value = "es用户密码(7.x以后需要)", name = "esUserPwd", required = false, example = "")
    private String esUserPwd;

    @ApiModelProperty(value = "备注", name = "remark", required = false, example = "")
    private String remark;

    @ApiModelProperty(value = "创建时间", name = "createDate", required = false, dataType = "date", example = "2021-11-04 15:28:33")
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    @JSONField(format = YYYY_MM_DD_HH_MM_SS)
    private Date createDate;

    @ApiModelProperty(value = "修改时间", name = "updateDate", required = false, dataType = "date", example = "2021-11-04 15:28:33")
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    @JSONField(format = YYYY_MM_DD_HH_MM_SS)
    private Date updateDate;

    @ApiModelProperty(value = "删除标识-0: 未删除 1-已删除", name = "deleteFlag", required = false, example = "")
    private Integer deleteFlag;

    @ApiModelProperty(value = "创建人", name = "createUser", required = false, example = "")
    private String createUser;

    @ApiModelProperty(value = "更新人", name = "updateUser", required = false, example = "")
    private String updateUser;

}
