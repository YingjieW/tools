package com.tools.ztest.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/6 下午12:21
 */
@Target({ METHOD, FIELD, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {ByteSizeValidator.class})
public @interface ByteSize {

    String value() default "";

    String message() default "参数长度不满足约束";

    Class<? extends Payload>[] payload() default {};
    Class<?>[] groups() default {};

    // inclusive
    int min() default 0;

    // inclusive
    int max() default Integer.MAX_VALUE;

    // 如果为空,则取JVM得编码格式
    String charset() default "";

    @Target({ METHOD, FIELD, PARAMETER })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface list {
        ByteSize[] value();
    }
}
