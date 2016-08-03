package com.tools.ztest.facade.impl;

import org.springframework.stereotype.Service;

@Service("interfaceTest")
public class InterfaceTest {

    public void print() {
        System.out.println("---------> print().");
    }

    public String getStr() {
        System.out.println("---------> In getStr().");
        return "getStr().";
    }

    public static void main(String[] args) {
        new InterfaceTest().print();
    }
}
