package com.litian.dancechar.framework.common.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 类描述：bean工具类(copy属性、beanToMap、mapToBean)
 *
 * @author 01402521
 */
public class DCBeanUtil extends BeanUtil {
    /**
     * 复制source属性到target属性(排除非空和忽略大小写)
     *
     * @param source 数据源类
     * @param target 目标类
     * @return 目标类
     */
    public static <T> T copyNotNull(Object source, T target) {
        copyProperties(source, target, CopyOptions.create().ignoreNullValue().ignoreCase());
        return target;
    }

    /**
     * 复制source属性到target属性(排除非空和忽略大小写, 忽略属性)
     *
     * @param source           数据源类
     * @param target           目标类
     * @param ignoreProperties 忽略属性
     * @return 目标类
     */
    public static <T> T copyNotNull(Object source, T target, String... ignoreProperties) {
        CopyOptions copyOptions = CopyOptions.create().ignoreNullValue().ignoreCase();
        copyProperties(source, target, copyOptions.setIgnoreProperties(ignoreProperties));
        return target;
    }

    /**
     * 集合数据的拷贝  忽略大小写
     *
     * @param sources 数据源类
     * @param target  目标类::new(eg: UserVO::new)
     * @param <S>     数据源泛型
     * @param <T>     目标类泛型
     * @return 返回目标bean集合
     */
    public static <S, T> List<T> copyList(List<S> sources, Supplier<T> target) {
        return copyList(sources, target, true);
    }

    /**
     * @param sources 数据源类
     * @param target  目标类
     * @param <S>     数据源泛型
     * @param <T>     目标类泛型
     * @return 返回目标bean集合
     */
    public static <S, T> List<T> copyList(List<S> sources, Class<T> target) {
        List<T> list = new ArrayList<>();
        if (DCObjectUtil.isEmpty(sources)) {
            return null;
        }
        for (S source : sources) {
            T t = null;
            try {
                t = target.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            copyNotNull(source, t);
            list.add(t);
        }
        return list;
    }

    /**
     * bean copy 忽略null值
     *
     * @param source 数据源类
     * @param clazz  目标类
     * @param <T>    泛型
     * @return 返回目标copy后的bean
     */
    public static <T> T toBeanIgnoreNullValue(Object source, Class<T> clazz) {
        return toBean(source, clazz, CopyOptions.create().setIgnoreNullValue(Boolean.TRUE));
    }

    /**
     * bean copy 根据ignoreNullValue 判断是否忽略null值
     *
     * @param source          数据源类
     * @param clazz           目标类
     * @param ignoreNullValue true-忽略null   false-不忽略null
     * @param <T>             泛型
     * @return 返回目标copy后的bean
     */
    public static <T> T toBeanIgnoreNullValue(Object source, Class<T> clazz, boolean ignoreNullValue) {
        return toBean(source, clazz, CopyOptions.create().setIgnoreNullValue(ignoreNullValue));
    }

    /**
     * @param sources          数据源类
     * @param target           目标类::new(eg: UserVO::new)
     * @param ignoreProperties 不拷贝的的属性列表
     * @param <S>              数据源泛型
     * @param <T>              目标类泛型
     * @return 返回目标bean集合
     */
    public static <S, T> List<T> copyList(List<S> sources, Supplier<T> target, String... ignoreProperties) {
        return copyList(sources, target, null, ignoreProperties);
    }

    /**
     * @param sources    数据源类
     * @param target     目标类::new(eg: UserVO::new)
     * @param ignoreCase 是否忽略大小写 true-忽略大小写  false-不忽略大小写
     * @param <S>        数据源泛型
     * @param <T>        目标类泛型
     * @return 返回目标bean集合
     */
    public static <S, T> List<T> copyList(List<S> sources, Supplier<T> target, boolean ignoreCase) {
        return copyList(sources, target, null, ignoreCase);
    }


    /**
     * 带回调函数的集合数据的拷贝（可自定义字段拷贝规则）
     *
     * @param sources:  数据源类
     * @param target:   目标类::new(eg: UserVO::new)
     * @param callBack: 回调函数
     * @return 返回目标bean集合
     */
    public static <S, T> List<T> copyList(List<S> sources, Supplier<T> target, BeanCopyUtilCallBack<S, T> callBack) {
        return copyList(sources, target, callBack, CopyOptions.create());
    }

    /**
     * @param sources          数据源类
     * @param target           目标类::new(eg: UserVO::new)
     * @param callBack         回调函数 可以根据来源bean属性生成自定义属性
     * @param ignoreProperties 不拷贝的的属性列表
     * @param <S>              数据源泛型
     * @param <T>              目标类泛型
     * @return 返回目标bean集合
     */
    public static <S, T> List<T> copyList(List<S> sources, Supplier<T> target, BeanCopyUtilCallBack<S, T> callBack, String... ignoreProperties) {
        return copyList(sources, target, callBack, CopyOptions.create().setIgnoreProperties(ignoreProperties));
    }

    /**
     * @param sources    数据源类
     * @param target     目标类::new(eg: UserVO::new)
     * @param callBack   回调函数 可以根据来源bean属性生成自定义属性
     * @param ignoreCase 是否忽略大小写 true-忽略大小写  false-不忽略大小写
     * @param <S>        数据源泛型
     * @param <T>        目标类泛型
     * @return 返回目标bean集合
     */
    public static <S, T> List<T> copyList(List<S> sources, Supplier<T> target, BeanCopyUtilCallBack<S, T> callBack, boolean ignoreCase) {
        return copyList(sources, target, callBack, CopyOptions.create().setIgnoreCase(ignoreCase));
    }

    /**
     * @param sources     数据源类
     * @param target      目标类::new(eg: UserVO::new)
     * @param copyOptions 属性复制选项 {@link CopyOptions}
     * @param <S>         数据源泛型
     * @param <T>         目标类泛型
     * @return 返回目标bean集合
     */
    public static <S, T> List<T> copyList(List<S> sources, Supplier<T> target, CopyOptions copyOptions) {
        return copyList(sources, target, null, copyOptions);
    }

    /**
     * @param sources     数据源类
     * @param target      目标类::new(eg: UserVO::new)
     * @param callBack    回调函数 可以根据来源bean属性生成自定义属性
     * @param copyOptions 属性复制选项 {@link CopyOptions}
     * @param <S>         数据源泛型
     * @param <T>         目标类泛型
     * @return 返回目标bean集合
     */
    public static <S, T> List<T> copyList(List<S> sources, Supplier<T> target, BeanCopyUtilCallBack<S, T> callBack, CopyOptions copyOptions) {
        List<T> list = new ArrayList<>();
        for (S source : sources) {
            T t = target.get();
            copyProperties(source, t, copyOptions);
            list.add(t);
            if (callBack != null) {
                // 回调
                callBack.callBack(source, t);
            }
        }
        return list;
    }

}
