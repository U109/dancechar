package com.litian.dancechar.base.biz.customergroup.mq;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.litian.dancechar.base.biz.customergroup.dto.CustomerGroupDetailEsDTO;
import com.litian.dancechar.base.biz.customergroup.dto.CustomerGroupDetailListEsDTO;
import com.litian.dancechar.base.biz.customergroup.dto.CustomerMessageDTO;
import com.litian.dancechar.base.biz.customergroup.service.CustomerGroupService;
import com.litian.dancechar.base.common.constants.CommConstants;
import com.litian.dancechar.base.common.constants.RedisKeyConstants;
import com.litian.dancechar.framework.cache.redis.util.RedisHelper;
import com.litian.dancechar.framework.common.base.RespResult;
import com.litian.dancechar.framework.common.httpclient.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 消费客群消息
 *
 * @author tojson
 * @date 2022/9/18 23:13
 */
@Slf4j
@Component
public class ConsumerCustomerGroupKafkaMsg {
    @Resource
    private RedisHelper redisHelper;
    @Resource
    private CustomerGroupService customerGroupService;

    /**
     * 逻辑确认完成后，手工确认消息
     */
    @KafkaListener(groupId="dancechar-base-service", topics = {CommConstants.KafkaTopic.TOPIC_CUSTOMER_GROUP_INFO})
    public void consumer(List<String> messageList, Acknowledgment ack) {
        log.info("消费客群消息！topic:{},value:{}",CommConstants.KafkaTopic.TOPIC_CUSTOMER_GROUP_INFO, messageList);
        try{
            if(CollUtil.isEmpty(messageList)){
                return;
            }
            List<CustomerMessageDTO> cmList = Lists.newArrayList();
            for(String value : messageList){
                cmList.add(JSONUtil.toBean(value, CustomerMessageDTO.class));
            }
            Map<String, Object> dataMap = Maps.newHashMap();
            // 按客群对明细进行分组
            Map<String,List<CustomerMessageDTO> > codeMapToList = cmList.stream().collect(Collectors.groupingBy(CustomerMessageDTO::getCustomerGroupCode));
            codeMapToList.forEach((k,v)->{
                for (CustomerMessageDTO customerMessageDTO : v) {
                    // 客群消费完成后，更新客群成功上传人数
                    long result = redisHelper.decrement(RedisKeyConstants.CustomerGroup.CUSTOMER_GROUP_COUNT+k);
                    if(result <= 0){
                        customerGroupService.updateSuccessTotalCount(k);
                        log.info("客群消费完成！customerGroupCode:{}", k);
                    }
                    dataMap.put(customerMessageDTO.getCustomerGroupCode()+customerMessageDTO.getMobile(), "1");
                    if (dataMap.size() == 100) {
                        // 批量执行
                        redisHelper.executeAsyncPipeLinedSetString(dataMap, (customerMessageDTO.getExpireTime().getTime()-System.currentTimeMillis()), TimeUnit.MILLISECONDS);
                        dataMap.clear();
                    }
                }
                // 批量执行的客群小于100
                if(MapUtil.isNotEmpty(dataMap)){
                    redisHelper.executeAsyncPipeLinedSetString(dataMap, (v.get(0).getExpireTime().getTime()-System.currentTimeMillis()), TimeUnit.MILLISECONDS);
                }
            });
            // 向es写入数据
            try{
                CustomerGroupDetailListEsDTO customerGroupDetailListDTO = new CustomerGroupDetailListEsDTO();
                List<CustomerGroupDetailEsDTO> detailDTOS = Lists.newArrayList();
                cmList.forEach(vo->{
                    CustomerGroupDetailEsDTO customerGroupDetailDTO = new CustomerGroupDetailEsDTO();
                    customerGroupDetailDTO.setCustomerGroupCode(vo.getCustomerGroupCode());
                    customerGroupDetailDTO.setMobile(vo.getMobile());
                    customerGroupDetailDTO.setExpireTime(vo.getExpireTime());
                    detailDTOS.add(customerGroupDetailDTO);
                });
                customerGroupDetailListDTO.setDetailDTOS(detailDTOS);
                RespResult<String> respResult =  HttpClientUtil.postJsonWithCustomTimeOutStrResult(CommConstants.Url.CUSTOMER_GROUP_BATCHINSERT_URL,
                        JSONUtil.toJsonStr(customerGroupDetailListDTO), 3000);
                if(respResult.isNotOk()){
                    log.error("向es写入数据失败！");
                }
            } catch (Exception e){
                log.error("向es写入数据失败！errMsg:{}", e.getMessage(), e);
            }
            ack.acknowledge();
        } catch (Exception e){
            log.error(e.getMessage() ,e);
        }
    }
}