package com.litian.dancechar.biz.sysmgr.kafkamgr.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 类描述: kafka配置信息DTO
 *
 * @author 01410001
 * @date 2021-10-12 14:07:34
 */
@Data
@ApiModel("kafka配置信息实例DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FcodeKafkaBaseInfoDTO implements Serializable {

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

}
