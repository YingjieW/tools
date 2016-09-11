package com.tools.ztest.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/9/6 下午11:30
 */
public class Cat extends Animal {

    int num = 80;
    static int age = 90;
    public String name = "tomCat";
    private int kkk = 1;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cat cat = (Cat) o;

        if (num != cat.num) return false;
        return name != null ? name.equals(cat.name) : cat.name == null;

    }

    @Override
    public int hashCode() {
        int result = num;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public void eat() {
        System.out.println("猫吃饭");

    }

    public static void sleep() {
        System.out.println("猫在睡觉");
    }

    public void catchMouse() {
        System.out.println("猫在抓老鼠");
    }

    private void testPrivate() {}

    public static void main(String[] args) throws Exception {
        // Animal am = new Cat();
        // 该语句在堆内存中开辟了子类(Cat)的对象
        // 并把栈内存中的父类(Animal)的引用指向了这个Cat对象。
        Animal am = new Cat();
        // 子类Cat重写了父类Animal的非静态成员方法am.eat();的输出结果为：猫吃饭。
        am.eat();
        // 子类重写了父类(Animal)的静态成员方法am.sleep();的输出结果为：动物在睡觉
        am.sleep();
        // 未被子类（Cat）重写的父类（Animal）方法am.run()输出结果为：动物在奔跑
        am.run();
        // 成员变量: 编译看左边(父类),运行看左边(父类)
        System.out.println(am.num);
        System.out.println(am.age);

        System.out.println("------------------------------");
        // 执行强转语句Cat ct = (Cat)am;之后，
        // ct就指向最开始在堆内存中创建的那个Cat类型的对象
        Cat ct = (Cat)am;
        ct.eat();
        ct.sleep();
        ct.run();
        ct.catchMouse();
        System.out.println(ct.num);
        System.out.println(ct.age);

        System.out.println("\t ===========================");
        Class clazz = Cat.class;
        Method[] methods = clazz.getDeclaredMethods();
//        Method[] methods = clazz.getMethods();
        for(Method method : methods) {
//     (public)       System.out.println("===> " + method.getName());
        }
//        Method method = clazz.getDeclaredMethod("testPrivate");
//        Method method = clazz.getDeclaredMethod("testPublickkk");
        Method method = clazz.getMethod("testPrivate");
        System.out.println("---> " + method.getName());

        Field[] fields = clazz.getDeclaredFields();
//        Field[] fields = clazz.getFields();
        for(Field field : fields) {
//            System.out.println("===> " + field.getName());
        }
    }
}
