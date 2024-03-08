package com.litian.dancechar.core.biz.activity.act20220910.mq;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.litian.dancechar.core.common.constants.CommConstants;
import com.litian.dancechar.framework.kafka.util.KafkaProducerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 生产积分消息
 *
 * @author tojson
 * @date 2022/9/18 23:13
 */
@Slf4j
@Component
public class ProduceIntegralAddKafkaMsg implements InitializingBean, DisposableBean {
    @Resource
    private KafkaProducerUtil kafkaProducerUtil;
    private static final ArrayBlockingQueue<String> messageQueue = new ArrayBlockingQueue<>(1000);

    /**
     * 批量发送记录数
     */
    private static final int BATCH_MESSAGE_LIST_SIZE = 1000;

    /**
     * 批量发送字节长度的大小
     */
    private static final long BATCH_MESSAGE_LENGTH_SIZE = 921600L;

    /**
     * 发送新增积分
     */
    public void sendMessage(String data) {
        // 如果队列满了，直接发送
        if(!messageQueue.offer(data)){
            kafkaProducerUtil.sendMessage(CommConstants.KafkaTopic.TOPIC_ADD_INTEGRAL, Lists.newArrayList(data));
        }
    }

    @Override
    public void destroy() throws Exception {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Thread thread = new Thread(this::init);
        thread.setDaemon(true);
        thread.setName("produce-integral-thread");
        thread.start();
    }

    /**
     * 初始化阻塞队列，到达指定数量批量发送给kafka
     */
    public void init(){
        List<String> messageList = Lists.newArrayList();
        long lengthSize = 0L;
        String msg;
        while(true){
            try{
                while((msg = messageQueue.poll(5000L, TimeUnit.MILLISECONDS)) != null){
                    lengthSize += msg.getBytes().length;
                    messageList.add(msg);
                    if(messageList.size() >= BATCH_MESSAGE_LIST_SIZE || lengthSize >= BATCH_MESSAGE_LENGTH_SIZE){
                        long start = System.currentTimeMillis();
                        kafkaProducerUtil.sendMessage(CommConstants.KafkaTopic.TOPIC_ADD_INTEGRAL, JSONUtil.toJsonStr(messageList));
                        log.info("kafka批量发送消息成功！耗时:{}ms", (System.currentTimeMillis()-start));
                        messageList.clear();
                        lengthSize = 0;
                    }
                }
                if(messageList.size() > 0){
                    long start = System.currentTimeMillis();
                    kafkaProducerUtil.sendMessage(CommConstants.KafkaTopic.TOPIC_ADD_INTEGRAL, JSONUtil.toJsonStr(messageList));
                    log.info("kafka批量发送消息成功！耗时:{}ms", (System.currentTimeMillis()-start));
                    messageList.clear();
                    lengthSize = 0;
                }
            } catch (Exception e){
                log.error("kafka批量发送消息系统异常！topic:{}",CommConstants.KafkaTopic.TOPIC_ADD_INTEGRAL, e);
            }
        }
    }
}
