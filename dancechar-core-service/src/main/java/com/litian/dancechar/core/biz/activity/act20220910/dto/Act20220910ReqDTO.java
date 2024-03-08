package com.litian.dancechar.core.biz.activity.act20220910.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 中秋活动请求对象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Act20220910ReqDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 活动code
     */
    private String actNo;

    /**
     * 客户Id
     */
    private String customerId;

    /**
     * 任务code
     */
    private String taskCode;

    /**
     * 是否采用mq发送
     */
    private Boolean sendMQ = false;
}