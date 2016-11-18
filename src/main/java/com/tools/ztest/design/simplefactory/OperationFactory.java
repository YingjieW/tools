package com.tools.ztest.design.simplefactory;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/17 下午9:42
 */
public class OperationFactory {
    public static Operation createOperation(String operate) {
        if ("+".equals(operate)) {
            return new OperationAdd();
        }
        if ("-".equals(operate)) {
            return new OperationSub();
        }
        if ("*".equals(operate)) {
            return new OperationMul();
        }
        if ("/".equals(operate)) {
            return new OperationDiv();
        }
        return null;
    }
}
