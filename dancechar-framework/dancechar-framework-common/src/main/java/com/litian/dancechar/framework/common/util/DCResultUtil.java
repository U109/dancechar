package com.litian.dancechar.framework.common.util;


import com.litian.dancechar.framework.common.base.DCIResultCode;
import com.litian.dancechar.framework.common.base.Result;
import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;

/**
 * 类描述: 通用返回结果工具类
 *
 * @author 01406831
 */
public class DCResultUtil {

    /**
     * 返回成功，不带输出结果
     *
     * @return Result<T>
     */
    public static <T> Result<T> success() {
        return success(null);
    }

    /**
     * 返回成功，带输出结果
     *
     * @param obj 输出结果
     * @return Result<T>
     */
    public static <T> Result<T> success(T obj) {
        Result<T> result = new Result<>(true);
        result.setDate(FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
        result.setObj(obj);
        return result;
    }

    /**
     * 返回失败，带错误信息
     *
     * @param errorMessage 错误消息
     * @return Result<T>
     */
    public static <T> Result<T> error(String errorMessage) {
        Result<T> result = new Result<>(false);
        result.setDate(FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
        result.setErrorCode(null);
        result.setErrorMessage(errorMessage);
        return result;
    }

    /**
     * 返回失败，带错误代码和错误消息
     *
     * @param errorMessage 错误消息
     * @param errorCode    错误代码
     * @return Result<T>
     */
    public static <T> Result<T> error(String errorCode, String errorMessage) {
        Result<T> result = new Result<>(false);
        result.setDate(FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
        result.setErrorCode(errorCode);
        result.setErrorMessage(errorMessage);
        return result;
    }

    /***
     * 返回失败，带错误信息
     * @param resultCode 错误消息
     * @return com.sf.framework.domain.Result<T>
     */
    public static <T> Result<T> error(DCIResultCode resultCode) {
        Result<T> result = new Result<>(false);
        result.setDate(FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
        result.setErrorCode(resultCode.getCode());
        result.setErrorMessage(resultCode.getDesc());
        return result;
    }

    /***
     * 返回失败，带错误信息
     * @param resultCode 错误消息
     * @param obj 响应内容
     * @return com.sf.framework.domain.Result<T>
     */
    public static <T> Result<T> error(DCIResultCode resultCode, T obj) {
        Result<T> result = error(resultCode);
        result.setObj(obj);
        return result;
    }

    public static <T> Result<T> error(String errorCode, String errorMessage, T obj) {
        Result<T> result = error(errorCode, errorMessage);
        result.setObj(obj);
        return result;
    }

}
