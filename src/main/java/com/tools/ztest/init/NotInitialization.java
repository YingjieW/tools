package com.tools.ztest.init;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/19 下午2:24
 */
public class NotInitialization {
    public static void main(String[] args) throws Exception {
//        System.out.println(ConstClass.HELLOWORLD);
//        System.out.println(SubClass.value);
//        Class clazz1 = SubClass.class; // 不会初始化
        SubClass subClass = new SubClass();
        System.out.println(".....");
        Class clazz2 = Class.forName("com.tools.ztest.init.SubClass");
    }
}
