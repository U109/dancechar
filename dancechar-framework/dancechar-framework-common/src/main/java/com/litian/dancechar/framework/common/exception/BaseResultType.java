package com.litian.dancechar.framework.common.exception;

public interface BaseResultType {

    /**
     * 返回调用编码
     * @return
     */
    String getCode();

    /**
     * 返回调用信息
     * @return
     */
    String getMessage();
}
