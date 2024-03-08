package com.litian.dancechar.member.biz.customer.mq;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.litian.dancechar.member.biz.customer.dao.entity.CustomerInfoDO;
import com.litian.dancechar.member.biz.customer.service.CustomerInfoService;
import com.litian.dancechar.member.common.constants.CommConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


/**
 * 消费新增客户消息
 *
 * @author tojson
 * @date 2022/9/18 23:13
 */
@Slf4j
@Component
public class ConsumerCustomerAddKafkaMsg {
    @Resource
    private CustomerInfoService customerInfoService;

    /**
     * 逻辑确认完成后，手工确认消息containerFactory = "batchFactory"
     */
    @KafkaListener(groupId="dancechar-member-service", topics = {CommConstants.KafkaTopic.TOPIC_CUSTOMER_INFO})
    public void consumerCustomerInfo(List<ConsumerRecord<?,?>> consumerRecords, Acknowledgment ack) {
        log.info("消费新增客户消息大小！size:{}", consumerRecords.size());
        if(CollUtil.isEmpty(consumerRecords)){
            return;
        }
        List<CustomerInfoDO> customerInfoDOList = Lists.newArrayList();
        for(ConsumerRecord<?,?> record : consumerRecords) {
            String value = (String)record.value();
            CustomerInfoDO customerInfoDO = JSON.parseObject(value, CustomerInfoDO.class);
            customerInfoDOList.add(customerInfoDO);
        }
        try{
            customerInfoService.saveBatch(customerInfoDOList);
            ack.acknowledge();
        }catch (Exception e){
            log.error("消费新增客户消息系统异常！errMsg:{}", e.getMessage(),e);
        }
    }
}
