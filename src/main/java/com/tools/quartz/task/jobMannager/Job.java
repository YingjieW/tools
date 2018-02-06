package com.tools.quartz.task.jobMannager;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Job{

    /** 定义定时器触发时间 */
    String cronExpression() default "";



}
