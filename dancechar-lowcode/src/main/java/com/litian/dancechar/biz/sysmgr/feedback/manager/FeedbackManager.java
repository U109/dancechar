package com.litian.dancechar.biz.sysmgr.feedback.manager;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.biz.sysmgr.feedback.dto.FeedbackDTO;
import com.litian.dancechar.biz.sysmgr.feedback.dto.FeedbackQueryReqDTO;
import com.litian.dancechar.biz.sysmgr.feedback.repository.dataobject.FeedbackDO;
import com.litian.dancechar.biz.sysmgr.feedback.repository.mapper.FeedbackMapper;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.DCObjectUtil;
import com.litian.dancechar.framework.common.util.page.PageResp;
import com.litian.dancechar.framework.common.util.page.PageRespUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 意见反馈信息manager处理
 *
 * @author 01406831
 * @since 2021-06-21 10:44:48
 */
@Component
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class FeedbackManager extends ServiceImpl<FeedbackMapper, FeedbackDO> {

    /**
     * 功能：分页查询列表记录
     */
    public PageResp<FeedbackDTO> listPage(FeedbackQueryReqDTO feedbackQueryReqDTO) {
        PageHelper.startPage(feedbackQueryReqDTO.getPageNo(), feedbackQueryReqDTO.getPageSize());
        QueryWrapper<FeedbackDO> queryWrapper = Wrappers.query();
        if (feedbackQueryReqDTO.getDealStatus() != null) {
            queryWrapper.eq("deal_status", feedbackQueryReqDTO.getDealStatus());
        }
        if (StrUtil.isNotEmpty(feedbackQueryReqDTO.getSuggestion())) {
            queryWrapper.like("suggestion", "%"
                    + feedbackQueryReqDTO.getSuggestion() + "%");
        }
        queryWrapper.orderByDesc("update_date");
        List<FeedbackDO> list = getBaseMapper().selectList(queryWrapper);
        return PageRespUtil.buildPageResult(list, FeedbackDTO.class);
    }

    /**
     * 功能：新增保存记录
     */
    public Boolean save(FeedbackDTO feedbackDTO) {
        FeedbackDO feedbackDO = new FeedbackDO();
        DCBeanUtil.copyNotNull(feedbackDTO, feedbackDO);
        return save(feedbackDO);
    }

    /**
     * 功能：修改记录
     */
    public Boolean update(FeedbackDTO feedbackDTO) {
        FeedbackDO feedbackDO = this.baseMapper.selectById(feedbackDTO.getId());
        if (DCObjectUtil.isNotNull(feedbackDO)) {
            DCBeanUtil.copyNotNull(feedbackDTO, feedbackDO);
            return this.baseMapper.updateById(feedbackDO) > 0;
        }
        return false;
    }

    /**
     * 功能：根据id删除记录
     */
    public Boolean deleteById(FeedbackDTO feedbackDTO) {
        FeedbackDO feedbackDO = this.baseMapper.selectById(feedbackDTO.getId());
        if (DCObjectUtil.isNotNull(feedbackDO)) {
            return super.removeById(feedbackDTO.getId());
        }
        return false;
    }

    /**
     * 功能：根据id获取记录
     */
    public FeedbackDTO getById(Long id) {
        return DCBeanUtil.copyNotNull(this.baseMapper.selectById(id), new FeedbackDTO());
    }
}
