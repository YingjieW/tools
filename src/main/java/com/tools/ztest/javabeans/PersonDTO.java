package com.tools.ztest.javabeans;

import com.tools.ztest.reflect.enumtype.CommonType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/6/17 上午11:01
 */
public class PersonDTO implements Serializable {

    private static final long serialVersionUID = -484375399579394512L;

    private Integer aaa;

    private String name;

    private int age;

//    private PersonDTO personDTO;

    private List list;

    private LinkedList linkedList;

    private ArrayList arrayList;

    private Set set;

    private HashSet hashSet;

    private TreeSet treeSet;

    private Map map;

    private HashMap hashMap;

    private TreeMap treeMap;

    private BigDecimal bigDecimal;

    private CommonType commonType;

    public PersonDTO(){}

    public PersonDTO(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Integer getAaa() {
        return aaa;
    }

    public void setAaa(Integer aaa) {
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

//    public PersonDTO getPersonDTO() {
//        return personDTO;
//    }
//
//    public void setPersonDTO(PersonDTO personDTO) {
//        this.personDTO = personDTO;
//    }
//
    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public LinkedList getLinkedList() {
        return linkedList;
    }

    public void setLinkedList(LinkedList linkedList) {
        this.linkedList = linkedList;
    }

    public ArrayList getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList arrayList) {
        this.arrayList = arrayList;
    }

    public Set getSet() {
        return set;
    }

    public void setSet(Set set) {
        this.set = set;
    }

    public HashSet getHashSet() {
        return hashSet;
    }

    public void setHashSet(HashSet hashSet) {
        this.hashSet = hashSet;
    }

    public TreeSet getTreeSet() {
        return treeSet;
    }

    public void setTreeSet(TreeSet treeSet) {
        this.treeSet = treeSet;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public HashMap getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap hashMap) {
        this.hashMap = hashMap;
    }

    public TreeMap getTreeMap() {
        return treeMap;
    }

    public void setTreeMap(TreeMap treeMap) {
        this.treeMap = treeMap;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public CommonType getCommonType() {
        return commonType;
    }

    public void setCommonType(CommonType commonType) {
        this.commonType = commonType;
    }
}
