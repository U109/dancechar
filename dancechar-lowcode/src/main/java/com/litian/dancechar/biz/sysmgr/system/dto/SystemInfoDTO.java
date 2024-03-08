package com.litian.dancechar.biz.sysmgr.system.dto;

import com.litian.dancechar.common.common.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 类描述: SystemInfoDTO
 *
 * @author fcoder
 * @date 2021-06-26 18:49:31
 */
@Data
@ApiModel("实例DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SystemInfoDTO extends BaseDTO implements Serializable {

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
}
