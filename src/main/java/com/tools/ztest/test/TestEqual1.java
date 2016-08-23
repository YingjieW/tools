package com.tools.ztest.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Descripe: http://blog.csdn.net/javazejian/article/details/51348320
 *
 * @author yingjie.wang
 * @since 16/8/23 下午4:19
 */
public class TestEqual1 {

    /**
     * 测试脚本
     */
    public static void main(String[] args) throws Exception {
        List<Parent> parentList = new ArrayList<Parent>();

        Parent parent = new Parent();
        Child child = new Child();

        parentList.add(parent);
        System.out.println("parentList.contains(parent): " + parentList.contains(parent));
        System.out.println("parentList.contains(child): " + parentList.contains(child));

        parentList.clear();
        parentList.add(child);
        System.out.println("parentList.contains(parent): " + parentList.contains(parent));
        System.out.println("parentList.contains(child): " + parentList.contains(child));

        System.out.println("parent.equals(child): " + parent.equals(child));
        System.out.println("child.equals(parent): " + child.equals(parent));
    }

    /**
     * 父类
     */
    static class Parent {
        @Override
        public boolean equals(Object o) {
            return o instanceof Parent;
        }
    }

    /**
     * 子类
     */
    static class Child extends Parent {
        @Override
        public boolean equals(Object o) {
            return o instanceof Child;
        }
    }
}


