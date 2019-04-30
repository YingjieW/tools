package com.tools.entity;

import com.alibaba.fastjson.JSON;
import lombok.ToString;

import java.lang.reflect.Constructor;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/7/26 下午8:49
 */
@ToString
public class Student extends People {

    private String role;

    public Student() {}

    public Student(String id, String name, int age, String role) {
        super(id, name, age);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static void main(String[] args) throws Exception {
        Class clazz = Student.class;
        Constructor constructor = clazz.getDeclaredConstructor(String.class, String.class, int.class, String.class);
        People people = (People) constructor.newInstance("1", "Tom", 18, "student");
        System.out.println("people: " + JSON.toJSONString(people));
        System.out.println(people.getClass());
        System.out.println(people.getClass().getName());
    }
}
