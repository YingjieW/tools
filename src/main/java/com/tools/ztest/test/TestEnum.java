package com.tools.ztest.test;

import com.tools.ztest.javabeans.Dog;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/9 下午2:50
 */
public class TestEnum {

    enum Color {
        GREEN(0), YELLOW(1), RED(2);
        private int num;
        Color(int num) {
            this.num = num;
        }
        public int getNum() {
            return this.num;
        }
        public static Color getColor(int num) {
            for(Color color : Color.values()) {
                if(num == color.getNum()) {
                    return color;
                }
            }
            return null;
        }
    }

    private static void changeColor01(Color color) {
        int newNum = (color.getNum() + 1) % 3;
        color = Color.getColor(newNum);
    }

    private static Color changeColor02(Color color) {
        int newNum = (color.getNum() + 1) % 3;
        return Color.getColor(newNum);
    }

    public static void main(String[] args) throws Exception {
        // 方法调用时,只是把对象内存中的地址传给了方法
        TestEnum testEnum = new TestEnum();
        Color color = Color.GREEN;
        System.out.println("color: " + color);
        color = changeColor02(color);
        System.out.println("color: " + color);

        Animal animal = new Animal("animal");
        System.out.println("animal: " + animal);
        changeAnimal(animal);
        System.out.println("animal: " + animal);

        Person person = testEnum. new Person("person");
        System.out.println("person: " + person);
        testEnum.changePerson(person);
        System.out.println("person: " + person);

        Dog dog = new Dog("dog_a");
        System.out.println("dog: " + dog);
//        dog = new Dog("dog_b");
        changeDog(dog);
        System.out.println("dog: " + dog);
    }

    private static void changeDog(Dog dog) {
        dog = new Dog("New_" + dog.toString());
        return;
    }

    static class Animal {
        private String name;
        Animal(String name) {
            this.name = name;
        }
        public String toString() {
            return this.name;
        }
    }

    private static void changeAnimal(Animal animal) {
        animal = new Animal("New_" + animal.toString());
    }

    class Person {
        private String name;
        Person(String name) {
            this.name = name;
        }
        public String toString() {
            return this.name;
        }
    }

    private void changePerson(Person person) {
        person = new Person("New_" + person.toString());
    }

    private static void test() {
        TestEnum testEnum = new TestEnum();
        Person person = testEnum. new Person("person");
    }
}
