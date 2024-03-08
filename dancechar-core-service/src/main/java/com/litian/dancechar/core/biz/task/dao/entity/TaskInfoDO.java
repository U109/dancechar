package com.litian.dancechar.core.biz.task.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 任务信息DO
 *
 * @author tojson
 * @date 2022/9/5 06:18
 */
@Data
@TableName("task_info")
@EqualsAndHashCode(callSuper = false)
public class TaskInfoDO extends BaseDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 任务code
     */
    private String taskCode;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务描述
     */
    private String remark;
}