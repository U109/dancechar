package com.litian.dancechar.core.feign.member.integral.enums;

/**
 * 积分类型枚举
 *
 * @author tojson
 * @date 2021/6/21 21:25
 */
public enum IntegralInfoTypeEnum {
    /**
     * 活动任务
     */
    ACT_TASK("actType", "活动任务");

    /**
     * 状态码
     */
    private String code;

    /**
     * 描述信息
     */
    private String message;

    IntegralInfoTypeEnum(String code, String message) {
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
