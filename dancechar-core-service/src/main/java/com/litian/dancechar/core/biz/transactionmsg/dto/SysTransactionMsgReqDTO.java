package com.litian.dancechar.core.biz.transactionmsg.dto;

import com.litian.dancechar.framework.common.base.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 本地事务消息请求对象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysTransactionMsgReqDTO extends BasePage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 业务唯一id(主键或code)
     */
    private String businessId;

    /**
     * 业务内容
     */
    private String businessContent;

    /**
     * 消息状态
     */
    private Integer msgStatus;

    /**
     * 重试次数
     */
    private Integer retryTimes;

    /**
     * 最大重试次数
     */
    private Integer maxRetryTimes;

    /**
     * 备注
     */
    private String remark;
}