package com.tools.ztest.javabeans;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/7/14 下午12:19
 */
class Fruit {
    public void myName()
    {
        System.out.println("我是父类  水果...");
    }
}

class Apple extends Fruit {
    @Override
    public void myName()
    {
        System.out.println("我是子类  苹果...");
    }
    public void myMore()
    {
        System.out.println("我是你的小呀小苹果~~~~~~");
    }
}

public class DowncastingTest {

    public static void main(String[] args) {
        //向上转型
        Fruit a = new Apple();
        a.myName();

        // 向下转型,编译和运行皆不会出错(正确的)
        Apple aa = (Apple)a;
        // 向下转型时调用的是子类的
        aa.myName();
        aa.myMore();;

        Fruit f = new Fruit();
        // 不安全的---向下转型,编译无错但会运行会出错
        Apple aaa = (Apple)f;
        aaa.myName();
        aaa.myMore();
    }
}
