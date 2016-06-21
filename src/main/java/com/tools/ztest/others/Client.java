package com.tools.ztest.others;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/4/22 下午2:41
 */
public class Client {

//    Overriding happens at runtime VS Redefining happens at compile time.
//    Overriding means the Object's type dictates which method to use, and not the reference variable type,
//    and this happens in runtime VS Redefining means that the reference variable type determines this,
//     and this is done at compile time.
//    If you replaced static to normal in your example, you should get "a d a" as output.

    public static void main(String[] args) {
        Animal animal = new Animal();
        Dog dog = new Dog();
        animal.doStuff();
        dog.doStuff();
        Animal animalDog = new Dog();
        animalDog.doStuff();

        Animal[] animals = {new Animal(), new Dog(), new Animal()};
        for(Animal a : animals) {
            a.doStuff();
        }
    }
}
