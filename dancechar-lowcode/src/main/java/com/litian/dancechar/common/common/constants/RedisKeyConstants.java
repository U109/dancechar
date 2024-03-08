package com.litian.dancechar.common.common.constants;

/**
 * 类描述: redis key 常量类
 *
 * @author 01406831
 * @date 2021/04/10 10:32
 */
public class RedisKeyConstants {

    /**
     * redis topic key定义
     */
    public static class Topic {

        public static final String REDIS_TOPIC ="redis:topic";
    }

    /**
     * demo业务redis key定义
     */
    public static class Demo {
        /**
         * demo list缓存
         */
        public static final String DEMO_LIST = "demo:list";

        /**
         * demo分布式锁前缀
         */
        public static final String DEMO_DISTRIBUTE_LOCK_PREFIX = "demo:lock:";
    }

    /**
     * demo业务redis key定义
     */
    public static class SystemDBInfo {
        /**
         * demo list缓存
         */
        public static final String LIST = "db:list";

        /**
         * systemDBInfo分布式锁前缀
         */
        public static final String DISTRIBUTE_LOCK_PREFIX = "sysdb:lock:";
    }

}
