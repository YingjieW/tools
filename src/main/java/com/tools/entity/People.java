package com.tools.entity;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/3/23 下午5:21
 */
public class People {

    static {
        System.out.println("--------");
    }
    private static class Inner {
        private static final People people = new People();
        static {
            System.out.println("....." + people);
        }
    }

    public static People getSingleton() {
        return Inner.people;
    }

    private String id;

    private String name;

    private Integer age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public static void main(String[] args) {
//        People.getSingleton();
    }
}
