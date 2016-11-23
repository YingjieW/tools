package com.tools.ztest.design.abstractfactory2;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/23 下午10:05
 */
public class Client {
    public static void main(String[] args) {
        // GUIFactory guiFactory = new WinFactory();
        GUIFactory guiFactory = new OSXFactory();
        Button button = guiFactory.createButton();
        Border border = guiFactory.createBorder();
        button.paint();
        border.doSth();
    }
}
