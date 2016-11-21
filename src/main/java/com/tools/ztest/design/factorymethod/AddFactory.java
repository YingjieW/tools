package com.tools.ztest.design.factorymethod;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/18 下午10:22
 */
public class AddFactory extends Factory {
    @Override
    public Operation createOperation() {
        return new OperationAdd();
    }
}
