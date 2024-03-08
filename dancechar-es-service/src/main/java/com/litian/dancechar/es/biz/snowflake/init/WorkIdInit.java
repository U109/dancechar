package com.litian.dancechar.es.biz.snowflake.init;

import com.litian.dancechar.es.biz.snowflake.util.SnowflakeHelper;
import com.litian.dancechar.es.common.constants.RedisKeyConstants;
import com.litian.dancechar.framework.cache.redis.distributelock.core.DistributeLockHelper;
import com.litian.dancechar.framework.cache.redis.util.RedisHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 解决分布式环境下，不同的应用节点在高并发workId会重复问题
 *
 * @author tojson
 * @date 2022/7/9 06:26
 */
@Component
public class WorkIdInit {
    @Resource
    private RedisHelper redisHelper;
    @Resource
    private DistributeLockHelper distributeLockHelper;

    @PostConstruct
    public void init() {
        long value = redisHelper.incrByLong(RedisKeyConstants.Snowflake.SNOWFLAKE_WORKID_KEY, 1);
        if (value > 1024L) {
            boolean lock = distributeLockHelper.tryLock(RedisKeyConstants.Snowflake.SNOWFLAKE_WORKID_KEY, TimeUnit.SECONDS, 2);
            if (lock) {
                try {
                    long curCount = redisHelper.getLong(RedisKeyConstants.Snowflake.SNOWFLAKE_WORKID_KEY);
                    if (curCount >= 1024) {
                        value = 0;
                        redisHelper.set(RedisKeyConstants.Snowflake.SNOWFLAKE_WORKID_KEY, String.valueOf(value));
                    } else {
                        value = curCount;
                    }
                } finally {
                    distributeLockHelper.unlock(RedisKeyConstants.Snowflake.SNOWFLAKE_WORKID_KEY);
                }
            }
        }
        SnowflakeHelper.setWorkerId(value);
    }
}
