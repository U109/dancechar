package com.litian.dancechar.framework.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述: 返回结果类
 *
 * @author 01406831
 * @date 2021/04/12 19:23
 */
@ApiModel("基础返回对象")
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("请求id,默认null,未启用")
    private String requestId;
    @ApiModelProperty(value = "是否成功,true成功,false失败", example = "true")
    private boolean success = false;
    @ApiModelProperty("业务编码,默认null,未启用")
    private Integer business;
    @ApiModelProperty("错误码")
    private String errorCode;
    @ApiModelProperty("错误信息")
    private String errorMessage;
    @ApiModelProperty("请求参数,默认null,未启用")
    private List<String> params;
    @ApiModelProperty(value = "当前时间", example = "2021-9-15 10:43:21")
    private String date;
    @ApiModelProperty("版本号,默认null,未启用")
    private String version;
    @ApiModelProperty("结果")
    private T obj;

    public Result() {
    }

    public Result(boolean success) {
        this.success = success;
    }

    public Result(String requestId, boolean success) {
        this.requestId = requestId;
        this.success = success;
    }

    public boolean isNotSuccess() {
        return !isSuccess();
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getBusiness() {
        return this.business;
    }

    public void setBusiness(Integer business) {
        this.business = business;
    }

    public T getObj() {
        return this.obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        if (errorCode != null && !"".equals(errorCode)) {
            this.setSuccess(false);
            this.setErrorMessage("unknown error");
        }
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getParams() {
        return this.params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

}
