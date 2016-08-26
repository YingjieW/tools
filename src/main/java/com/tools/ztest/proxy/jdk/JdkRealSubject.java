package com.tools.ztest.proxy.jdk;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/5/24 下午3:59
 */
public class JdkRealSubject implements JdkInterface {

    @Override
    public void visit() {
        System.out.println("*** I am 'RealSubject',I am the execution method");
    }

    @Override
    public String toCapital(String text) {
        return text == null ? null : text.toUpperCase();
    }
}
