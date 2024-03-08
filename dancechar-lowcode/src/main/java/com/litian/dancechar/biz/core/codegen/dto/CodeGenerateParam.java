package com.litian.dancechar.biz.core.codegen.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 代码生成参数类
 *
 * @author yubaoshan
 * @date 2020年12月16日20:41:21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CodeGenerateParam extends BaseParam {

    /**
     * 主键
     */
//    @NotNull(message = "id不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 作者姓名
     */
    @NotBlank(message = "作者姓名不能为空，请检查authorName参数", groups = {add.class, edit.class})
    private String authorName;

    /**
     * 类名
     */
    @NotBlank(message = "类名不能为空，请检查className参数", groups = {add.class, edit.class})
    private String className;

//    /**
//     * 是否移除表前缀
//     */
//    @NotBlank(message = "是否移除表前缀不能为空，请检查tablePrefix参数", groups = {BaseParam.add.class, edit.class})
//    @FlagValue(message = "是否移除表前缀格式错误，正确格式应该Y或者N，请检查tablePrefix参数", groups = {add.class, edit.class})
//    private String tablePrefix;

//    /**
//     * 生成方式
//     */
//    @NotBlank(message = "生成方式不能为空，请检查generateType参数", groups = {BaseParam.add.class, edit.class})
//    private String generateType;

    /**
     * 数据库表名
     */
    @NotBlank(message = "数据库表名不能为空，请检查tableName参数", groups = {add.class, edit.class})
    private String tableName;

    /**
     * 代码包名
     */
    private String packageName;

    /**
     * 业务名（业务代码包名称）
     */
    @NotBlank(message = "业务名不能为空，请检查busName参数", groups = {add.class, edit.class})
    private String busName;

    /**
     * 子业务名（业务代码包名称）
     */
    @NotBlank(message = "子业务名不能为空，请检查busName参数", groups = {add.class, edit.class})
    private String childBusName;

    /**
     * 功能名（数据库表名称）
     */
    @NotBlank(message = "功能名不能为空，请检查tableComment参数", groups = {add.class, edit.class})
    private String tableComment;


    /**
     * 模板类型 demo简单crud new old
     */
    private String templateType;

    /**
     * 插件名称集合选择 redis kafka
     */
    private List<String> pluginList;

    /**
     * groupId
     */
    private String groupId;

    /**
     * artifactId
     */
    private String artifactId;

    /**
     * 数据库连接信息
     */
    private CodeGenerateDBParam codeGenerateDBParam;
}
