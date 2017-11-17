package com.tools.ztest.proxy.cglib;


public class CglibRealSubject {
//    public class CglibRealSubject implements CglibInterface{

    public void visit() {
        System.out.println("*** I am 'RealSubject',I am the execution method");
    }

    public String toCapital(String text) {
        return text == null ? null : text.toUpperCase();
    }

    public static String testStaticMethod() {
        System.out.println("*** This is a static method.");
        return "static method...";
    }
}
