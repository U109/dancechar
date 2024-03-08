package com.litian.dancechar.biz.sysmgr.feedback.dto;

import com.litian.dancechar.framework.common.base.BasePage;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 意见反馈信息查询请求DTO
 *
 * @author 01406831
 * @since 2021-06-21 10:44:49
 */
@Data
@ApiModel("feedback查询请求DTO")
public class FeedbackQueryReqDTO extends BasePage {
    private static final long serialVersionUID = -59170380558868789L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 意见
     */
    private String suggestion;

    /**
     * 处理状态(0-未处理 1-已处理)
     */
    private Integer dealStatus;
}