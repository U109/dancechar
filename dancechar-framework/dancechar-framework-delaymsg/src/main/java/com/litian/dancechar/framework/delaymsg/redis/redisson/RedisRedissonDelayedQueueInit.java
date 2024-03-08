package com.litian.dancechar.framework.delaymsg.redis.redisson;

import cn.hutool.core.util.ObjectUtil;
import com.litian.dancechar.framework.common.trace.TraceHelper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;


/**
 * 基于redis redisson 初始化队列监听
 *
 * @author tojson
 * @date 2022/09/28 23:53
 */
@Slf4j
@Component
public class RedisRedissonDelayedQueueInit implements ApplicationContextAware {
    @Resource
    private RedissonClient redissonClient;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        startThread(applicationContext.getBeansOfType(RedisRedissonDelayedQueueListener.class));
    }

    /**
     * 启动线程获取队列
     */
    private <T> void startThread(Map<String, RedisRedissonDelayedQueueListener> map) {
        Thread thread = new Thread(() -> {
            log.info("启动监听redisson延时队列线程!!！");
            while (true) {
                for (Map.Entry<String, RedisRedissonDelayedQueueListener> taskEventListenerEntry : map.entrySet()) {
                    String queueName = taskEventListenerEntry.getValue().getClass().getSimpleName();
                    try {
                        RBlockingQueue<T> blockingFairQueue = redissonClient.getBlockingQueue(queueName);
                        T t = blockingFairQueue.poll();
                        if(ObjectUtil.isNull(t)){
                            try{
                                // 睡眠一下，防止竞争太激烈, 对CPU产生影响
                                Thread.sleep(1000);
                            }catch (Exception e){
                                log.error(e.getMessage(),e);
                            }
                            continue;
                        }
                        // 重新设置traceId
                        TraceHelper.getCurrentTrace();
                        long start = System.currentTimeMillis();
                        log.info("开始执行业务逻辑，data:{}", t);
                        taskEventListenerEntry.getValue().execute(t);
                        log.info("执行业务逻辑结束, 总耗时:{}ms", System.currentTimeMillis() - start);
                    } catch (Exception e) {
                        log.error("启动监听redisson延时队列线程系统异常! errMsg:{}", e.getMessage() ,e);
                    }
                }
            }
        });
        thread.setName("redisson-delayqueue");
        thread.setDaemon(true);
        thread.start();
    }
}
