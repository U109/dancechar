package com.litian.dancechar.core.biz.activity.act20220910.enums;

/**
 * 物品类型枚举
 *
 * @author tojson
 * @date 2021/6/21 21:25
 */
public enum ItemTypeEnum {
    /**
     * 积分
     */
    INTEGRAL("integral", "积分");

    /**
     * 状态码
     */
    private String code;

    /**
     * 描述信息
     */
    private String message;

    ItemTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
