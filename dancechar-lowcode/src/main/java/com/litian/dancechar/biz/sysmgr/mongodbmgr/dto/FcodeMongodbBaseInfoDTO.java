package com.litian.dancechar.biz.sysmgr.mongodbmgr.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 类描述: mongodb配置基本信息DTO
 *
 * @author 01410001
 * @date 2021-11-08 16:16:16
 */
@Data
@ApiModel("mongodb配置基本信息实例DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FcodeMongodbBaseInfoDTO implements Serializable {

    @ApiModelProperty(value = "主键", name = "id", required = false, example = "")
    private Long id;

    @ApiModelProperty(value = "mongodb名字", name = "mongodbName", required = false, example = "")
    private String mongodbName;

    @ApiModelProperty(value = "mongodb数据库", name = "mongodbDatabase", required = false, example = "")
    private String mongodbDatabase;

    @ApiModelProperty(value = "mongodb host", name = "mongodbHost", required = false, example = "")
    private String mongodbHost;

    @ApiModelProperty(value = "mongodb用户名", name = "mongodbUsername", required = false, example = "")
    private String mongodbUsername;

    @ApiModelProperty(value = "mongodb密码", name = "mongodbPassword", required = false, example = "")
    private String mongodbPassword;

    @ApiModelProperty(value = "mongodb服务器地址", name = "mongodbUrl", required = false, example = "")
    private String mongodbUrl;

}
