package com.tools.ztest.test;

import java.io.Serializable;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/9/6 下午11:29
 */
public class Animal implements Serializable{

    private static final long serialVersionUID = 3082724054936921805L;

    public int num = 10;
    public int age = 20;
    public int animal;

    public Animal() {}

    public void eat() {
        System.out.println("动物吃饭");
    }

    public static void sleep() {
        System.out.println("动物在睡觉");
    }

    public void run(){
        System.out.println("动物在奔跑");
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAnimal() {
        return animal;
    }

    public void setAnimal(int animal) {
        this.animal = animal;
    }

    private void testPrivateKKK() {}

    public void testPublicKKK() {}
}
