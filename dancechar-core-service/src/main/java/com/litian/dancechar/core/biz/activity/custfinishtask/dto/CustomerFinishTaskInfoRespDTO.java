package com.litian.dancechar.core.biz.activity.custfinishtask.dto;

import com.litian.dancechar.framework.common.base.BaseRespDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 客户完成任务信息返回对象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerFinishTaskInfoRespDTO extends BaseRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 客户Id
     */
    private String customerId;

    /**
     * 活动编号
     */
    private String actNo;

    /**
     * 任务编码
     */
    private String taskCode;
}