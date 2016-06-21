package com.tools.ztest.proxy.cglib;


public class CglibRealSubject {

    public void visit() {
        System.out.println("I am 'RealSubject',I am the execution method");
    }
    
    public String doTest() {
        System.out.println("# 3 $ 4 test.");
        return "Ce Shi Ni Mei.";
    }
}
