package com.litian.dancechar.biz.core.scaffold.repository.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import lombok.*;

import java.io.Serializable;

/**
 * 已选择的库表字段
 *
 * @author 01396106
 * @since 2021-08-04 14:32:35
 */
@Data
@TableName("focde_choosed_table_columns")
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChoosedTableColumnsDO extends BaseDO implements Serializable {
    private static final long serialVersionUID = -45407410460668716L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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
