package com.litian.dancechar.core.common.constants;

/**
 * 公共的常量类
 *
 * @author tojson
 * @date 2022/7/28 10:33
 */
public class CommConstants {
    /**
     * redis防穿透默认空值
     */
    public static final String REDIS_NULL_DEFAULT_VALUE = "empty";

    /**
     * redis防穿，默认时间为2分钟
     */
    public static final long REDIS_NULL_DEFAULT_time = 120000L;

    /**
     * kafka信息
     */
    public static class KafkaTopic{
        /**
         * 订单主题信息
         */
        public static final String TOPIC_ORDER_INFO = "topic_order_info";

        /**
         * 活动变更主题信息
         */
        public static final String TOPIC_ACT_CHANGE_INFO = "topic_act_change_info";

        /**
         * 积分新增主题信息
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
