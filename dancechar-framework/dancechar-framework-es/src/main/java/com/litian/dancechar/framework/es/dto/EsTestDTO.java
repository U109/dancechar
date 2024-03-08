package com.litian.dancechar.framework.es.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EsTestDTO implements Serializable {
    private String id;

    private String no;

    private String name;

    private String indexName;

    /**
     * 查询记录某些字段，多个字段之间使用,分隔
     */
    private String fields;
}
