package com.litian.dancechar.gateway.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 黑名单url服务
 *
 * @author tojson
 * @date 2021/6/22 09:51
 */
@Component
public class BlackListService {
    /**
     * 缓存黑名单
     */
    public static List<String> blackListArrayList = Lists.newCopyOnWriteArrayList();

    private static final String SPLIT_TWO = "/**";
    private static final String SPLIT_ONE = "/*";
    private static final char SPLIT_THREE = '/';


    /**
     * 请求是否黑名单
     */
    public boolean isBlackList(String reqUrl) {
        if (StrUtil.isEmpty(reqUrl) || CollUtil.isEmpty(blackListArrayList)) {
            return false;
        }
        for (String v : blackListArrayList) {
            if (StrUtil.endWith(v, SPLIT_TWO) || StrUtil.endWith(v, SPLIT_ONE)) {
                if (StrUtil.containsIgnoreCase(reqUrl, v.substring(0, v.lastIndexOf(SPLIT_THREE) - 1))) {
                    return true;
                }
            }
        }
        return false;
    }
}