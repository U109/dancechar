package com.litian.dancechar.framework.common.util;

import cn.hutool.core.collection.CollectionUtil;

import java.util.Collection;


/**
 * 类描述：集合工具类
 * 集合空判断
 * 空集合生成
 *
 * @author 01402521
 *
 */
public class DCCollectionUtil extends CollectionUtil {

    /**
     * 判断集合是否为空
     * @param collection    集合
     * @return              true-空  false-不为空
     */
   public static boolean isEmpty(Collection<?> collection){
       return CollectionUtil.isEmpty(collection);
   }
}
