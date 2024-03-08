package com.litian.dancechar.core.feign.idgen.enums;

/**
 * Id生成类型枚举
 *
 * @author tojson
 * @date 2021/6/21 21:25
 */
public enum IdGenTypeEnum {
    /**
     * 活动
     */
    ACT("act", "活动"),
    /**
     * 客户
     */
    CUSTOMER("customer", "客户"),
    /**
     * 公共配置
     */
    COMMON_CONFIG("commonconfig", "公共配置"),
    /**
     * order
     */
    ORDER("order", "订单"),
    /**
     * integral
     */
    INTEGRAL("integral", "积分"),
    ;

    /**
     * 状态码
     */
    private String code;

    /**
     * 描述信息
     */
    private String message;

    IdGenTypeEnum(String code, String message) {
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
