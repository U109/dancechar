package com.litian.dancechar.framework.common.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import org.springframework.util.DigestUtils;

/**
 * Md5工具类
 *
 * @author tojson
 * @date 2021/6/14 21:42
 */
public class DCMd5Util extends MD5 {
    private static final String salt = "abcd1234";

    /**
     * 功能: 获取traceId
     */
    public static String getTraceId(String source) {
        return SecureUtil.md5(source).substring(2, 16);
    }

    /**
     * 功能：获取md5加密字符串
     * @param str 加密字符串
     * @return 加密后的字符串
     */
    public static String getMd5(String str){
        byte[] pwd = (str + salt).getBytes();
        return DigestUtils.md5DigestAsHex(pwd);
    }

    public static void main(String[] args) {
        System.out.println(getMd5("36373737134ddhdh"));
        System.out.println(getMd5("abddg3373737377"));
    }
}
