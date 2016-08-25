package com.tools.ztest.proxy.jdk;

import java.lang.reflect.Method;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/5/24 下午3:59
 */
public class JdkClient {

    public static void main(String[] args) throws Exception {
        // 创建将被代理的对象
        JdkInterface jdkInterface = new JdkRealSubject();
        // 创建代理包装对象
        JdkDynamicProxy jdkDynamicProxy = new JdkDynamicProxy(jdkInterface);
        // 获取代理对象
        JdkInterface proxy = (JdkInterface)jdkDynamicProxy.getProxy();
        proxy.print("******");
        proxy.visit();
        System.out.println(proxy.doTest());

        Method method = proxy.getClass().getMethod("doTest");
        System.out.println("***  " + method.invoke(proxy));
        System.out.println("***  " + method.getDeclaringClass().getName());

        System.exit(0);
    }
}
