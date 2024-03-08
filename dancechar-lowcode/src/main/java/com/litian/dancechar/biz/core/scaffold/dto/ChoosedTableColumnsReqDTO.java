package com.litian.dancechar.biz.core.scaffold.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 已选择的库表字段请求对象
 *
 * @author 01396106
 * @since 2021-08-04 14:32:35
 */
@Data
public class ChoosedTableColumnsReqDTO implements Serializable {

    /**
     * 脚手架生成-基础信息Id
     */
    private Long scaffoldGenInfoId;

    /**
     * 字段信息
     */
    List<ChoosedTableColumnsDTO> columnsList;

    /**
     * sql对象
     */
    FunctionGenSqlDTO genSqlDTO;

}
