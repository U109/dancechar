package com.litian.dancechar.es.biz.snowflake.util;

public abstract class AbstractSnowflakeClock {

    /**
     * 创建系统时钟.
     *
     * @return 系统时钟
     */
    public static AbstractSnowflakeClock systemClock() {
        return new SystemClock();
    }

    /**
     * 返回从纪元开始的毫秒数.
     *
     * @return 从纪元开始的毫秒数
     */
    public abstract long millis();

    private static final class SystemClock extends AbstractSnowflakeClock {

        @Override
        public long millis() {
            return System.currentTimeMillis();
        }
    }
}
