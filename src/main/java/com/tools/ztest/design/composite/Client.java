package com.tools.ztest.design.composite;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/21 下午11:14
 */
public class Client {
    public static void main(String[] args) {
        Composite root = new Composite("root");
        root.add(new Leaf("Leaf A"));
        root.add(new Leaf("Leaf B"));

        Composite compositeX = new Composite("Composite X");
        compositeX.add(new Leaf("Leaf XA"));
        compositeX.add(new Leaf("Leaf XB"));
        root.add(compositeX);

        Composite compositeXY = new Composite("Composite XY");
        compositeX.add(new Leaf("Leaf XYA"));
        compositeX.add(new Leaf("Leaf XYB"));
        compositeX.add(compositeXY);

        root.add(new Leaf("Leaf C"));
        root.display(1);
    }
}
