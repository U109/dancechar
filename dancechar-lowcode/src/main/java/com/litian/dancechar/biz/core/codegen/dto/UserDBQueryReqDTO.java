package com.litian.dancechar.biz.core.codegen.dto;

import com.litian.dancechar.framework.common.base.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 类描述: userDB查询请求DTO
 *
 * @author terryhl
 * @date 2021-06-15 14:31:16
 */
@Data
@ApiModel("demo查询请求DTO")
public class UserDBQueryReqDTO extends BasePage {

    @ApiModelProperty(value = "所属用户id")
    private String userId;

    @ApiModelProperty(value = "数据库标识code")
    private String dbCode;

    @ApiModelProperty(value = "数据库驱动")
    private String dbDriver;

    @ApiModelProperty(value = "数据库host")
    private String dbHost;

    @ApiModelProperty(value = "数据库port")
    private String dbPort;

    @ApiModelProperty(value = "数据库连接url")
    private String dbUrl;

    @ApiModelProperty(value = "账户")
    private String dbUsername;

    @ApiModelProperty(value = "密码")
    private String dbPassword;

    @ApiModelProperty(value = "数据库名")
    private String dbName;

    @ApiModelProperty(value = "数据库描述用于展示")
    private String dbDesc;

}