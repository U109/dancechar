package com.litian.dancechar.biz.sysmgr.esmgr.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * 类描述: ES操作DTO
 *
 * @author 01396106
 * @date 2021-10-18 13:39:03
 */
@Data
@ApiModel("ES操作DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EsOprDTO implements Serializable {


    private int from = 0;

    private int size = 20;

    @ApiModelProperty(value = "索引名", name = "indexName", required = false, example = "")
    private String indexName;

    @ApiModelProperty(value = "id", name = "id", required = false, example = "")
    private String id;

    @ApiModelProperty(value = "入参json串", name = "reqJson", required = false, example = "")
    private String reqJson;


    @ApiModelProperty(value = "精确匹配matchQuery", name = "matchQuery", required = false, example = "")
    private Map<String, String> matchQuery;

    @ApiModelProperty(value = "范围匹配rangeQuery", name = "rangeQuery", required = false, example = "")
    private Map<String, Map<String, String>> rangeQuery;

}
