package com.tools.ztest.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/5 下午9:35
 */
@Target({ElementType.FIELD})   // 约束注解应用的目标元素类型
@Retention(RetentionPolicy.RUNTIME)   // 约束注解应用的时机
@Constraint(validatedBy ={})  // 与约束注解关联的验证器
public @interface ConstraintName{
    String message() default " ";   // 约束注解验证时的输出消息
    Class<?>[] groups() default { };  // 约束注解在验证时所属的组别
    Class<? extends Payload>[] payload() default { }; // 约束注解的有效负载
}
