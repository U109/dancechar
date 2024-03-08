package com.litian.dancechar.framework.common.exception;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * 自定义异常类
 *
 * @author terryhl
 */
public class BizException extends BaseException {

    private Object data;

    private static final long serialVersionUID = -1776868554028292642L;

    public BizException(String code, String errorMessage, Object data) {
        super(code, errorMessage);
        this.data = data;
    }

    public BizException(FcodeResponseResultCodeEnum fcodeResponseResultCodeEnum) {
        super(fcodeResponseResultCodeEnum.getCode(), fcodeResponseResultCodeEnum.getMsg());
    }

    /**
     * 覆盖提示 或替换占位
     */
    public BizException(FcodeResponseResultCodeEnum fcodeResponseResultCodeEnum, Object... args) {
        super(fcodeResponseResultCodeEnum.getCode(), StringUtils.indexOf(fcodeResponseResultCodeEnum.getMsg(), "{}") > 0
                ? StrUtil.format(fcodeResponseResultCodeEnum.getMsg(), args) :
                (String) args[0]);
    }

    public BizException(String code, String errorMessage) {
        super(code, errorMessage);
    }
}
