package com.tools.ztest.test;

import java.util.Date;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/9/7 上午10:04
 */
public class ShallowClone implements Cloneable{

    public String name;

    public int age;

    public Long score;

    public Date date;

    ShallowClone(String name, int age, Long score, Date date) {
        this.name = name;
        this.age = age;
        this.score = score;
        this.date = date;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShallowClone that = (ShallowClone) o;

        if (age != that.age) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (score != null ? !score.equals(that.score) : that.score != null) return false;
        return date != null ? date.equals(that.date) : that.date == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    public static void main(String[] args) throws Exception {
        ShallowClone clone1 = new ShallowClone("clone", 1, 97l, new Date());
        ShallowClone clone2 = (ShallowClone) clone1.clone();

        System.out.println("===> " + (clone1 == clone2));
        System.out.println("===> " + (clone1.equals(clone2)));
        System.out.println("===> " + (clone1.name == clone2.name));
        System.out.println("===> " + (clone1.age == clone2.age));
        System.out.println("===> " + (clone1.score == clone2.score));
        System.out.println("===> " + (clone1.date == clone2.date));
    }
}
