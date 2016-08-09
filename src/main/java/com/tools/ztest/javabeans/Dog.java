package com.tools.ztest.javabeans;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/7/13 下午11:45
 */
//@Repository("dog")
public class Dog extends Mammal{

    private String name;

    public Dog() {
    }

    public Dog(String name) {
        this.name = name;
    }

    private int x = 0;
    private int y = 0;
    public void move(int dx, int dy) {
        x = x + dx;
        y = y + dy;
    }

    public String toString() {
//        return "x = " + x + ", y = " + y;
        return this.name;
    }

    public void test() {
        System.out.println("Dog.test");
    }

    @Override
    public void eat() {
        System.out.println("Dog.eat");
    }

    public void bark() {
        System.out.println("Dog.bark");
    }
    public static void main(String[] args) {
        Dog dog = new Dog();
        Mammal mammal = dog;
        mammal.eat();
        dog.move(1, 1);
//        Mammal mammal1 = new Mammal();
//        Dog dog1 = (Dog)mammal;
//        dog1.test();
//        dog1.test1();
//        dog1.bark();
    }
}
