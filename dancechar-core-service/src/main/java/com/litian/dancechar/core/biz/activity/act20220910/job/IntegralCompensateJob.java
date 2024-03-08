package com.litian.dancechar.core.biz.activity.act20220910.job;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.litian.dancechar.core.biz.activity.act20220910.enums.ItemTypeEnum;
import com.litian.dancechar.core.biz.activity.itemrecords.dao.entity.ActItemRecordsDO;
import com.litian.dancechar.core.biz.activity.itemrecords.service.ActItemRecordService;
import com.litian.dancechar.core.biz.transactionmsg.dto.SysTransactionMsgReqDTO;
import com.litian.dancechar.core.biz.transactionmsg.dto.SysTransactionMsgRespDTO;
import com.litian.dancechar.core.biz.transactionmsg.enums.TransactionStatusEnum;
import com.litian.dancechar.core.biz.transactionmsg.service.TransactionMsgService;
import com.litian.dancechar.core.feign.member.integral.client.IntegralClient;
import com.litian.dancechar.core.feign.member.integral.dto.IntegralLogInfoReqDTO;
import com.litian.dancechar.core.feign.member.integral.dto.IntegralLogInfoRespDTO;
import com.litian.dancechar.core.feign.member.integral.dto.IntegralLogInfoSaveDTO;
import com.litian.dancechar.framework.cache.redis.distributelock.core.DistributeLockHelper;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.util.DCBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 积分补偿job(基于spring)
 *
 * @author tojson
 * @date 2022/8/21 23:25
 */
@Slf4j
@Configuration
@EnableScheduling
public class IntegralCompensateJob {
    @Resource
    private TransactionMsgService transactionMsgService;
    @Resource
    private IntegralClient integralClient;
    @Resource
    private ActItemRecordService actItemRecordService;
    @Resource
    private DistributeLockHelper distributeLockHelper;

    private static final String INTEGRAL_COMPENSATE_LOCK = "integralCompensateLock";

    @Scheduled(cron = "0 0/1 * * * ?")
    public void integralCompensate() {
        if(!distributeLockHelper.tryLock(INTEGRAL_COMPENSATE_LOCK, TimeUnit.MINUTES, 1)){
            return;
        }
        try{
            SysTransactionMsgReqDTO sysTransactionMsgReqDTO = new SysTransactionMsgReqDTO();
            sysTransactionMsgReqDTO.setBusinessType(ItemTypeEnum.INTEGRAL.getCode());
            List<SysTransactionMsgRespDTO>  transactionMsgList = transactionMsgService.findList(sysTransactionMsgReqDTO);
            if(CollUtil.isNotEmpty(transactionMsgList)){
                for(SysTransactionMsgRespDTO sysTransactionMsg : transactionMsgList){
                    try {
                        IntegralLogInfoReqDTO integralLogInfoReqDTO = new IntegralLogInfoReqDTO();
                        integralLogInfoReqDTO.setBusinessType(sysTransactionMsg.getBusinessType());
                        integralLogInfoReqDTO.setBusinessId(sysTransactionMsg.getBusinessId());
                        RespResult<IntegralLogInfoRespDTO>  isR = integralClient.findByBusinessId(integralLogInfoReqDTO);
                        if(isR.isOk()){
                            if(ObjectUtil.isNotNull(isR.getData())){
                                // 说明之前调用新增积分是成功的，这个时候只需要更新客户发送记录和消息补偿表
                                ActItemRecordsDO actItemRecordsDO = actItemRecordService.findById(sysTransactionMsg.getBusinessId());
                                if(ObjectUtil.isNotNull(actItemRecordsDO)){
                                    actItemRecordsDO.setItemSerialNo(isR.getData().getSerialNo());
                                }
                                actItemRecordService.updateById(actItemRecordsDO);
                                // 更新补偿表为成功
                                SysTransactionMsgReqDTO smReqDTO = new SysTransactionMsgReqDTO();
                                DCBeanUtil.copyNotNull(smReqDTO, sysTransactionMsg);
                                smReqDTO.setMsgStatus(TransactionStatusEnum.SUCCESS.getCode());
                                transactionMsgService.insertOrUpdate(smReqDTO);
                            } else{
                                // 补偿积分记录
                                RespResult<String> integralR = integralClient.add(JSONUtil.toBean(sysTransactionMsg.getBusinessContent(), IntegralLogInfoSaveDTO.class));
                                if(integralR.isOk()){
                                    // 更新客户发放记录
                                    ActItemRecordsDO actItemRecordsDO = actItemRecordService.findById(sysTransactionMsg.getBusinessId());
                                    if(ObjectUtil.isNotNull(actItemRecordsDO)){
                                        actItemRecordsDO.setItemSerialNo(integralR.getData());
                                    }
                                    actItemRecordService.updateById(actItemRecordsDO);
                                    // 更新补偿表为成功
                                    SysTransactionMsgReqDTO smReqDTO = new SysTransactionMsgReqDTO();
                                    DCBeanUtil.copyNotNull(smReqDTO, sysTransactionMsg);
                                    smReqDTO.setMsgStatus(TransactionStatusEnum.SUCCESS.getCode());
                                    transactionMsgService.insertOrUpdate(smReqDTO);
                                } else{
                                    SysTransactionMsgReqDTO smReqDTO = new SysTransactionMsgReqDTO();
                                    DCBeanUtil.copyNotNull(smReqDTO, sysTransactionMsg);
                                    smReqDTO.setMsgStatus(TransactionStatusEnum.FAIL.getCode());
                                    transactionMsgService.insertOrUpdate(smReqDTO);
                                }

                            }
                        }
                    } catch (Exception e){
                        log.error(e.getMessage(),e);
                        SysTransactionMsgReqDTO smReqDTO = new SysTransactionMsgReqDTO();
                        DCBeanUtil.copyNotNull(smReqDTO, sysTransactionMsg);
                        smReqDTO.setMsgStatus(TransactionStatusEnum.FAIL.getCode());
                        transactionMsgService.insertOrUpdate(smReqDTO);
                    }
                }
            }
        } catch (Exception e){
            log.error("积分补偿失败！errMsg：{}", e.getMessage(), e);
        } finally {
            distributeLockHelper.unlock(INTEGRAL_COMPENSATE_LOCK);
        }
    }
}
