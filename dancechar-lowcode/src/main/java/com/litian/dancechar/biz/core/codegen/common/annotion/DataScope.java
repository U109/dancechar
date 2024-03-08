package com.litian.dancechar.biz.core.codegen.common.annotion;

import java.lang.annotation.*;

/**
 * 数据权限注解
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DataScope {
}
