package com.tools.ztest.enum_test;

import sun.reflect.ReflectionFactory;

import java.lang.reflect.Constructor;

public class Human {
    public void sing(HumanState state) {
        switch (state) {
            case HAPPY:
                singHappySong();
                break;
            case SAD:
                singDirge();
                break;
            default:
                new IllegalStateException("Invalid State: " + state);
        }
    }

    private void singHappySong() {
        System.out.println("When you're happy and you know it ...");
    }

    private void singDirge() {
        System.out.println("Don't cry for me Argentina, ...");
    }

    public static void main(String[] args) throws Exception {
        Constructor cstr = HumanState.class.getDeclaredConstructor(String.class, int.class);

        ReflectionFactory reflection = ReflectionFactory.getReflectionFactory();

        HumanState e = (HumanState) reflection.newConstructorAccessor(cstr).newInstance(new Object[]{"ANGRY", 3});

        System.out.printf("%s = %d\n", e.toString(), e.ordinal());

        Human human = new Human();
        human.sing(e);
    }
}
