package com.litian.dancechar.framework.delaymsg.jdk;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.litian.dancechar.framework.common.trace.TraceHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 基于jdk DelayQueue初始化队列监听
 *
 * @author tojson
 * @date 2022/09/28 23:53
 */
@Slf4j
@Component
public class JDKDelayedQueueInit implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        startThread(applicationContext.getBeansOfType(JDKDelayedQueueListener.class));
    }

    /**
     * 启动线程获取队列
     * @param map     任务回调监听
     */
    private <T> void startThread(Map<String, JDKDelayedQueueListener> map) {
        //由于此线程需要常驻，可以新建线程，不用交给线程池管理
        Thread thread = new Thread(() -> {
            log.info("启动监听JDK延时队列线程!!！");
            while (true) {
                for (Map.Entry<String, JDKDelayedQueueListener> taskEventListenerEntry : map.entrySet()) {
                    String queueName = taskEventListenerEntry.getValue().getClass().getSimpleName();
                    try {
                        JDKDelayTask<T> jdkDelayTask = JDKDelayQueue.delayQueue.poll();
                        if(ObjectUtil.isNull(jdkDelayTask)){
                            try{
                                // 睡眠一下，防止竞争太激烈, 对CPU产生影响
                                Thread.sleep(1000);
                            }catch (Exception e){
                                log.error(e.getMessage(),e);
                            }
                            continue;
                        }
                        T t = jdkDelayTask.getBody();
                        if(t == null){
                            continue;
                        }
                        // 重新设置traceId
                        TraceHelper.getCurrentTrace();
                        long start = System.currentTimeMillis();
                        log.info("开始执行业务逻辑，queueName:{},data:{}",queueName,JSONUtil.toJsonStr(t));
                        taskEventListenerEntry.getValue().execute(t);
                        log.info("执行业务逻辑结束, 总耗时:{}ms", System.currentTimeMillis() - start);
                    } catch (Exception e) {
                        log.error("启动监听JDK延时队列线程系统异常! errMsg:{}", e.getMessage() ,e);
                    }
                }
            }
        });
        thread.setName("jdk-delayqueue");
        thread.setDaemon(true);
        thread.start();
    }
}
