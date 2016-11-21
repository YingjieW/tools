package com.tools.ztest.design.factorymethod;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/17 下午9:39
 */
public class OperationSub extends Operation {
    @Override
    public double getResult() {
        return getNumberA() - getNumberB();
    }
}
