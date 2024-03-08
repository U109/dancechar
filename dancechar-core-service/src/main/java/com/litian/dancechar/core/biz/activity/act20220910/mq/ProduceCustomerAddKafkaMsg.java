package com.litian.dancechar.core.biz.activity.act20220910.mq;

import com.litian.dancechar.core.common.constants.CommConstants;
import com.litian.dancechar.framework.kafka.util.KafkaProducerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 生产客户消息
 *
 * @author tojson
 * @date 2022/9/18 23:13
 */
@Slf4j
@Component
public class ProduceCustomerAddKafkaMsg {
    @Resource
    private KafkaProducerUtil kafkaProducerUtil;

    /**
     * 发送新增积分
     */
    public void sendMessage(String data){
        kafkaProducerUtil.sendMessage(CommConstants.KafkaTopic.TOPIC_CUSTOMER_INFO, data);
    }
}
