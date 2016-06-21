package com.tools.ztest.yop;

import com.alibaba.fastjson.JSON;
import com.yeepay.g3.sdk.yop.client.YopClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/4/22 上午11:50
 */
public class Decription {

    public static String TEST = "TEST";

    public static void main(String[] args) {

        Decription decription = new Decription();
        System.out.println(decription.TEST);
        System.out.println(Decription.TEST);

        Map<String, HashMap<String,String>> test = new HashMap<String, HashMap<String, String>>();
        HashMap<String, String> map1 = new HashMap<String, String>();
        map1.put("map11", "111");
        map1.put("map12", "222");
        map1.put("map13", "333");
        HashMap<String, String> map2 = new HashMap<String, String>();
        map2.put("map21", "aaa");
        map2.put("map22", "bbb");
        map2.put("map23", "ccc");
        test.put("First", map1);
        test.put("Second", map2);
        System.out.println("#######   " + JSON.toJSONString(test));

        Map<String, Object> yop = new HashMap<String, Object>();

        System.out.println("Start");
        String key = "ER59202j4EqI44c18L76iyjVjTl17c75268w15L9M5f7638A8l2zJ7UB0d54";
        String response1 = "{\"doSignature\":\"true\",\"signatureAlg\":\"SHA1\",\"encryption\":\"QQXigkiycKKhf7Ln34oZi2jPubSnsMdc5VmN0YQFHEGxR+OGX74j6fkUu3k1dn+L5OrfopLu5AGg7rTkrwPHTR4DnOCgEbfnx8LY6egeKH8arEFjcny2\\/FWhTX+XG9z6gOBGWn5\\/WJexnsFlr8N85fcqk8TgSLOQ\\/5LUNSzhKuphqEH+lO1DvT7Dbvzx0WbJZIs2irGX0fSbmrIN2kjkHjZmrYDRGBpjrIzsIJaDFmmYqLPXK0RlPOagSElJHy0MdZ2yKcSUGIQ=\",\"signature\":\"7e4edcb6d2cf9b3863f79b5765c2da7ec1cb0fa4\",\"doEncryption\":\"true\",\"customerIdentification\":\"10040014971\",\"encryptionAlg\":\"BLOWFISH\"},\"customerIdentification\":\"10040014971\"}";
        String response = "{\"doSignature\":\"true\",\"signatureAlg\":\"SHA1\",\"encryption\":\"TjvvPFm5dQlhNMNE4Cu7BoOr+Toobvr0xM0X5cqTzA29+IqAuBaFDZUZFuSH9KajJQH9C77X9JfSt7dvMFxMV\\/5OBilmoBxBTlg2X7YDP\\/qA2SeZ6JsRR99QSXY8k+Izk+cJ0A2EwV0QAbTW61hyNn0nOh7JyE7sOJqPmp5epNfaWNGzZpFey\\/Zd7CDOz0mRSHvJ7BYbMGYiDwHRJZcMy277i00F8nUrve1M6WHI6zGddqBNnKnTtNjW5rvYmA\\/K0X2AuTH2098JmQDZxzYNc9B8OjEA7FLgFIx+BrCitPxNz0lDgijvonxVBxu92\\/Dxo\\/pK1SQJjTtfPDD0GXI2Snhg\",\"encryptionAlg\":\"BLOWFISH\",\"customerIdentification\":\"10012442799\",\"signature\":\"4b8507e4b3ba9bff5570187b846c727c6623bc93\",\"doEncryption\":\"true\"}";
        System.out.println("#######   " + response);


        Map mapResult = YopClient.acceptNotificationAsMap(key, response);
        System.out.println("#######   mapResult: " + mapResult);
        System.out.println("End");
    }
}
