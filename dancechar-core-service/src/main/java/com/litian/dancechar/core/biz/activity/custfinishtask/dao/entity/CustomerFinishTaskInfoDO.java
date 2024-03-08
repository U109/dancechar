package com.litian.dancechar.core.biz.activity.custfinishtask.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 客户完成任务信息DO
 *
 * @author tojson
 * @date 2022/9/5 06:18
 */
@Data
@TableName("customer_finish_task_records")
@EqualsAndHashCode(callSuper = false)
public class CustomerFinishTaskInfoDO extends BaseDO implements Serializable {
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