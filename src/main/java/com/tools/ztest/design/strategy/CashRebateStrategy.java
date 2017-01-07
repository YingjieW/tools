package com.tools.ztest.design.strategy;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/7 下午9:16
 */
public class CashRebateStrategy implements CashStrategy {

    private double rebate;

    public CashRebateStrategy(double rebate) {
        this.rebate = rebate;
    }

    @Override
    public double acceptCash(double money) {
        return money * rebate;
    }
}
