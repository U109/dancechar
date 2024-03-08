package com.litian.dancechar.framework.common.base;

/**
 * 类描述: 公共的返回结果码接口
 *
 * @author 01406831
 * @date 2021/04/16 10:43
 */
public interface DCIResultCode {
    /**
     * 结果码
     *
     * @return 结果码
     */
    String getCode();

    /**
     * 错误消息
     *
     * @return 错误消息
     */
    String getMsg();

    /**
     * 描述
     *
     * @return 描述
     */
    String getDesc();
}