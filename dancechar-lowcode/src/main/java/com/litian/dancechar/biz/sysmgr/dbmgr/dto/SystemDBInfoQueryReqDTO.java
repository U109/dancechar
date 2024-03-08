package com.litian.dancechar.biz.sysmgr.dbmgr.dto;

import com.litian.dancechar.framework.common.base.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 类描述: systemDBInfo查询请求DTO
 *
 * @author terryhl
 * @date 2021-06-21 11:48:37
 */
@Data
@ApiModel("查询请求DTO")
public class SystemDBInfoQueryReqDTO extends BasePage {

    @ApiModelProperty(value = "数据库驱动类型")
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