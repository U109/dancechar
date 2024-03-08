package com.litian.dancechar.core.biz.item.enums;

/**
 * 事务补偿状态枚举
 *
 * @author tojson
 * @date 2021/6/21 21:25
 */
public enum TransactionStatusEnum {
    /**
     * 1-进行中 2-成功 3、失败
     */
    IS_DOING(1, "进行中"),
    /**
     * 成功
     */
    SUCCESS(2, "成功"),
    /**
     * 失败
     */
    FAIL(3, "失败");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 描述信息
     */
    private String message;

    TransactionStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
