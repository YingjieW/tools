package com.tools.ztest.facade.impl;

import com.tools.ztest.facade.InterfaceOne;
import com.tools.ztest.facade.InterfaceTwo;


public class InterfaceTest implements InterfaceOne, InterfaceTwo {

    public void print() {
        System.out.println("test");
    }
    
    public static void main(String[] args) {
        new InterfaceTest().print();
    }
}
