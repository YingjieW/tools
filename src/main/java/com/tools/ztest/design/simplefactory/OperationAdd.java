package com.tools.ztest.design.simplefactory;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/17 下午9:37
 */
public class OperationAdd extends Operation{
    @Override
    public double getResult() {
        return getNumberA() + getNumberB();
    }
}
