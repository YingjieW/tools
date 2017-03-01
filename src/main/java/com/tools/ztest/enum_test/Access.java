package com.tools.ztest.enum_test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/3/1 下午10:14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Access {
    // 默认级别是ADMIN
    CommonIdentifier level() default CommonIdentifier.ADMIN;
}
