package com.tools.ztest.annotation;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/11 下午2:40
 */
@TestAnnotation(name = "type", gid = Long.class)
public class UseAnnotation {

    // 类成员变量注解
    @TestAnnotation(id = 2, name = "variable", gid = Long.class)
    private Integer age;

    // 构造方法注解
    @TestAnnotation(id = 1, name = "constructor", gid = Long.class)
    public UseAnnotation(){}

    // 类方法注解
    @TestAnnotation(id = 3, name = "public method", gid = Long.class)
    public void printer1() {
        System.out.println("===> printer1");
    }

    // 类方法注解
    @TestAnnotation(id = 3, name = "protected method", gid = Long.class)
    protected void printer2() {
        System.out.println("===> printer2");
    }

    // 类方法注解
    @TestAnnotation(id = 3, name = "private method", gid = Long.class)
    private void printer3() {
        System.out.println("===> printer3");
    }

    // 类方法注解
    @TestAnnotation(id = 3, name = "getAge", gid = Long.class)
    public Integer getAge() {
        return this.age;
    }
}
