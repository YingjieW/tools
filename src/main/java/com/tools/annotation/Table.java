package com.tools.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/8/28 下午2:30
 */
@Inherited
@Retention(RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {

    /**
     * 表名
     *
     * @return
     */
    String name();
}
