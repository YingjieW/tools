package com.tools.ztest.design.strategy;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/7 下午9:11
 */
public class CashReturnStrategy implements CashStrategy {
    private double moneyCondition = 0.0d;
    private double moneyReturn = 0.0d;

    public CashReturnStrategy(double moneyCondition, double moneyReturn) {
        this.moneyCondition = moneyCondition;
        this.moneyReturn = moneyReturn;
    }

    @Override
    public double acceptCash(double money) {
        if (money >= moneyCondition) {
            return money - moneyReturn;
        }
        return money;
    }
}
