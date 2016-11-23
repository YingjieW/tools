package com.tools.ztest.design.abstractfactory2;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/23 下午10:03
 */
public class WinFactory implements GUIFactory{
    @Override
    public Button createButton() {
        return new WinButton();
    }

    @Override
    public Border createBorder() {
        return new WinBorder();
    }
}
