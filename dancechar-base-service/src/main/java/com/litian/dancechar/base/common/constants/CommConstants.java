package com.litian.dancechar.base.common.constants;

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
     * 请求url管理
     */
    public static class Url{
        /**
         * es 客群明细插入URL
         */
        public static  final String CUSTOMER_GROUP_BATCHINSERT_URL = "http://127.0.0.1:2222/customergroup/detail/batchInsert";
    }



    /**
     * es索引信息
     */
    public static class EsIndex{
        public static final String INDEX_SYS_OP_LOG = "sysoplog";
    }

    /**
     * 文件上传
     */
    public static class UploadFile{
        /**
         * 允许上传的最大文件大小
         */
        public static final long MAX_FILE_SIZE =  400 * 1024 * 1024;

        /**
         * 允许上传的最大文件大小(后续接入动态配置中心)
         */
        public static final long TOTAL_RECORD_LIMIT = 1200 * 10000;

        /**
         * 拆分客群最小的数量
         */
        public static final int  SPLIT_TOTAL_COUNT_MIN =  30 * 10000;
    }

    /**
     * kafka信息
     */
    public static class KafkaTopic{
        /**
         * 客群手机信息
         */
        public static final String TOPIC_CUSTOMER_GROUP_INFO = "topic_customer_group_mobile_info";
    }

}
