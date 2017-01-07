package com.tools.ztest.design.strategy;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/7 下午9:09
 */
public class CashNormalStrategy implements CashStrategy {
    @Override
    public double acceptCash(double money) {
        return money;
    }
}
