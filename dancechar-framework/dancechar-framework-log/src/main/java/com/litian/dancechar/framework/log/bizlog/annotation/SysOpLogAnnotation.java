package com.litian.dancechar.framework.log.bizlog.annotation;


import java.lang.annotation.*;

/**
 * 系统操作日志注解
 *
 * @author tojson
 * @date 2021/6/14 21:15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface SysOpLogAnnotation {

    /**
     * 菜单名称
     */
    String menuName() default "";

    /**
     * 按钮名称
     */
    String menuBtn() default "";

    /**
     * 操作内容
     */
    String opContent() default "";

    /**
     * 请求参数
     */
    String reqParam() default "";
}
