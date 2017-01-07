package com.tools.ztest.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.*;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/6 下午6:27
 */
@NotNull
@Size(min = 1, message = " should have at least one charactor.")
@Pattern(regexp = ".*\\S.*", message = " should not be blank.")
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {NotBlankValidator.class}) // hibernate-validator已包含NotBlank
public @interface NotBlank {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
