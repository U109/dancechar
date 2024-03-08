package com.litian.dancechar.framework.common.util;

import cn.hutool.core.map.MapUtil;

import java.util.*;

/**
 * 类描述: map集合工具类
 *
 * @author 01406831
 *
 */
public class DCMapUtil extends MapUtil {

    /**
     * 分割Map
     *
     * @param map 原始数据
     * @param pageSize 每个map的大小
     * @param <K> key的泛型
     * @param <V> value的泛型
     * @return List<Map<K, V>>
     */
    public static <K, V> List<Map<K, V>> splitMap(Map<K, V> map, int pageSize) {
        if (isEmpty(map)) {
            return Collections.emptyList();
        }
        pageSize = pageSize <= 0 ? 1000 : pageSize;
        if (map.size() <= pageSize) {
            return Collections.singletonList(map);
        }

        List<Map<K, V>> newList = new ArrayList<>();
        int j = 0;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (j % pageSize == 0) {
                newList.add(new HashMap<>(pageSize));
            }
            newList.get(newList.size() - 1).put(entry.getKey(), entry.getValue());
            j++;
        }
        return newList;
    }
}
