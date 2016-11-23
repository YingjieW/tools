package com.tools.ztest.design.abstractfactory2;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/23 下午10:01
 */
public class OSXButton implements Button{
    @Override
    public void paint() {
        System.out.println("Paint by OSX button.");
    }
}
