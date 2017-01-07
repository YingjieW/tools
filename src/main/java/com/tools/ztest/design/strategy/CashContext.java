package com.tools.ztest.design.strategy;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/7 下午9:17
 */
public class CashContext {
    /**
     * 将实例化具体策略的过程由客户端转移到CashContext类中(简单工厂应用)。
     */

    private CashStrategy cashStrategy;

    public CashContext(CashStrategyEnum cashStrategyEnum) {
        switch (cashStrategyEnum) {
            case NORMAL:
                cashStrategy = new CashNormalStrategy();
                break;
            case RETRURN_300_100:
                cashStrategy = new CashReturnStrategy(300, 100);
                break;
            case REBATE_0_8:
                cashStrategy = new CashRebateStrategy(0.8);
                break;
            default:
                throw new RuntimeException("Unknown cashStrategy!");
        }
    }

    public double getResult(double money) {
        return cashStrategy.acceptCash(money);
    }
}
