package com.tools.ztest.proxy.jdk;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/5/24 下午3:59
 */
public class JdkClient {

    public static void main(String[] args) {
        JdkInterface jdkInterface = new JdkRealSubject();
        JdkDynamicProxy jdkDynamicProxy = new JdkDynamicProxy(jdkInterface);
        JdkInterface proxy = (JdkInterface)jdkDynamicProxy.getProxy();
        proxy.print("******");
        proxy.visit();
        System.out.println(proxy.doTest());
    }
}
