package com.tools.ztest.design.factorymethod;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/18 下午10:24
 */
public class MulFactory extends Factory{
    @Override
    public Operation createOperation() {
        return new OperationMul();
    }
}
