package com.litian.dancechar.core.biz.activity.acttask.service;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.core.biz.activity.acttask.dao.entity.ActTaskConfigInfoDO;
import com.litian.dancechar.core.biz.activity.acttask.dao.inf.ActTaskConfigInfoDao;
import com.litian.dancechar.core.biz.activity.acttask.dto.ActTaskConfigInfoReqDTO;
import com.litian.dancechar.core.biz.activity.acttask.dto.ActTaskConfigInfoRespDTO;
import com.litian.dancechar.core.biz.activity.acttask.dto.ActTaskConfigInfoSaveDTO;
import com.litian.dancechar.framework.common.base.PageWrapperDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import com.litian.dancechar.framework.common.util.PageResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 活动任务配置信息服务
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ActTaskConfigInfoService extends ServiceImpl<ActTaskConfigInfoDao, ActTaskConfigInfoDO> {
    @Resource
    private ActTaskConfigInfoDao actTaskConfigInfoDao;

    /**
     * 功能: 活动任务信息列表
     */
    public RespResult<PageWrapperDTO<ActTaskConfigInfoRespDTO>> listPaged(ActTaskConfigInfoReqDTO req) {
        PageHelper.startPage(req.getPageNo(), req.getPageSize());
        PageWrapperDTO<ActTaskConfigInfoRespDTO> pageCommon = new PageWrapperDTO<>();
        PageResultUtil.setPageResult(actTaskConfigInfoDao.findList(req), pageCommon);
        return RespResult.success(pageCommon);
    }

    /**
     * 功能：根据Id-查询活动任务信息
     */
    public ActTaskConfigInfoDO findById(String id) {
        return actTaskConfigInfoDao.selectById(id);
    }

    /**
     * 功能：查询某个活动任务信息
     */
    public ActTaskConfigInfoDO getByActNoAndTaskCode(String actNo, String taskCode){
        LambdaQueryWrapper<ActTaskConfigInfoDO> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(ActTaskConfigInfoDO::getActNo, actNo);
        lambdaQueryWrapper.eq(ActTaskConfigInfoDO::getTaskCode, taskCode);
        return actTaskConfigInfoDao.selectOne(lambdaQueryWrapper);
    }

    /**
     * 功能：查询活动任务信息列表
     */
    public List<ActTaskConfigInfoRespDTO> findList(ActTaskConfigInfoReqDTO req) {
        return actTaskConfigInfoDao.findList(req);
    }

    /**
     * 功能：新增活动任务信息
     */
    public boolean insert(ActTaskConfigInfoSaveDTO req) {
        ActTaskConfigInfoDO actTaskConfigInfoDO = new ActTaskConfigInfoDO();
        DCBeanUtil.copyNotNull(actTaskConfigInfoDO, req);
        return actTaskConfigInfoDao.insert(actTaskConfigInfoDO) > 0;
    }

    /**
     * 功能：更新活动任务信息
     */
    public boolean update(ActTaskConfigInfoSaveDTO req) {
        ActTaskConfigInfoDO actTaskConfigInfoDO = this.findById(req.getId());
        if(ObjectUtil.isNotNull(actTaskConfigInfoDO)){
            DCBeanUtil.copyNotNull(actTaskConfigInfoDO, req);
            return actTaskConfigInfoDao.updateById(actTaskConfigInfoDO) > 0;
        }
        return false;
    }
}