package com.tools.ztest.proxy.cglib;


import java.lang.reflect.Method;

public class CglibClient {
    
    public static void main(String[] args) throws Exception {
        // 代理类对象
        CglibDynamicProxy cglibDynamicProxy = new CglibDynamicProxy();
        // 获取代理对象
        CglibRealSubject proxy = (CglibRealSubject)cglibDynamicProxy.getProxyInstance(new CglibRealSubject());

        proxy.visit();

        Method method = proxy.getClass().getMethod("toCapital", String.class);
        System.out.println("*** " + method.invoke(proxy, "hello world."));
        System.out.println("*** " + method.getDeclaringClass().getName());
    }
}
