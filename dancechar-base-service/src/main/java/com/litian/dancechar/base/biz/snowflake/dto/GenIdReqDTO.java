package com.litian.dancechar.base.biz.snowflake.dto;

import lombok.Data;

@Data
public class GenIdReqDTO {
    /**
     * 前缀
     */
    private String prefix;

    /**
     * 生成的数量
     */
    private int num;
}
