package com.litian.dancechar.biz.sysmgr.redismgr.dto;

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
 * 类描述: Redis信息管理查询请求DTO
 *
 * @author 01396106
 * @date 2021-10-12 11:05:08
 */
@Data
@ApiModel("Redis信息管理查询请求DTO")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FcodeRedisInfoQueryReqDTO extends BasePage {

    @ApiModelProperty(value = "主键，自增长", name = "id", required = false, example = "")
    private Long id;

    @ApiModelProperty(value = "部署方式:sentinel或cluster", name = "installType", required = false, example = "")
    private String installType;

    @ApiModelProperty(value = "名称", name = "name", required = false, example = "")
    private String name;

    @ApiModelProperty(value = "集群名", name = "clusterName", required = false, example = "")
    private String clusterName;

    @ApiModelProperty(value = "连接信息", name = "connectInfo", required = false, example = "")
    private String connectInfo;

    @ApiModelProperty(value = "超时时间，默认0-表示永不过期", name = "timeOut", required = false, example = "")
    private Integer timeOut;

    @ApiModelProperty(value = "密码", name = "password", required = false, example = "")
    private String password;

    @ApiModelProperty(value = "创建时间", name = "createDate", required = false, dataType = "date", example = "2021-10-12 11:05:08")
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    private Date createDate;

    @ApiModelProperty(value = "修改时间", name = "updateDate", required = false, dataType = "date", example = "2021-10-12 11:05:08")
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    private Date updateDate;

    @ApiModelProperty(value = "删除标识-0: 未删除 1-已删除", name = "deleteFlag", required = false, example = "")
    private Integer deleteFlag;

    @ApiModelProperty(value = "创建人", name = "createUser", required = false, example = "")
    private String createUser;

    @ApiModelProperty(value = "更新人", name = "updateUser", required = false, example = "")
    private String updateUser;

}