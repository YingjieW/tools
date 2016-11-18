package com.tools.ztest.design.simplefactory;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/17 下午9:35
 */
public abstract class Operation {

    protected double numberA = 0d;

    protected double numberB = 0d;

    public double getNumberA() {
        return numberA;
    }

    public void setNumberA(double numberA) {
        this.numberA = numberA;
    }

    public double getNumberB() {
        return numberB;
    }

    public void setNumberB(double numberB) {
        this.numberB = numberB;
    }

    public abstract double getResult();
}
