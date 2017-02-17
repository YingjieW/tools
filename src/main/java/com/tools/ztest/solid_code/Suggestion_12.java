package com.tools.ztest.solid_code;

import com.alibaba.fastjson.JSON;

import java.io.*;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/15 下午10:16
 */
public class Suggestion_12 {
    private static final String path = "/Users/YJ/Documents/Zzzz/ZZZZtesting/Suggestion_12_1.txt";

    private static void serialize() throws Exception {
        System.out.println("Start to serialize.");
        Suggestion_12_1 suggestion_12_1 = new Suggestion_12_1();
        suggestion_12_1.setScore(18);
        suggestion_12_1.setName("Tom");
        System.out.println(JSON.toJSONString(suggestion_12_1));
        System.out.println(suggestion_12_1);

        FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(suggestion_12_1);
        objectOutputStream.close();
        System.out.println("Serialize successfully!");
    }

    private static void deserialize() throws Exception {
        System.out.println("Start to deserialize.");
        FileInputStream fileInputStream = new FileInputStream(new File(path));
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Suggestion_12_1 suggestion_12_1 = (Suggestion_12_1) objectInputStream.readObject();
        objectInputStream.close();
        System.out.println(JSON.toJSONString(suggestion_12_1));
        System.out.println(suggestion_12_1);
        System.out.println("Deserialize successfully!");
    }

    public static void main(String[] args) throws Exception {
//        serialize();
        deserialize();
    }
}
