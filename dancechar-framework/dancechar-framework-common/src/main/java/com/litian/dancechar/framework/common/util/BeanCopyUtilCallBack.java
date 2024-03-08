package com.litian.dancechar.framework.common.util;

/**
 * 类描述: 定义bean copy 的函数式接口
 *
 * @author 01396614
 *
 */
@FunctionalInterface
public interface BeanCopyUtilCallBack <S, T> {

    /**
     * 定义默认回调方法
     * @param s  bean copy 来源
     * @param t  目标 bean
     */
    void callBack(S s, T t);
}