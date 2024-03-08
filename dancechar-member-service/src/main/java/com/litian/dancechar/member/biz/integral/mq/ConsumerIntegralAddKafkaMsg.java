package com.litian.dancechar.member.biz.integral.mq;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.member.biz.integral.dto.IntegralLogFinishInfoDTO;
import com.litian.dancechar.member.biz.integral.dto.IntegralLogInfoSaveDTO;
import com.litian.dancechar.member.biz.integral.service.IntegralLogInfoService;
import com.litian.dancechar.member.common.constants.CommConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


/**
 * 消费新增积分消息
 *
 * @author tojson
 * @date 2022/9/18 23:13
 */
@Slf4j
@Component
public class ConsumerIntegralAddKafkaMsg {
    @Resource
    private IntegralLogInfoService integralLogInfoService;
    @Resource
    private ProduceIntegralFinishKafkaMsg produceIntegralFinishKafkaMsg;

    /**
     * 逻辑确认完成后，手工确认消息
     */
    @KafkaListener(groupId="dancechar-member-service", topics = {CommConstants.KafkaTopic.TOPIC_ADD_INTEGRAL})
    public void consumerAddIntegral(String value, Acknowledgment ack) {
        log.info("消费新增积分消息！topic:{} ,value:{}", CommConstants.KafkaTopic.TOPIC_ADD_INTEGRAL, value);
        if(StrUtil.isEmpty(value)){
            return;
        }
        try{
            List<String> integralLogInfoStrList = JSONArray.parseArray(value, String.class);
            integralLogInfoStrList.forEach(integralLogInfoSaveStr->{
                IntegralLogInfoSaveDTO integralLogInfoSaveDTO = JSON.parseObject(integralLogInfoSaveStr,
                        IntegralLogInfoSaveDTO.class);
                IntegralLogFinishInfoDTO integralLogFinishInfoDTO = new IntegralLogFinishInfoDTO();
                integralLogFinishInfoDTO.setCustomerId(integralLogInfoSaveDTO.getCustomerId());
                integralLogFinishInfoDTO.setBusinessType(integralLogInfoSaveDTO.getBusinessType());
                integralLogFinishInfoDTO.setBusinessId(integralLogInfoSaveDTO.getBusinessId());
                RespResult<String> logSaveR = integralLogInfoService.saveWithInsert(integralLogInfoSaveDTO);
                if(logSaveR.isOk()){
                    integralLogFinishInfoDTO.setSerialNo(logSaveR.getData());
                }else{
                    integralLogFinishInfoDTO.setReason(logSaveR.getCode());
                }
                integralLogFinishInfoDTO.setResult(logSaveR.isOk());
                produceIntegralFinishKafkaMsg.sendMessage(JSONUtil.toJsonStr(integralLogFinishInfoDTO));
            });
            ack.acknowledge();
        } catch (Exception e){
            // 保存积分消息到数据库异常，这里不发送失败MQ消息，
            log.error("消费新增积分消息系统异常！value:{}, errMsg:{}", value, e.getMessage(),e);
        }
    }
}
