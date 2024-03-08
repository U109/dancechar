package com.litian.dancechar.biz.sysmgr.feedback.repository.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.litian.dancechar.framework.common.mybatis.BaseDO;
import lombok.*;

import java.io.Serializable;

/**
 * 意见反馈信息实体类
 *
 * @author 01406831
 * @since 2021-06-21 10:44:48
 */
@Data
@TableName("fcode_feedback")
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackDO extends BaseDO implements Serializable {
    private static final long serialVersionUID = 284740870121429184L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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