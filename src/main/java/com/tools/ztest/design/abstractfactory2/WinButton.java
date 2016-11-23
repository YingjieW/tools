package com.tools.ztest.design.abstractfactory2;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/23 下午10:00
 */
public class WinButton implements Button{
    @Override
    public void paint() {
        System.out.println("Paint by win button.");
    }
}
