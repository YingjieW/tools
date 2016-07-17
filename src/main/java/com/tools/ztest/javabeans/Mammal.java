package com.tools.ztest.javabeans;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/7/13 下午11:44
 */
public abstract class Mammal {

    public Mammal() {
        System.out.println("Mammal.constructor");
    }

    public void test() {
        System.out.println("Mammal.test");
    }

    public void test1() {
        System.out.println("Mammal.test1");
    }

    public abstract void eat();
}
