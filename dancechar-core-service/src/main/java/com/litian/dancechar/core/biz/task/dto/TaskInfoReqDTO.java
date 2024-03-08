package com.litian.dancechar.core.biz.task.dto;

import com.litian.dancechar.framework.common.base.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 任务信息请求对象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TaskInfoReqDTO extends BasePage implements Serializable {
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