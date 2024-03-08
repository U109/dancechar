package com.litian.dancechar.biz.sysmgr.dbmgr.dto;

import com.litian.dancechar.biz.sysmgr.dbmgr.common.enums.SystemDBInfoEnums;
import com.litian.dancechar.common.common.dto.BaseParam;
import com.litian.dancechar.framework.common.util.page.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 类描述: 实例DTO
 *
 * @author terryhl
 * @date 2021-06-21 11:48:37
 */
@Data
@ApiModel("实例DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SystemDBInfoDTO extends BaseDTO implements Serializable {
    @ApiModelProperty(value = "主键")
    private Long id;

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
    private String dbDesc;

    @ApiModelProperty(value = "是否主库 1-是，0-否")
    private Integer primaryDb;

    public String getDbUrl() {
        if (this.dbUrl != null) {
            return this.dbUrl;
        }
        if (SystemDBInfoEnums.DriverEnum.MYSQL.getCode().equals(this.dbDriver)) {
            return "jdbc:mysql://" + this.dbHost + ":" + this.dbPort + "/" + this.dbName;
        } else if (SystemDBInfoEnums.DriverEnum.ORACLE.getCode().equals(this.dbDriver)) {
            return "jdbc:oracle:thin:@" + this.dbHost + ":" + this.dbPort + ":" + this.dbName;
        }
        return null;
    }
}
