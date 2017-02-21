package com.tools.ztest.design.composite;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/21 下午11:13
 */
public class Leaf extends Component {

    public Leaf(String name) {
        super(name);
    }

    @Override
    public void add(Component component) {
        System.out.println("Cannot add to a leaf.");
    }

    @Override
    public void remove(Component component) {
        System.out.println("Cannot remove from a leaf.");
    }

    @Override
    public void display(int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("-");
        }
        System.out.println(name);
    }
}
