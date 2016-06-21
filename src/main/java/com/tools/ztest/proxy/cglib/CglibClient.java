package com.tools.ztest.proxy.cglib;


public class CglibClient {
    
    public static void main(String[] args) {
        CglibDynamicProxy proxy = new CglibDynamicProxy();
        CglibRealSubject subject = (CglibRealSubject)proxy.getProxyInstance(new CglibRealSubject());
        subject.visit();
        System.out.println(subject.doTest());
    }
}
