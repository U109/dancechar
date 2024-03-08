package com.litian.dancechar.base.biz.customergroup.enums;

/**
 * 上传状态
 *
 * @author tojson
 * @date 2022/7/9 06:26
 */
public enum UploadStatusEnum {
    /**
     * 待上传
     */
    WAIT_DO(0, "待上传"),
    /**
     * 上传中
     */
    DOING(1, "上传中"),
    /**
     * 上传成功
     */
    SUCCESS(2, "上传成功"),
    /**
     * 上传失败
     */
    FAIL(3, "上传失败"),
    ;
    UploadStatusEnum(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    private Integer code;

    private String name;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
