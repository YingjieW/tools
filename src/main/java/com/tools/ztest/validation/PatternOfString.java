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
 * @since 17/1/6 下午4:50
 */
@Target({ METHOD, FIELD, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {PatternOfStringValidator.class})
public @interface PatternOfString {

    String mustContainText() default "";

    String message() default "this pattern may not be right";

    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};

    @Target({ METHOD, FIELD, ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        PatternOfString[] value();
    }
}
