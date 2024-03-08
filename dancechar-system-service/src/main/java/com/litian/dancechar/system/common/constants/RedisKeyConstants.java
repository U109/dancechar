package com.litian.dancechar.system.common.constants;

/**
 * redis常量类
 *
 * @author tojson
 * @date 2022/7/28 10:33
 */
public class RedisKeyConstants {

    /**
     * 用户信息
     */
    public static class User{
        /**
         * 用户信息
         */
        public static  final String USER_INFO = "user:info:%s";
        /**
         * 用户信息过期时间
         */
        public static final long USER_INFO_EXPIRE_TIME = 7 * 24 * 3600L;
    }
}
