package com.litian.dancechar.biz.core.scaffold.repository.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import lombok.*;

import java.io.Serializable;

/**
 * 类描述：单功能sql预览
 *
 * @author 01410001
 * @date 2021/08/07 16:47
 */
@Data
@TableName("fcode_function_gen_sql")
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FunctionGenSqlDO extends BaseDO implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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
}
