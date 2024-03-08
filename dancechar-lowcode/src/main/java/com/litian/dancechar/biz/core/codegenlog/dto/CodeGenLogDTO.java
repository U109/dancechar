package com.litian.dancechar.biz.core.codegenlog.dto;

import com.litian.dancechar.biz.sysmgr.sentinelmgr.dto.FcodeSentinelBaseInfoDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 类描述: 实例DTO
 *
 * @author fcoder
 * @date 2021-07-05 10:07:23
 */
@Data
@ApiModel("实例DTO")
public class CodeGenLogDTO extends FcodeSentinelBaseInfoDTO implements Serializable {

    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "代码生产量(行)")
    private Long codeTotalNum;
    @ApiModelProperty(value = "方法数")
    private Integer toolMethodNum;
    @ApiModelProperty(value = "所有库名")
    private String databaseName;
    @ApiModelProperty(value = "所有表名")
    private String tableName;
    @ApiModelProperty(value = "系统编码")
    private String systemCode;
    @ApiModelProperty(value = "创建时间")
    private Date createDate;
    @ApiModelProperty(value = "修改时间")
    private Date updateDate;
    @ApiModelProperty(value = "删除标识-0: 未删除 1-已删除")
    private Integer deleteFlag;
    @ApiModelProperty(value = "创建人")
    private String createUser;
    @ApiModelProperty(value = "更新人")
    private String updateUser;
}
