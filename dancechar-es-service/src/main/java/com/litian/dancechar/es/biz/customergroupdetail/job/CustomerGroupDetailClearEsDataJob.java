package com.litian.dancechar.es.biz.customergroupdetail.job;


import com.litian.dancechar.es.biz.customergroupdetail.service.CustomerGroupDocumentService;
import com.litian.dancechar.framework.cache.redis.distributelock.core.DistributeLockHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 清理es客群数据job
 *
 * @author tojson
 * @date 2022/8/21 23:25
 */
@Slf4j
@Configuration
@EnableScheduling
public class CustomerGroupDetailClearEsDataJob {
    @Resource
    private CustomerGroupDocumentService customerGroupDocumentService;
    @Resource
    private DistributeLockHelper distributeLockHelper;

    private static final String ES_CUSTOMER_GROUP_DETAIL_LOCK = "clearEsCustomerGroupDetailLock";

    /**
     * 晚上1点低峰期执行
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void clearExpireData() {
        if(!distributeLockHelper.tryLock(ES_CUSTOMER_GROUP_DETAIL_LOCK, TimeUnit.MINUTES, 1)){
            return;
        }
        try{
            customerGroupDocumentService.clearExpireData(null);
        } catch (Exception e){
            log.error("清理客群数据失败！errMsg：{}", e.getMessage(), e);
        } finally {
            distributeLockHelper.unlock(ES_CUSTOMER_GROUP_DETAIL_LOCK);
        }
    }
}
