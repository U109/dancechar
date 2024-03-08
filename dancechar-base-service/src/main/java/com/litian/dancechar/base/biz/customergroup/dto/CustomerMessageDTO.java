package com.litian.dancechar.base.biz.customergroup.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CustomerMessageDTO implements Serializable {

    /**
     * 消息Id
     */
    private String msgId;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 客群code
     */
    private String customerGroupCode;

    /**
     * 过期时间
     */
    private Date expireTime;
}
