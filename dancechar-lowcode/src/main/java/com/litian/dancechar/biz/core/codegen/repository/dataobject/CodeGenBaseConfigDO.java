package com.litian.dancechar.biz.core.codegen.repository.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 代码生成基础配置
 *
 * @author 01406831
 * @date 2021年06月02日8:04:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("fcode_code_gen_base_config")
public class CodeGenBaseConfigDO extends BaseDO {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 作者姓名
     */
    private String authorName;

    /**
     * 类名
     */
    private String className;

    /**
     * 是否移除表前缀
     */
    private String tablePrefix;

    /**
     * 生成方式
     */
    private String generateType;

    /**
     * 数据库表名
     */
    private String tableName;
    /**
     * 包名
     */
    private String packageName;

    /**
     * 业务名（业务代码包名称）
     */
    private String busName;

    /**
     * 子业务名（业务代码包名称）
     */
    private String childBusName;

    /**
     * 功能名（数据库表名称）
     */
    private String tableComment;
}
