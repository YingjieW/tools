package com.tools.ztest.test;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Descripe: http://blog.csdn.net/avrilwu/article/details/8726441
 *
 * @author yingjie.wang
 * @since 16/8/30 上午10:27
 */
public class TestIdentityHashMap {
    private int diameter;

    private String color;

    public TestIdentityHashMap(int diameter, String color) {
        this.diameter = diameter;
        this.color = color;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof TestIdentityHashMap){
            TestIdentityHashMap testIdentityHashMap = (TestIdentityHashMap)o;
            if((testIdentityHashMap.color.equalsIgnoreCase(color))&&(testIdentityHashMap.diameter==diameter)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return color == null ? this.diameter : this.color.hashCode() * this.diameter;
    }

    @Override
    public String toString() {
        return "...diameter:[" + this.diameter  + "]; color:[" + this.color + "].";
    }

    public static void main(String[] args) throws Exception {
        HashMap<TestIdentityHashMap, String> hashMap = new HashMap<TestIdentityHashMap, String>();
        hashMap.put(new TestIdentityHashMap(1, "RED"), "A");
        hashMap.put(new TestIdentityHashMap(1, "RED"), "B");
        hashMap.put(new TestIdentityHashMap(1, "RED"), "C");
        hashMap.put(new TestIdentityHashMap(2, "BLU"), "D");
        for(Map.Entry<TestIdentityHashMap, String> entry : hashMap.entrySet()) {
            System.out.println("===> key:" + entry.getKey() + ", value:" + entry.getValue() + ".");
        }
        System.out.println();

        IdentityHashMap<TestIdentityHashMap, String> identityHashMap = new IdentityHashMap<TestIdentityHashMap, String>();
        TestIdentityHashMap testIdentityHashMap = new TestIdentityHashMap(1, "RED");
        identityHashMap.put(testIdentityHashMap, "A");
        identityHashMap.put(new TestIdentityHashMap(1, "RED"), "B");
        identityHashMap.put(new TestIdentityHashMap(1, "RED"), "C");
        identityHashMap.put(new TestIdentityHashMap(2, "BLU"), "D");
        identityHashMap.put(testIdentityHashMap, "AAA");
        for(Map.Entry<TestIdentityHashMap, String> entry : identityHashMap.entrySet()) {
            System.out.println("===> key:" + entry.getKey() + ", value:" + entry.getValue() + ".");
        }
    }
}
