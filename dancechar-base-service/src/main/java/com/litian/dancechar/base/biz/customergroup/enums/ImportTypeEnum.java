package com.litian.dancechar.base.biz.customergroup.enums;

/**
 * 导入类型
 *
 * @author tojson
 * @date 2022/7/9 06:26
 */
public enum ImportTypeEnum {
    /**
     * 单个导入
     */
    ONE(1, "单个导入"),
    /**
     * 拆分导入
     */
    MANY(2, "拆分导入");
    ImportTypeEnum(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    private Integer code;

    private String name;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
