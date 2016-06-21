package com.tools.ztest.proxy.jdk;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/5/24 下午3:59
 */
public class JdkRealSubject implements JdkInterface {

    public void print(String text) {
        System.out.println("###   text: " + text);
    }

    public void visit() {
        System.out.println("I am 'RealSubject',I am the execution method");
    }

    public String doTest() {
        System.out.println("# 3 $ 4 test.");
        return "Ce Shi Ni Mei.";
    }
}
