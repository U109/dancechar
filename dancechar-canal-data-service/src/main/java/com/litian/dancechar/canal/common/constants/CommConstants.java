package com.litian.dancechar.canal.common.constants;

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
         * 订单主题信息
         */
        public static final String TOPIC_ORDER_INFO = "topic_order_info";
        /**
         * 积分主题信息
         */
        public static final String TOPIC_INTEGRAL = "topic_integral";
        /**
         * 积分处理完成主题信息
         */
        public static final String TOPIC_INTEGRAL_FINISH = "topic_integral_finish";
        /**
         * 客户主题信息
         */
        public static final String TOPIC_CUSTOMER_INFO = "dancechar_customer_info";
        /**
         * 操作日志主题消息
         */
        public static final String TOPIC_SYS_OP_LOG = "dancechar_sys_op_log";
    }

    /**
     * es索引信息
     */
    public static class EsIndex{
        public static final String INDEX_SYS_OP_LOG = "sysoplog";
    }
}
