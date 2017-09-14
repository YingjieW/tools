package com.tools.entity;

import com.alibaba.fastjson.JSON;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/9/11 上午11:26
 */
public abstract class AbstractTask {

    public static final Map<String, String> map = new ConcurrentHashMap<String, String>();

    public void put(String key, String value) {
        map.put(key, value);
    }

    public static void main(String[] args) throws Exception {
        MainTask mainTask = new MainTask();
        mainTask.put("001", "aaa");
        SubTask subTask = new SubTask();
        System.out.println(subTask.map);
        subTask.put("001", "002");
        System.out.println(JSON.toJSONString(mainTask.map));
    }
}
