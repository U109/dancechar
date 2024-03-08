package com.litian.dancechar.biz.core.codegen.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 代码生成数据库参数类
 *
 * @author terry
 * @date 20210609
 */
@Data
public class CodeGenerateDBParam extends BaseParam {

    /**
     * 数据库url
     */
    @NotNull(message = "数据库url不能为空", groups = {add.class})
    private String dbUrl;

    /**
     * 数据库用户名
     */
    @NotNull(message = "用户名不能为空", groups = {add.class})
    private String username;

    /**
     * 数据库密码
     */
    @NotNull(message = "数据库密码不能为空", groups = {add.class})
    private String password;

}
