package com.litian.dancechar.core.biz.activity.itemrecords.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.core.biz.activity.itemrecords.dao.entity.ActItemRecordsDO;
import com.litian.dancechar.core.biz.activity.itemrecords.dao.inf.ActItemRecordsDao;
import com.litian.dancechar.core.biz.activity.itemrecords.dto.ActItemRecordReqDTO;
import com.litian.dancechar.core.biz.activity.itemrecords.dto.ActItemRecordsRespDTO;
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
 * 活动领取信息服务
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ActItemRecordService extends ServiceImpl<ActItemRecordsDao, ActItemRecordsDO> {
    @Resource
    private ActItemRecordsDao actItemRecordsDao;

    /**
     * 功能: 分页查询活动领取列表
     */
    public RespResult<PageWrapperDTO<ActItemRecordsRespDTO>> listPaged(ActItemRecordReqDTO req) {
        PageHelper.startPage(req.getPageNo(), req.getPageSize());
        PageWrapperDTO<ActItemRecordsRespDTO> pageCommon = new PageWrapperDTO<>();
        PageResultUtil.setPageResult(actItemRecordsDao.findList(req), pageCommon);
        return RespResult.success(pageCommon);
    }

    /**
     * 功能：根据Id-查询活动领取信息
     */
    public ActItemRecordsDO findById(String id) {
        return actItemRecordsDao.selectById(id);
    }

    /**
     * 功能：查询活动领取列表
     */
    public RespResult<List<ActItemRecordsRespDTO>> findList(ActItemRecordReqDTO req) {
        return RespResult.success(actItemRecordsDao.findList(req));
    }

    /**
     * 功能：更新活动领取信息
     */
    public void update(ActItemRecordsDO actItemRecordsDO) {
        ActItemRecordsDO itemRecordsDB = findById(actItemRecordsDO.getId());
        if(ObjectUtil.isNotNull(itemRecordsDB)){
            DCBeanUtil.copyNotNull(itemRecordsDB, actItemRecordsDO);
            this.updateById(itemRecordsDB);
        }
    }

    /**
     * 功能：新增活动领取信息
     */
    public RespResult<String> insert(ActItemRecordsDO actItemRecordsDO) {
        // 同一个人在同一个活动的任务不能重复领取
        LambdaQueryWrapper<ActItemRecordsDO> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(ActItemRecordsDO::getCustomerId, actItemRecordsDO.getCustomerId());
        lambdaQueryWrapper.eq(ActItemRecordsDO::getActNo, actItemRecordsDO.getActNo());
        lambdaQueryWrapper.eq(ActItemRecordsDO::getTaskCode, actItemRecordsDO.getTaskCode());
        if(CollUtil.isNotEmpty(actItemRecordsDao.selectList(lambdaQueryWrapper))){
            return RespResult.error("您已经领取过，请勿重复领取");
        }
        this.save(actItemRecordsDO);
        return RespResult.success(actItemRecordsDO.getId());
    }
}