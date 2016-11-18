package com.tools.ztest.design.simplefactory;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/17 下午9:41
 */
public class Client {
    public static void main(String[] args) {
        Operation operation = OperationFactory.createOperation("/");
        operation.setNumberA(10d);
        operation.setNumberB(4d);
        System.out.println(operation.getResult());
    }
}
