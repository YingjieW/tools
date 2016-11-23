package com.tools.ztest.design.abstractfactory2;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/23 下午10:03
 */
public class OSXFactory implements GUIFactory{
    @Override
    public Button createButton() {
        return new OSXButton();
    }

    @Override
    public Border createBorder() {
        return new OSXBorder();
    }
}
