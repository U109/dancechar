package com.litian.dancechar.core.biz.activity.acttask.dto;

import com.litian.dancechar.framework.common.base.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 活动任务配置请求对象
 *
 * @author tojson
 * @date 2022/7/9 06:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ActTaskConfigInfoReqDTO extends BasePage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 活动编码
     */
    private String actNo;

    /**
     * 任务code
     */
    private String taskCode;

    /**
     * 奖励物品类型
     */
    private String itemType;

    /**
     * 奖励物品Id
     */
    private String itemId;

    /**
     * 奖励物品数量
     */
    private Integer itemNum;
}