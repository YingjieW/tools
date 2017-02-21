package com.tools.ztest.design.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/21 下午11:13
 */
public class Composite extends Component{

    private List<Component> childrenList = new ArrayList<Component>();

    public Composite(String name) {
        super(name);
    }

    @Override
    public void add(Component component) {
        childrenList.add(component);
    }

    @Override
    public void remove(Component component) {
        childrenList.remove(component);
    }

    @Override
    public void display(int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("-");
        }
        System.out.println(name);
        for (Component component : childrenList) {
            component.display(depth+2);
        }
    }
}
