package com.litian.dancechar.biz.core.codegen.dto;

import com.litian.dancechar.biz.core.codegen.common.constants.Config;
import com.litian.dancechar.biz.core.codegen.common.enums.CodeGenParamEnums;
import com.litian.dancechar.biz.core.codegen.repository.dataobject.CodeGenDetailConfigDO;
import lombok.Data;

import java.util.List;

@Data
public class XnCodeGenParam {

    /**
     * 作者姓名
     */
    private String authorName;

    /**
     * 类名
     */
    private String className;

    /**
     * 功能名
     */
    private String functionName;

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
     * 数据库表名（经过组装的）
     */
    private String tableNameAss;

    /**
     * 代码包名
     */
    private String packageName;

    /**
     * 生成时间（String类型的）
     */
    private String createTimeString;

    /**
     * 数据库表中字段集合
     */
    private List<CodeGenDetailConfigDO> configList;

    /**
     * 模块名
     */
    private String modularNane = Config.MODULAR_NAME;

    /**
     * 业务名
     */
    private String busName;

    /**
     * 子业务名
     */
    private String childBusName;

    /**
     * 插件名称集合选择
     */
    private List<String> pluginList;

    /**
     * 模板类型 demo new old
     */
    private CodeGenParamEnums.TemplateTypeEnum templateTypeEnum;

    /**
     * groupId
     */
    private String groupId;

    /**
     * artifactId
     */
    private String artifactId;
}
