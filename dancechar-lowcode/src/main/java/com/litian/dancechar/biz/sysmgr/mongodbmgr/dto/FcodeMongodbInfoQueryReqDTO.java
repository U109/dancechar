package com.litian.dancechar.biz.sysmgr.mongodbmgr.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.litian.dancechar.framework.common.base.BasePage;
import com.litian.dancechar.framework.common.util.DCDateUtil;
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
 * 类描述: mongodb配置信息查询请求DTO
 *
 * @author 01410001
 * @date 2021-11-08 16:16:16
 */
@Data
@ApiModel("mongodb配置信息查询请求DTO")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FcodeMongodbInfoQueryReqDTO extends BasePage {

    @ApiModelProperty(value = "主键", name = "id", required = false, example = "")
    private Long id;

    @ApiModelProperty(value = "mongodb名字", name = "mongodbName", required = false, example = "")
    private String mongodbName;

    @ApiModelProperty(value = "mongodb数据库", name = "mongodbDatabase", required = false, example = "")
    private String mongodbDatabase;

    @ApiModelProperty(value = "mongodb host", name = "mongodbHost", required = false, example = "")
    private String mongodbHost;

    @ApiModelProperty(value = "mongodb服务器地址", name = "mongodbUrl", required = false, example = "")
    private String mongodbUrl;

    @ApiModelProperty(value = "mongodb用户名", name = "mongodbUsername", required = false, example = "")
    private String mongodbUsername;

    @ApiModelProperty(value = "mongodb密码", name = "mongodbPassword", required = false, example = "")
    private String mongodbPassword;

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