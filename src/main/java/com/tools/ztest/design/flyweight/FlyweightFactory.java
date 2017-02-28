package com.tools.ztest.design.flyweight;

import java.util.HashMap;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/28 下午2:21
 */
public class FlyweightFactory {
    private HashMap<String, Flyweight> flyweightHashMap = new HashMap<String, Flyweight>();

    public FlyweightFactory() {
        flyweightHashMap.put("X", new ConcreteFlyweight());
        flyweightHashMap.put("Y", new ConcreteFlyweight());
        flyweightHashMap.put("Z", new ConcreteFlyweight());
    }

    public Flyweight getFlyweight(String key) {
        return flyweightHashMap.get(key);
    }
}
