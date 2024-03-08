package com.litian.dancechar.framework.delaymsg.redis.zset;

import cn.hutool.core.collection.CollUtil;
import com.litian.dancechar.framework.cache.redis.util.RedisHelper;
import com.litian.dancechar.framework.common.trace.TraceHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * 基于redis zset初始化队列监听
 *
 * @author tojson
 * @date 2022/09/28 23:53
 */
@Slf4j
@Component
public class RedisZSetDelayedQueueInit implements ApplicationContextAware {

    @Resource
    private RedisHelper redisHelper;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, RedisZSetDelayedQueueListener> map = applicationContext.getBeansOfType(RedisZSetDelayedQueueListener.class);
        startThread(map);
    }

    /**
     * 启动线程获取队列
     */
    private <T> void startThread(Map<String, RedisZSetDelayedQueueListener> map) {
        //由于此线程需要常驻，可以新建线程，不用交给线程池管理
        Thread thread = new Thread(() -> {
            log.info("启动监听zset延时队列线程!!！");
            while (true) {
                for (Map.Entry<String, RedisZSetDelayedQueueListener> taskEventListenerEntry : map.entrySet()) {
                    String queueName = taskEventListenerEntry.getValue().getClass().getSimpleName();
                    try {
                        Set<String> set = redisHelper.zSetRangeByScore(queueName, 0 ,System.currentTimeMillis(), 0 ,1);
                        if(CollUtil.isEmpty(set)){
                            try{
                                // 睡眠一下，防止竞争太激烈, 对CPU产生影响
                                Thread.sleep(1000);
                            }catch (Exception e){
                                log.error(e.getMessage(),e);
                            }
                            continue;
                        }
                        String data = set.iterator().next();
                        if(redisHelper.zSetRemove(queueName ,data) <= 0){
                            continue;
                        }
                        // 重新设置traceId
                        TraceHelper.getCurrentTrace();
                        long start = System.currentTimeMillis();
                        log.info("开始执行业务逻辑，data:{}", data);
                        taskEventListenerEntry.getValue().execute(data);
                        log.info("执行业务逻辑结束, 总耗时:{}ms", System.currentTimeMillis() - start);
                    } catch (Exception e) {
                        log.error("启动监听zset延时队列线程系统异常! errMsg:{}", e.getMessage() ,e);
                    }
                }
            }
        });
        thread.setName("zset-delayqueue");
        thread.setDaemon(true);
        thread.start();
    }
}
