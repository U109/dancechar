package com.litian.dancechar.biz.core.scaffold.repository.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 脚手架生成-数据库-示例信息(ScaffoldGenDbExampleInfo)实体类
 *
 * @author 01406831
 * @since 2021-06-21 14:33:56
 */
@Data
@TableName("fcode_scaffold_gen_db_table_info")
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScaffoldGenDbExampleInfoDO extends BaseDO implements Serializable {
    private static final long serialVersionUID = 405114778286761328L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 脚手架工程生成-数据库信息Id
     */
    private Long scaffoldGenDbInfoId;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 表描述
     */
    private String tableDesc;
    /**
     * 实例类名
     */
    private String className;
    /**
     * 实例目录名
     */
    private String catalog;
    /**
     * 包名
     */
    private String packageName;
    /**
     * 作者
     */
    private String author;

}