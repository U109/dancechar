package com.litian.dancechar.biz.core.scaffold.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 已选择的库表字段
 *
 * @author 01396106
 * @since 2021-08-04 14:32:35
 */
@Data
public class ChoosedTableColumnsDTO implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 1: 脚手架生成 2: 单功能生成
     */
    private Long scaffoldGenInfoId;
    /**
     * 字段名
     */
    private String columnName;

    /**
     * 字段类型
     */
    private String columnType;

    /**
     * 字段别名
     */
    private String aliasName;
    /**
     * 字段描述
     */
    private String columnComment;

    /**
     * 表名
     */
    private String tableName;

}
