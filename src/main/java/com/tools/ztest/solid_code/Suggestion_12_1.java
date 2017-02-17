package com.tools.ztest.solid_code;

import java.io.Serializable;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/15 下午10:19
 */
public class Suggestion_12_1 implements Serializable {

    private static final long serialVersionUID = -2951474845235259296L;

    private static final int age = -18;

    private static int score = -1;

    private final String classRoom;

    private String name = null;

    public Suggestion_12_1() {
        classRoom = "default...kkk";
        name = "testing...";
    }

    public static int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        Suggestion_12_1.score = score;
    }

    @Override
    public String toString() {
        return "name:" + name + ", age:" + age + ", score:" + score + ", classRoom:" + classRoom;
    }
}
