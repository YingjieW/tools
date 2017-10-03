package com.tools.ztest.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/10/2 下午3:04
 */
public class JsonToMap {

    public static void main(String[] args) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("A", "001");
        map.put("B", "002");
        String jsonStr = JSON.toJSONString(map);
        Map<String, String> map1 = JSON.parseObject(jsonStr, new TypeReference<Map<String, String>>(){});
    }
}
