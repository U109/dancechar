package com.litian.dancechar.member.biz.integral.mq;

import com.litian.dancechar.framework.kafka.util.KafkaProducerUtil;
import com.litian.dancechar.member.common.constants.CommConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * 积分新增结束后MQ通知给下游
 *
 * @author tojson
 * @date 2022/9/18 23:13
 */
@Slf4j
@Component
public class ProduceIntegralFinishKafkaMsg {
    @Resource
    private KafkaProducerUtil kafkaProducerUtil;

    /**
     * 生成积分
     * @param data 数据
     */
    public void sendMessage(String data) {
        kafkaProducerUtil.sendMessage(CommConstants.KafkaTopic.TOPIC_INTEGRAL_FINISH, data);
    }
}
