package com.litian.dancechar.biz.sysmgr.feedback.service.impl;

import com.litian.dancechar.biz.sysmgr.feedback.dto.FeedbackDTO;
import com.litian.dancechar.biz.sysmgr.feedback.dto.FeedbackQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.feedback.manager.FeedbackManager;
import com.litian.dancechar.biz.sysmgr.feedback.service.FeedbackService;
import com.litian.dancechar.framework.common.base.Result;
import com.litian.dancechar.framework.common.context.ContextHoldUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.DCResultUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * 意见反馈信息服务实现
 *
 * @author 01406831
 * @since 2021-06-21 10:44:48
 */
@Component
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {
    @Resource
    private FeedbackManager feedbackManager;

    private static final String ID_TIPS = "id不能为空";

    @Override
    public Result<PageResp<FeedbackDTO>> listPage(FeedbackQueryReqDTO req) {
        PageResp<FeedbackDTO> pageResp = feedbackManager.listPage(req);
        return DCResultUtil.success(pageResp);
    }

    @Override
    public Result<Boolean> save(FeedbackDTO feedbackDTO) {
        feedbackDTO.setFeedbackUser(ContextHoldUtil.getEmpNum());
        return DCResultUtil.success(feedbackManager.save(feedbackDTO));
    }

    @Override
    public Result<Boolean> deal(FeedbackDTO feedbackDTO) {
        if (DCObjectUtil.isNull(feedbackDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        feedbackDTO.setDealStatus(1);
        return DCResultUtil.success(feedbackManager.update(feedbackDTO));
    }

    @Override
    public Result<Boolean> deleteById(FeedbackDTO feedbackDTO) {
        if (DCObjectUtil.isNull(feedbackDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(feedbackManager.deleteById(feedbackDTO));
    }

    @Override
    public Result<FeedbackDTO> getById(FeedbackDTO feedbackDTO) {
        if (DCObjectUtil.isNull(feedbackDTO.getId())) {
            return DCResultUtil.error(ID_TIPS);
        }
        return DCResultUtil.success(feedbackManager.getById(feedbackDTO.getId()));
    }
}