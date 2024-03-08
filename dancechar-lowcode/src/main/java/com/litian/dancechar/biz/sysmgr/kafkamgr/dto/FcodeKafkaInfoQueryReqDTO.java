package com.litian.dancechar.biz.sysmgr.kafkamgr.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.litian.dancechar.framework.common.base.BasePage;
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
 * 类描述: kafka配置信息查询请求DTO
 *
 * @author 01410001
 * @date 2021-10-12 14:07:34
 */
@Data
@ApiModel("kafka配置信息查询请求DTO")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FcodeKafkaInfoQueryReqDTO extends BasePage {

    @ApiModelProperty(value = "主键", name = "id", required = false, example = "")
    private Long id;

    @ApiModelProperty(value = "kafka名字", name = "kafkaName", required = false, example = "")
    private String kafkaName;

    @ApiModelProperty(value = "集群名字", name = "clusterName", required = false, example = "")
    private String clusterName;

    @ApiModelProperty(value = "monitor地址", name = "monitorUrl", required = false, example = "")
    private String monitorUrl;

    @ApiModelProperty(value = "消费者名称", name = "consumerName", required = false, example = "")
    private String consumerName;

    @ApiModelProperty(value = "验证码", name = "checkCode", required = false, example = "")
    private String checkCode;

    @ApiModelProperty(value = "topic名字", name = "topicName", required = false, example = "")
    private String topicName;

    @ApiModelProperty(value = "创建时间", name = "createDate", required = false, dataType = "date", example = "2021-10-12 14:07:34")
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    private Date createDate;

    @ApiModelProperty(value = "创建人", name = "createUser", required = false, example = "")
    private String createUser;

    @ApiModelProperty(value = "删除标识-0: 未删除 1-已删除", name = "deleteFlag", required = false, example = "")
    private Integer deleteFlag;

    @ApiModelProperty(value = "修改时间", name = "updateDate", required = false, dataType = "date", example = "2021-10-12 14:07:34")
    @XmlJavaTypeAdapter(YYYYMMDDHHMMSSDateAdapter.class)
    private Date updateDate;

    @ApiModelProperty(value = "更新人", name = "updateUser", required = false, example = "")
    private String updateUser;

}