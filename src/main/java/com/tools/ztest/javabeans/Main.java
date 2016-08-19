package com.tools.ztest.javabeans;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/17 下午3:03
 */
public class Main {
    public static void main(String[] args) {
        // 创建两个对象,并引用
        MyObject myObject1 = new MyObject();
        MyObject myObject2 = new MyObject();
        // 两个对象之间相互引用
        myObject1.nextMyObject = myObject2;
        myObject2.nextMyObject = myObject1;
        // 将最初的两个引用置为null
        myObject1 = null;
        myObject2 = null;
    }
}
