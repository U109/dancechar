package com.litian.dancechar.framework.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * base异常类
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException {

    private String code;

    private String message;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

}
