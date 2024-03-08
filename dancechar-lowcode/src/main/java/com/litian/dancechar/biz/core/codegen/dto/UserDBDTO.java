package com.litian.dancechar.biz.core.codegen.dto;

import com.litian.dancechar.common.common.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 类描述: 实例DTO
 *
 * @author terryhl
 * @date 2021-06-15 14:31:16
 */
@Data
@ApiModel("实例DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDBDTO extends BaseDTO implements Serializable {

    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "所属用户id")
    private String userId;
    @ApiModelProperty(value = "数据库标识code")
    @NotBlank(message = "数据库dbCode不能为空", groups = {BaseParam.edit.class})
    private String dbCode;
    @ApiModelProperty(value = "数据库驱动")
    @NotBlank(message = "数据库驱动不能为空", groups = {BaseParam.add.class, BaseParam.edit.class})
    private String dbDriver;
    @ApiModelProperty(value = "数据库host")
    @NotBlank(message = "数据库host不能为空", groups = {BaseParam.add.class, BaseParam.edit.class})
    private String dbHost;
    @ApiModelProperty(value = "数据库port")
    @NotBlank(message = "数据库port不能为空", groups = {BaseParam.add.class, BaseParam.edit.class})
    private String dbPort;
    @ApiModelProperty(value = "数据库连接url")
    @NotBlank(message = "数据库url不能为空", groups = {BaseParam.add.class, BaseParam.edit.class})
    private String dbUrl;
    @ApiModelProperty(value = "账户")
    @NotBlank(message = "数据库username不能为空", groups = {BaseParam.add.class, BaseParam.edit.class})
    private String dbUsername;
    @ApiModelProperty(value = "密码")
    @NotBlank(message = "数据库password不能为空", groups = {BaseParam.add.class, BaseParam.edit.class})
    private String dbPassword;
    @ApiModelProperty(value = "数据库名")
    @NotBlank(message = "数据库dbname不能为空", groups = {BaseParam.add.class, BaseParam.edit.class})
    private String dbName;
    @ApiModelProperty(value = "数据库描述用于展示")
    @NotBlank(message = "数据库描述不能为空", groups = {BaseParam.add.class, BaseParam.edit.class})
    private String dbDesc;
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

}