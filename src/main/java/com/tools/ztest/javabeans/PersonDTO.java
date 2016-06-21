package com.tools.ztest.javabeans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/6/17 上午11:01
 */
public class PersonDTO implements Serializable {

    private static final long serialVersionUID = -484375399579394512L;

    private String name;

    private int age;

    private PersonDTO personDTO;

    private Map map;

    private List list;

    public PersonDTO(){}

    public PersonDTO(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public PersonDTO getPersonDTO() {
        return personDTO;
    }

    public void setPersonDTO(PersonDTO personDTO) {
        this.personDTO = personDTO;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
