package com.litian.dancechar.common.common.constants;

import com.litian.dancechar.framework.common.base.DCIResultCode;

/**
 * 类描述：低代码异常返回值集合
 * @author 01396106
 * @date 2021/07/16 14:12
 */
public enum FcodeReponseErrorCode implements DCIResultCode {

    COLLECT_DB_ERROR("010000","No Tables","DB连接异常或数据库中没有表");
    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 错误描述
     */
    private String desc;

    FcodeReponseErrorCode(String code, String msg, String desc) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getDesc() {
        return desc;
    }
}
