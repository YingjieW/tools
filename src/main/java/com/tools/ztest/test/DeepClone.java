package com.tools.ztest.test;

import java.io.Serializable;
import java.util.Date;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/9/7 上午10:16
 */
public class DeepClone implements Cloneable, Serializable {

    private static final long serialVersionUID = -9108165455198589805L;

    public String name;

    public int age;

    public Long score;

    public Date date;

    public DeepClone(String name, int age, Long score, Date date) {
        this.name = name;
        this.age = age;
        this.score = score;
        this.date = date;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        DeepClone deepClone = (DeepClone) super.clone();
        deepClone.date = (Date) deepClone.date.clone();
        return deepClone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeepClone deepClone = (DeepClone) o;

        if (age != deepClone.age) return false;
        if (name != null ? !name.equals(deepClone.name) : deepClone.name != null) return false;
        if (score != null ? !score.equals(deepClone.score) : deepClone.score != null) return false;
        return date != null ? date.equals(deepClone.date) : deepClone.date == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "name:[" + this.name + "], age:[" + this.age +
                "], score:[" + this.score + "], date:[" + date.toString() + "].";
    }

    public static void main(String[] args) throws Exception {
        DeepClone clone1 = new DeepClone("ccc", 1, 97l, new Date());
        DeepClone clone2 = (DeepClone) clone1.clone();

        System.out.println("===> " + (clone1 == clone2));
        System.out.println("===> " + (clone1.equals(clone2)));
        System.out.println("===> " + (clone1.name == clone2.name));
        System.out.println("===> " + (clone1.age == clone2.age));
        System.out.println("===> " + (clone1.score == clone2.score));
        System.out.println("===> " + (clone1.date == clone2.date));

        System.out.println();
        System.out.println("===> clone1: " + clone1);
        System.out.println("===> clone2: " + clone2);
        System.out.println();

        Thread.sleep(1000);

        clone1.name = "aaa";
        clone1.age = 2;
        clone1.score = 99l;
        clone1.date = new Date();

        System.out.println("===> clone1: " + clone1);
        System.out.println("===> clone2: " + clone2);

        System.out.println();
        System.out.println("===> " + (clone1 == clone2));
        System.out.println("===> " + (clone1.equals(clone2)));
        System.out.println("===> " + (clone1.name == clone2.name));
        System.out.println("===> " + (clone1.age == clone2.age));
        System.out.println("===> " + (clone1.score == clone2.score));
        System.out.println("===> " + (clone1.date == clone2.date));

    }
}
