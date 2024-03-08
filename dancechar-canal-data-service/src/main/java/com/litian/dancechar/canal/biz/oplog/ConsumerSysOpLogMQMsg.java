package com.litian.dancechar.canal.biz.oplog;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.otter.canal.protocol.FlatMessage;
import com.litian.dancechar.canal.common.constants.CommConstants;
import com.litian.dancechar.framework.es.util.ElasticsearchUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 消费操作日志MQ
 *
 * @author tojson
 * @date 2022/9/18 23:13
 */
@Slf4j
@Component
public class ConsumerSysOpLogMQMsg {
    @Resource
    private ElasticsearchUtil elasticsearchUtil;

    @KafkaListener(groupId="dancechar-canal-data-service", topics = {CommConstants.KafkaTopic.TOPIC_SYS_OP_LOG})
    public void consumerCustomer(ConsumerRecord<Integer, String> record) {
        log.info("消费操作日志消息！topic:{},partition:{},value:{}",record.topic(),record.partition(), record.value());
        FlatMessage flatMessage = JSONUtil.toBean(record.value(), FlatMessage.class);
        if(ObjectUtil.isNotNull(flatMessage)){
            String type = flatMessage.getType();
            List<Map<String, String>> result = flatMessage.getData();
            if(CollUtil.isNotEmpty(result)){
                Map<String, String> dataMap = result.get(0);
                SysOpLogDO sysOpLogDO = new SysOpLogDO();
                BeanUtil.fillBeanWithMap(dataMap, sysOpLogDO, true);
                dealData(type, sysOpLogDO);
            }
        }
    }

    private void dealData(String type, SysOpLogDO sysOpLogDO){
        if("INSERT".equals(type)){
            elasticsearchUtil.addData(sysOpLogDO, CommConstants.EsIndex.INDEX_SYS_OP_LOG,sysOpLogDO.getId());
            log.info("新增记录成功！docId:{}", sysOpLogDO.getId());
        }else if("UPDATE".equals(type)){
            elasticsearchUtil.updateDataById(sysOpLogDO, CommConstants.EsIndex.INDEX_SYS_OP_LOG, sysOpLogDO.getId());
            log.info("修改记录成功！docId:{}", sysOpLogDO.getId());
        }else if("DELETE".equals(type)){
            elasticsearchUtil.deleteDataById(CommConstants.EsIndex.INDEX_SYS_OP_LOG, sysOpLogDO.getId());
            log.info("删除记录成功！docId:{}", sysOpLogDO.getId());
        }
        log.info("操作完成后查询es记录!{}", elasticsearchUtil.searchDataById(CommConstants.EsIndex.INDEX_SYS_OP_LOG, sysOpLogDO.getId()));
    }
}
