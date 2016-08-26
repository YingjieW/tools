package com.tools.ztest.proxy.jdk;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/26 上午10:04
 */
public class JdkRealSubjectX {

    public void visit() {
        System.out.println("*** I am 'RealSubject',I am the execution method");
    }

    public String toCapital(String text) {
        return text == null ? null : text.toUpperCase();
    }
}
