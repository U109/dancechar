package com.litian.dancechar.common.common.constants;

import com.litian.dancechar.framework.common.thread.CustomThreadPoolFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 类描述: 线程池常量类
 *
 * @author 01406831
 * @date 2021/05/19 18:03
 */
public class ThreadPoolConstants {

    /**
     * 公共线程池
     */
    public final static ThreadPoolTaskExecutor COMMON_THREAD_POOL_TASK_EXECUTOR = CustomThreadPoolFactory.newFixedThreadPool(32);

}
