package com.litian.dancechar.member.common.constants;

/**
 * 公共的常量类
 *
 * @author tojson
 * @date 2022/7/28 10:33
 */
public class CommConstants {

    /**
     * 本地缓存
     */
    public static class LocalCache{
        /**
         * 例子
         */
        public static  final String EXAMPLE = "example";
    }

    /**
     * kafka信息
     */
    public static class KafkaTopic{
        /**
         * 积分主题信息
         */
        public static final String TOPIC_ADD_INTEGRAL = "topic_integral";
        /**
         * 积分处理完成主题信息
         */
        public static final String TOPIC_INTEGRAL_FINISH = "topic_integral_finish";
        /**
         * 客户主题信息
         */
        public static final String TOPIC_CUSTOMER_INFO = "topic_add_customer_info";
    }
}
