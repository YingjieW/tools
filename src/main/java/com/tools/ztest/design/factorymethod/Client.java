package com.tools.ztest.design.factorymethod;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/18 下午10:24
 */
public class Client {
    public static void main(String[] args) {
        Factory factory = new MulFactory();
        Operation operation = factory.createOperation();
        operation.setNumberA(17d);
        operation.setNumberB(3d);
        System.out.println(operation.getResult());
    }
}
