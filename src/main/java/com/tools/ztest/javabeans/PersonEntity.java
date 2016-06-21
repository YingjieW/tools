package com.tools.ztest.javabeans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/6/14 下午6:32
 */
public class PersonEntity implements Serializable {

    private static final long serialVersionUID = -2401373486315501895L;

    private String aaa;

    private String name;

    private int age;


    private Float score;

    private PersonDTO personDTO;

    private Map map;

    private List list;

    public PersonEntity(){}

    public PersonEntity(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getAaa() {
        return aaa;
    }

    public void setAaa(String aaa) {
        this.aaa = aaa;
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

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
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
