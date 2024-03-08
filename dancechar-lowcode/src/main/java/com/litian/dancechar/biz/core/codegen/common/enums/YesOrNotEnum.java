package com.litian.dancechar.biz.core.codegen.common.enums;

import lombok.Getter;

/**
 * 是或否的枚举
 */
@Getter
public enum YesOrNotEnum {

    /**
     * 是
     */
    Y(1, "Y","是"),

    /**
     * 否
     */
    N(0, "N","否");

    private final Integer code;

    private final String desc;

    private final String message;

    YesOrNotEnum(Integer code, String desc, String message) {
        this.code = code;
        this.desc = desc;
        this.message = message;
    }

}
