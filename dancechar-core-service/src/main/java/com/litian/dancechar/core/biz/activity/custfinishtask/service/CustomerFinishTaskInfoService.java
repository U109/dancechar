package com.litian.dancechar.core.biz.activity.custfinishtask.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.litian.dancechar.core.biz.activity.custfinishtask.dao.entity.CustomerFinishTaskInfoDO;
import com.litian.dancechar.core.biz.activity.custfinishtask.dao.inf.CustomerFinishTaskInfoDao;
import com.litian.dancechar.core.biz.activity.custfinishtask.dto.CustomerFinishTaskInfoReqDTO;
import com.litian.dancechar.core.biz.activity.custfinishtask.dto.CustomerFinishTaskInfoRespDTO;
import com.litian.dancechar.core.biz.activity.custfinishtask.dto.CustomerFinishTaskInfoSaveDTO;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客户完成任务信息服务
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class CustomerFinishTaskInfoService extends ServiceImpl<CustomerFinishTaskInfoDao, CustomerFinishTaskInfoDO> {
    @Resource
    private CustomerFinishTaskInfoDao customerFinishTaskInfoDao;

    /**
     * 功能：根据Id-查询客户完成任务信息
     */
    public CustomerFinishTaskInfoDO findById(String id) {
        return customerFinishTaskInfoDao.selectById(id);
    }

    /**
     * 功能：查询客户完成任务信息列表
     */
    public List<CustomerFinishTaskInfoRespDTO> findList(CustomerFinishTaskInfoReqDTO req) {
        return customerFinishTaskInfoDao.findList(req);
    }

    /**
     * 功能：新增客户完成任务信息
     */
    public RespResult<Boolean> insert(CustomerFinishTaskInfoSaveDTO req) {
        LambdaQueryWrapper<CustomerFinishTaskInfoDO> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(CustomerFinishTaskInfoDO::getCustomerId, req.getCustomerId());
        lambdaQueryWrapper.eq(CustomerFinishTaskInfoDO::getActNo, req.getActNo());
        lambdaQueryWrapper.eq(CustomerFinishTaskInfoDO::getTaskCode, req.getTaskCode());
        if(CollUtil.isNotEmpty(customerFinishTaskInfoDao.selectList(lambdaQueryWrapper))){
            return RespResult.error("您已完成过该任务，请勿重复点击");
        }
        CustomerFinishTaskInfoDO customerFinishTaskInfoDO = new CustomerFinishTaskInfoDO();
        DCBeanUtil.copyNotNull(customerFinishTaskInfoDO, req);
        return RespResult.success(customerFinishTaskInfoDao.insert(customerFinishTaskInfoDO) > 0);
    }
}