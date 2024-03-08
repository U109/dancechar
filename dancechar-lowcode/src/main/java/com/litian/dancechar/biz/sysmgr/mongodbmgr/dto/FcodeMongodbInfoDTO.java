package com.litian.dancechar.biz.sysmgr.mongodbmgr.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.litian.dancechar.common.common.dto.BaseParam;
import com.litian.dancechar.framework.common.util.DCDateUtil;
import com.litian.dancechar.framework.common.util.YYYYMMDDHHMMSSDateAdapter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Date;

/**
 * 类描述: mongodb配置信息DTO
 *
 * @author 01410001
 * @date 2021-11-08 16:16:16
 */
@Data
@ApiModel("mongodb配置信息实例DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FcodeMongodbInfoDTO implements Serializable {

    @ApiModelProperty(value = "主键", name = "id", required = false, example = "")
    private Long id;

    @NotNull(message = "mongodb名字不能为空", groups = BaseParam.addUpdateRequired.class)
    @ApiModelProperty(value = "mongodb名字", name = "mongodbName", required = false, example = "")
    private String mongodbName;

    @NotNull(message = "mongodb数据库不能为空", groups = BaseParam.addUpdateRequired.class)
    @ApiModelProperty(value = "mongodb数据库", name = "mongodbDatabase", required = false, example = "")
    private String mongodbDatabase;

    @NotNull(message = "mongodb服务器地址不能为空", groups = BaseParam.addUpdateRequired.class)
    @ApiModelProperty(value = "mongodb host", name = "mongodbHost", required = false, example = "")
    private String mongodbHost;

    @NotNull(message = "mongodb用户名不能为空", groups = BaseParam.addUpdateRequired.class)
    @ApiModelProperty(value = "mongodb用户名", name = "mongodbUsername", required = false, example = "")
    private String mongodbUsername;

    @NotNull(message = "mongodb密码不能为空", groups = BaseParam.addUpdateRequired.class)
    @ApiModelProperty(value = "mongodb密码", name = "mongodbPassword", required = false, example = "")
    private String mongodbPassword;

    @NotNull(message = "mongodb客户端连接地址", groups = BaseParam.addUpdateRequired.class)
    @ApiModelProperty(value = "mongodb客户端连接地址", name = "mongodbUrl", required = false, example = "")
    private String mongodbUrl;

    @ApiModelProperty(value = "创建时间", name = "createDate", required = false, dataType = "date", example = "2021-11-08 16:16:16")
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    @JSONField(format = DCDateUtil.YYYY_MM_DD_HH_MM_SS)
    private Date createDate;

    @ApiModelProperty(value = "创建人", name = "createUser", required = false, example = "")
    private String createUser;

    @ApiModelProperty(value = "删除标识-0: 未删除 1-已删除", name = "deleteFlag", required = false, example = "")
    private Integer deleteFlag;

    @ApiModelProperty(value = "修改时间", name = "updateDate", required = false, dataType = "date", example = "2021-11-08 16:16:16")
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    @JSONField(format = DCDateUtil.YYYY_MM_DD_HH_MM_SS)
    private Date updateDate;

    @ApiModelProperty(value = "更新人", name = "updateUser", required = false, example = "")
    private String updateUser;

}
