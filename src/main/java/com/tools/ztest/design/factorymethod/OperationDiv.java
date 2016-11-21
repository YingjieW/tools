package com.tools.ztest.design.factorymethod;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/17 下午9:40
 */
public class OperationDiv extends Operation {
    @Override
    public double getResult() {
        return getNumberA()/getNumberB();
    }
}
