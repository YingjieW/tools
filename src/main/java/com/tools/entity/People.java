package com.tools.entity;

import java.io.Serializable;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/3/23 下午5:21
 */
public class People implements Serializable {

    private String id;

    private String name;

    private Integer age;

    public People() {}

    public People(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

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

//    public static void main(String[] args) {
////        People.getSingleton();
//    }
}
