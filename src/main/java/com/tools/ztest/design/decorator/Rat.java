package com.tools.ztest.design.decorator;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/3/3 下午11:35
 */
public class Rat implements Animal {
    @Override
    public void doStuff() {
        System.out.println("Jerry will play with Tom.");
    }
}
