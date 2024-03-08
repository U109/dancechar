package com.litian.dancechar.es.biz.snowflake.util;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

/**
 * 雪花算法ID生成工具类(解决时钟回拨)
 *
 * @author tojson
 * @date 2022/7/9 06:26
 */
@Slf4j
public class SnowflakeHelper {

    /**
     * 生成带前缀字符串类型的Id
     */
    public static synchronized String genString(String prefix){
        return prefix + genString();
    }

    /**
     * 生成字符串类型的Id
     */
    public static synchronized String genString() {
        return genLong().toString();
    }

    /**
     * 生成long类型的Id
     */
    public static synchronized Long genLong() {
        long time = clock.millis();
        if (lastTime > time) {
            log.warn(String.format("时钟回拨, last time is %d milliseconds, current time is %d milliseconds", lastTime, time));
            try {
                TimeUnit.MILLISECONDS.sleep(lastTime - time);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        if (lastTime == time) {
            if (0L == (sequence = ++sequence & SEQUENCE_MASK)) {
                time = waitUntilNextTime(time);
            }
        } else {
            sequence = 0;
        }
        lastTime = time;
        return ((time - EPOCH) << TIMESTAMP_LEFT_SHIFT_BITS) | (workerId << WORKER_ID_LEFT_SHIFT_BITS) | sequence;
    }

    public static void initWorkerId() {
        InetAddress address = getLocalAddress();
        byte[] ipAddressByteArray = address.getAddress();
        setWorkerId((long) (((ipAddressByteArray[ipAddressByteArray.length - 2] & 0B11) << Byte.SIZE) + (ipAddressByteArray[ipAddressByteArray.length - 1] & 0xFF)));
    }

    /**
     * 设置工作进程Id(分布式环境容易冲突，所以在系统启动的时候workId自动加1，超过1024，归0)
     */
    public static void setWorkerId(final Long workerId) {
        if (workerId < 0L || workerId >= WORKER_ID_MAX_VALUE) {
            throw new RuntimeException("workerId must between [0, 1024)");
        }
        SnowflakeHelper.workerId = workerId;
    }

    private static InetAddress getLocalAddress() {
        try {
            for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); interfaces.hasMoreElements(); ) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                if (addresses.hasMoreElements()) {
                    return addresses.nextElement();
                }
            }
        } catch (Exception e) {
            log.error("Error when getting host ip address: <{}>.", e.getMessage());
            throw new IllegalStateException("Cannot get LocalHost InetAddress, please check your network!");
        }
        return null;
    }

    private static long waitUntilNextTime(final long lastTime) {
        long time = clock.millis();
        while (time <= lastTime) {
            time = clock.millis();
        }
        return time;
    }

    public static final long EPOCH;
    private static final long SEQUENCE_BITS = 6L;
    private static final long WORKER_ID_BITS = 10L;
    private static final long SEQUENCE_MASK = (1 << SEQUENCE_BITS) - 1;
    private static final long WORKER_ID_LEFT_SHIFT_BITS = SEQUENCE_BITS;
    private static final long TIMESTAMP_LEFT_SHIFT_BITS = WORKER_ID_LEFT_SHIFT_BITS + WORKER_ID_BITS;
    private static final long WORKER_ID_MAX_VALUE = 1L << WORKER_ID_BITS;
    private static AbstractSnowflakeClock clock = AbstractSnowflakeClock.systemClock();
    private static long workerId;

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.APRIL, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        EPOCH = calendar.getTimeInMillis();
        initWorkerId();
    }

    private static long sequence;
    private static long lastTime;
}
