package com.litian.dancechar.biz.sysmgr.feedback.dto;

import com.litian.dancechar.framework.common.base.BaseRespDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 意见反馈信息(FcodeFeedback)DTO
 *
 * @author 01406831
 * @since 2021-06-21 10:44:49
 */
@Data
@ApiModel("fcodeFeedbackDTO")
public class FeedbackDTO extends BaseRespDTO implements Serializable {
    private static final long serialVersionUID = 597704242668292716L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 意见
     */
    private String suggestion;
    /**
     * 反馈人
     */
    private String feedbackUser;
    /**
     * 处理状态(0-未处理 1-已处理)
     */
    private Integer dealStatus;
}