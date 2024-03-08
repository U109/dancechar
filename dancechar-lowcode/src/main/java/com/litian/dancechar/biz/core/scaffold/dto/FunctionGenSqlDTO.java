package com.litian.dancechar.biz.core.scaffold.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 类描述：单功能sql预览
 *
 * @author 01410001
 * @date 2021/08/07 16:47
 */
@Data
public class FunctionGenSqlDTO implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 脚手架工程生成-基础信息Id
     */
    private Long scaffoldGenInfoId;

    /**
     * 结果集
     */
    private String resultColumns;

    /**
     * 关联字段
     */
    private String relevanceColumns;

    /**
     * 预览sql
     */
    private String previewSql;

    private String tableName;

    private Long systemDbId;
}
