package com.litian.dancechar.core.biz.activity.act20220910.mq;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.litian.dancechar.core.biz.activity.itemrecords.dao.entity.ActItemRecordsDO;
import com.litian.dancechar.core.biz.activity.itemrecords.service.ActItemRecordService;
import com.litian.dancechar.core.biz.transactionmsg.dto.SysTransactionMsgReqDTO;
import com.litian.dancechar.core.biz.transactionmsg.enums.TransactionStatusEnum;
import com.litian.dancechar.core.biz.transactionmsg.service.TransactionMsgService;
import com.litian.dancechar.core.common.constants.CommConstants;
import com.litian.dancechar.core.feign.member.integral.dto.IntegralLogFinishInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * 消费积分新增完成消息
 *
 * @author tojson
 * @date 2022/9/18 23:13
 */
@Slf4j
@Component
public class ConsumerIntegralFinishKafkaMsg {
    @Resource
    private TransactionMsgService transactionMsgService;
    @Resource
    private ActItemRecordService actItemRecordService;

    /**
     * 逻辑确认完成后，手工确认消息
     */
    @KafkaListener(groupId="dancechar-core-service", topics = {CommConstants.KafkaTopic.TOPIC_INTEGRAL_FINISH})
    public void consumer1(String value, Acknowledgment ack) {
        log.info("消费积分新增完成消息订阅！topic:{},value:{}",CommConstants.KafkaTopic.TOPIC_INTEGRAL_FINISH, value);
        if(StrUtil.isEmpty(value)){
            return;
        }
        try{
            IntegralLogFinishInfoDTO integralLogFinishInfoDTO = JSON.parseObject(value,
                    IntegralLogFinishInfoDTO.class);
            if(integralLogFinishInfoDTO.getResult() && StrUtil.isNotEmpty(integralLogFinishInfoDTO.getSerialNo())){
                // 说明之前调用新增积分是成功的，这个时候只需要更新客户发送记录和消息补偿表
                ActItemRecordsDO actItemRecordsDO = actItemRecordService.findById(
                        integralLogFinishInfoDTO.getBusinessId());
                if(ObjectUtil.isNull(actItemRecordsDO)){
                    log.warn("消费积分消息后发放记录不存在！businessId:{}", integralLogFinishInfoDTO.getBusinessId());
                    return;
                }
                actItemRecordsDO.setItemSerialNo(integralLogFinishInfoDTO.getSerialNo());
                actItemRecordService.updateById(actItemRecordsDO);
                // 更新补偿表为成功
                SysTransactionMsgReqDTO smReqDTO = new SysTransactionMsgReqDTO();
                smReqDTO.setBusinessType(integralLogFinishInfoDTO.getBusinessType());
                smReqDTO.setBusinessId(integralLogFinishInfoDTO.getBusinessId());
                smReqDTO.setMsgStatus(TransactionStatusEnum.SUCCESS.getCode());
                transactionMsgService.insertOrUpdate(smReqDTO);
            } else{
                // 重复新增积分，更新补偿表为失败，不在进行补偿处理
                if(ObjectUtil.equal(integralLogFinishInfoDTO.getReason(), 10001)){
                    log.warn("消费积分消息后重复新增积分！businessId:{}", integralLogFinishInfoDTO.getBusinessId());
                    SysTransactionMsgReqDTO smReqDTO = new SysTransactionMsgReqDTO();
                    smReqDTO.setBusinessType(integralLogFinishInfoDTO.getBusinessType());
                    smReqDTO.setBusinessId(integralLogFinishInfoDTO.getBusinessId());
                    smReqDTO.setMsgStatus(TransactionStatusEnum.FAIL.getCode());
                    transactionMsgService.insertOrUpdate(smReqDTO);
                }
            }
            ack.acknowledge();
        } catch (Exception e){
            // 这里要配置监控告警，防止程序出问题，无法补偿
            log.error("消费积分新增完成消息订阅系统异常！errMsg:{}",e.getMessage(),e);
        }
    }
}
