package com.litian.dancechar.biz.sysmgr.system.dto;

import com.litian.dancechar.framework.common.base.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 类描述: systemInfo查询请求DTO
 *
 * @author fcoder
 * @date 2021-06-26 18:49:31
 */
@Data
@ApiModel("systemInfo查询请求DTO")
public class SystemInfoQueryReqDTO extends BasePage {

    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "系统编码")
    private String systemCode;

    @ApiModelProperty(value = "系统打包包名")
    private String packageName;

    @ApiModelProperty(value = "系统访问前缀")
    private String contextPath;

    @ApiModelProperty(value = "打包包名")
    private String groupId;

    @ApiModelProperty(value = "系统artifactId")
    private String artifactId;

    @ApiModelProperty(value = "版本号")
    private String version;
    @ApiModelProperty(value = "系统名称")
    private String systemName;
    @ApiModelProperty(value = "系统描述信息")
    private String systemDesc;
    @ApiModelProperty(value = "系统维护的团队")
    private String teamName;
    @ApiModelProperty(value = "系统负责人")
    private String leader;
    @ApiModelProperty(value = "创建时间")
    private Date createDate;
    @ApiModelProperty(value = "更新时间")
    private Date updateDate;
    @ApiModelProperty(value = "删除标识-0: 未删除 1-已删除")
    private Integer deleteFlag;
    @ApiModelProperty(value = "创建人")
    private String createUser;
    @ApiModelProperty(value = "更新人")
    private String updateUser;
    @ApiModelProperty(value = "1: 脚手架工程生成 2: 单功能生成单表 3：单功能多表")
    private Integer scaffoldType;
    @ApiModelProperty(value = "脚手架工程生成-基础信息Id")
    private Long scaffoldGenInfoId;

}
