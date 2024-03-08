package com.litian.dancechar.framework.common.base;

/**
 * 类描述: 通用返回结果码状态
 *
 * @author 01406831
 * @date 2021/04/16 10:43
 */
public enum DCResponseResultCodeEnum implements DCIResultCode {
    /**
     * 成功
     */
    SUCCESS("000000", "success", "成功"),
    /**
     * 通过错误
     */
    ERR_PARAM_EMPTY("100001", "error.param.empty", "必填参数为空"),
    /**
     * 参数不合法
     */
    ERR_PARAM_ILLEGAL("100002", "error.param.illegal", "参数不合法"),
    /**
     * 参数超出限定范围
     */
    ERR_PARAM_OUT_RANGE("100003", "error.param.out.range", "参数超出限定范围"),
    /**
     * 上传附件类型不符合规范
     */
    ERR_FILE_SUFFIX_EXIT("100004", "error.file.suffix", "上传附件类型不符合规范"),
    /**
     * 小哥工号必须是本地区的
     */
    ERR_CHECK_NON_PERMISSION_REGION("100005", "error.no.permission.region", "小哥工号必须是本地区的"),
    /**
     * 数据库记录重复
     */
    ERR_CONSTRAINT_VIOLATION("100006", "error.constraint.violation", "数据库记录重复"),
    /**
     * 您无权限做此操作
     */
    ERR_CHECK_PERM_OPERATION("100007", "error.no.permission.operation", "您无权限做此操作"),

    ERR_DESERIALIZATION_FAIL("999995", "error.deserialization.fail", "返回值反序列化失败"),
    ERR_AUTHENTICATION_FAIL("999996", "error.authentication.fail", "鉴权失败"),
    ERR_RPC_TIMEOUT("999997", "error.rpc.timeout", "远程服务调用超时"),
    ERR_RPC_FAIL("999998", "error.rpc.fail", "网络异常"),
    ERR_UNKNOWN("999999", "error.unknown", "系统繁忙，请稍后再试"),
    /**
     * 服务不可用时网关层返回异常码
     */
    RPC_SERVER_UN_AVAILABLE_CODE("09020501", "read timed out", "服务不可用，请稍后再试");
    ;


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

    DCResponseResultCodeEnum(String code, String msg, String desc) {
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
