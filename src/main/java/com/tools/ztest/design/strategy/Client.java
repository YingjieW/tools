package com.tools.ztest.design.strategy;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/7 下午9:28
 */
public class Client {
    public static void main(String[] args) throws Exception {
        CashContext cashContext1 = new CashContext(CashStrategyEnum.NORMAL);
        System.out.println("---> normal(300): " + cashContext1.getResult(300));
        CashContext cashContext2 = new CashContext(CashStrategyEnum.RETRURN_300_100);
        System.out.println("---> return(300): " + cashContext2.getResult(300));
        CashContext cashContext3 = new CashContext(CashStrategyEnum.REBATE_0_8);
        System.out.println("---> rebate(300): " + cashContext3.getResult(300));
    }
}
