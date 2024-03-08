package com.litian.dancechar.biz.sysmgr.sentinelmgr.dto;

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

/**
 * 类描述: sentinel配置信息DTO
 *
 * @author 01410001
 * @date 2021-10-12 14:08:05
 */
@Data
@ApiModel("sentinel配置信息实例DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FcodeSentinelInfoDTO implements Serializable {

    @ApiModelProperty(value = "主键", name = "id", required = false, example = "")
    private Long id;

    @ApiModelProperty(value = "sentinel名称", name = "sentinelName", required = false, example = "")
    private String sentinelName;

    @ApiModelProperty(value = "控制台地址", name = "consoleUrl", required = false, example = "")
    private String consoleUrl;

    @ApiModelProperty(value = "zk地址", name = "zkUrl", required = false, example = "")
    private String zkUrl;

    @ApiModelProperty(value = "创建时间", name = "createDate", required = false, dataType = "date", example = "2021-10-12 14:08:05")
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    private Date createDate;

    @ApiModelProperty(value = "创建人", name = "createUser", required = false, example = "")
    private String createUser;

    @ApiModelProperty(value = "删除标识-0: 未删除 1-已删除", name = "deleteFlag", required = false, example = "")
    private Integer deleteFlag;

    @ApiModelProperty(value = "修改时间", name = "updateDate", required = false, dataType = "date", example = "2021-10-12 14:08:05")
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    private Date updateDate;

    @ApiModelProperty(value = "更新人", name = "updateUser", required = false, example = "")
    private String updateUser;

}
