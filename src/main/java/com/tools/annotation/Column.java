package com.tools.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/8/28 下午2:31
 */
@Retention(RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

    /** 字段名称 */
    String name();
}

