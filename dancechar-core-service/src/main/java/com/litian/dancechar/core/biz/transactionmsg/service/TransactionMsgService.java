package com.litian.dancechar.core.biz.transactionmsg.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.litian.dancechar.core.biz.transactionmsg.dao.entity.SysTransactionMsgDO;
import com.litian.dancechar.core.biz.transactionmsg.dao.inf.SysTransactionMsgDao;
import com.litian.dancechar.core.biz.transactionmsg.dto.SysTransactionMsgReqDTO;
import com.litian.dancechar.core.biz.transactionmsg.dto.SysTransactionMsgRespDTO;
import com.litian.dancechar.core.biz.transactionmsg.enums.TransactionStatusEnum;
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
 * 事务补偿消息服务
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class TransactionMsgService extends ServiceImpl<SysTransactionMsgDao, SysTransactionMsgDO> {
    @Resource
    private SysTransactionMsgDao sysTransactionMsgDao;

    /**
     * 功能: 分页查询事务补偿消息列表
     */
    public RespResult<PageWrapperDTO<SysTransactionMsgRespDTO>> listPaged(SysTransactionMsgReqDTO req) {
        PageHelper.startPage(req.getPageNo(), req.getPageSize());
        PageWrapperDTO<SysTransactionMsgRespDTO> pageCommon = new PageWrapperDTO<>();
        PageResultUtil.setPageResult(sysTransactionMsgDao.findList(req), pageCommon);
        return RespResult.success(pageCommon);
    }

    /**
     * 功能：根据Id-查询事务补偿消息
     */
    public SysTransactionMsgDO findById(String id) {
        return sysTransactionMsgDao.selectById(id);
    }

    /**
     * 功能：查询事务补偿消息列表
     */
    public List<SysTransactionMsgRespDTO> findList(SysTransactionMsgReqDTO req) {
        return sysTransactionMsgDao.findList(req);
    }

    /**
     * 功能：新增或更新事务补偿消息
     */
    public void insertOrUpdate(SysTransactionMsgReqDTO req) {
        if(StrUtil.hasBlank(req.getBusinessType(), req.getBusinessId())){
            return;
        }
        LambdaQueryWrapper<SysTransactionMsgDO> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(SysTransactionMsgDO::getBusinessType, req.getBusinessType());
        lambdaQueryWrapper.eq(SysTransactionMsgDO::getBusinessId, req.getBusinessId());
        SysTransactionMsgDO sysTransactionMsgDB = sysTransactionMsgDao.selectOne(lambdaQueryWrapper);
        if(ObjectUtil.isNotNull(sysTransactionMsgDB)){
            int retryTimes = Convert.toInt(sysTransactionMsgDB.getRetryTimes(), 0)+1;
            int maxRetryTimes = Convert.toInt(sysTransactionMsgDB.getMaxRetryTimes(), 5);
            if(TransactionStatusEnum.FAIL.getCode().equals(req.getMsgStatus())){
                if(retryTimes > maxRetryTimes){
                    retryTimes = maxRetryTimes;
                }
            }
            DCBeanUtil.copyNotNull(sysTransactionMsgDB, req);
            sysTransactionMsgDB.setRetryTimes(retryTimes);
            sysTransactionMsgDao.updateById(sysTransactionMsgDB);
        }else{
            SysTransactionMsgDO sysTransactionMsgDO = new SysTransactionMsgDO();
            DCBeanUtil.copyNotNull(sysTransactionMsgDO, req);
            sysTransactionMsgDO.setMsgStatus(TransactionStatusEnum.IS_DOING.getCode());
            sysTransactionMsgDO.setRetryTimes(0);
            sysTransactionMsgDO.setMaxRetryTimes(5);
            sysTransactionMsgDao.insert(sysTransactionMsgDO);
        }
    }
}