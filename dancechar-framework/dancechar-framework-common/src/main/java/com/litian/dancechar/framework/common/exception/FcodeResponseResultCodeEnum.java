package com.litian.dancechar.framework.common.exception;


import com.litian.dancechar.framework.common.base.DCIResultCode;

/**
 * 类描述: 返回码状态枚举
 *
 * @author 01406831
 * @date 2021/04/16 11:13
 */
public enum FcodeResponseResultCodeEnum implements DCIResultCode {
    /**
     * 接口返回正常
     */
    SUCCESS("0", "接口返回正常", "接口返回正常"),
    /**
     * 接口返回失败
     */
    FAIL("1", "接口返回失败", "接口返回失败"),
    /**
     * 用于处理常见参数校验失败
     */
    ILLEGAL_ARGUMENT("10000", "参数不符合规范", "参数不符合规范"),
    ILLEGAL_ARGUMENT_WITH_MESSAGE("10001", "参数不符合规范,{}", "参数不符合规范,{}"),
    SYSTEM_EXCEPTION("10002", "系统异常", "系统异常"),
    SYSTEM_EXCEPTION_WITH_MESSAGE("10003", "系统异常:{}", "系统异常:{}"),
    ENCRYPT_FAIL("10009", "加密错误", "加密错误"),
    DECRYPT_FAIL("10009", "解密错误", "解密错误"),
    GEN_FILE_CREATE_FAIL("20000", "文件生成表数据创建失败", "文件生成表数据创建失败"),
    GEN_FILE_NOT_FOUND("20001", "未找到关联文件", "未找到关联文件"),
    GEN_FILE_RENDER_FAIL("20002", "模板渲染错误", "模板渲染错误"),
    DB_CHECK_FAIL("20003", "DB创建失败", "DB创建失败"),
    DB_TABLE_INFO_MISSING("20004", "表信息缺失", "表信息缺失"),
    DB_CHECK_FAIL_SAMEDB_CREATE("20005", "DB创建失败，已有相同db", "DB创建失败，已有相同db"),
    DB_CHECK_FAIL_SQL_ERROR("20006", "sql有误，请重新填写信息", "sql有误，请重新填写信息"),
    /**
     * 名字不能重复
     */
    NAME_NOT_REPEAT("5000000", "error.rpc.fail", "名字不能重复"),
    ERR_UPLOAD_FILE("100051", "error.upload.file", "请上传文件或文件有误"),
    ERR_UPLOAD_FILE_SIZE("100052", "error.upload.file", "您上传的图片过大，图片请勿超过大小限制"),
    ERR_UPLOAD_TXT_SIZE("100053", "error.upload.file", "您上传的文件过大，文件请勿超过大小限制"),
    ERR_UPLOAD_TXT_EMPTY("100054", "error.upload.file", "文件不能为空"),
    /**
     * ************* 系统异常 ******************
     */
    ERR_AUTHENTICATION_FAIL("999996", "error.authentication.fail", "鉴权失败"),
    ERR_RPC_TIMEOUT("999997", "error.rpc.timeout", "远程服务调用超时"),
    ERR_RPC_FAIL("999998", "error.rpc.fail", "网络异常，请稍后再试"),
    ERR_UNKNOWN("999999", "error.rpc.fail", "系统繁忙，请稍后再试");

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

    FcodeResponseResultCodeEnum(String code, String msg, String desc) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}