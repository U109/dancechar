package com.litian.dancechar.biz.sysmgr.feedback.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litian.dancechar.biz.sysmgr.feedback.repository.dataobject.FeedbackDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 意见反馈信息mapper接口
 *
 * @author 01406831
 * @since 2021-06-21 10:44:48
 */
@Mapper
public interface FeedbackMapper extends BaseMapper<FeedbackDO> {

}