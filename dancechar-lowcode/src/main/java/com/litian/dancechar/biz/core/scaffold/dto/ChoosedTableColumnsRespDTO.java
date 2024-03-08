package com.litian.dancechar.biz.core.scaffold.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 类描述：单功能保存数据库返回对象
 *
 * @author 01410001
 * @date 2021/08/09 15:07
 */
@Data
public class ChoosedTableColumnsRespDTO implements Serializable {

    /**
     * dbId
     */
    private Long id;
    /**
     * 实例类名
     */
    private String className;
    /**
     * 实例目录名
     */
    private String catalog;
    /**
     * 生成功能
     */
    private String genFunctions;

    /**
     * 脚手架生成-基础信息Id
     */
    private Long scaffoldGenInfoId;

    /**
     * 表名
     */
    private String tableName;

    private Long scaffoldGenDbInfoId;

}
