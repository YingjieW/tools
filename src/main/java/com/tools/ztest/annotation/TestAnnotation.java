package com.tools.ztest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/11 下午2:40
 */
@Target({ElementType.PACKAGE, ElementType.METHOD, ElementType.TYPE, ElementType.FIELD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestAnnotation {

    String name();

    int id() default 0;

    Class<Long> gid();
}
