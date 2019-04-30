package com.tools.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/3/23 下午5:21
 */
@Setter
@Getter
@ToString
public class People implements Serializable {

    private String id;

    private String name;

    private Integer age;

    private List<Student> studentList;

    public People() {}

    public People(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }


    public static void main(String[] args) throws Exception {
        Student student = new Student("S001", "Tom", 119, "role");
        List<Student> students = new ArrayList<>();
        students.add(student);
        People people = new People("P001", "Mr.Zhou", 19);
        people.setStudentList(students);
        System.out.println(people);
        people = null;
        System.out.println(people);
    }
}
