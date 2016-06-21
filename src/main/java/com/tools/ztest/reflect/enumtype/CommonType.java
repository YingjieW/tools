package com.tools.ztest.reflect.enumtype;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.math.BigDecimal;
import java.util.*;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/5/20 上午11:45
 */
public enum CommonType {

    BOOLEAN(new String[] {"java.lang.Boolean", "boolean", "java.lang.boolean"}),
    STRING(new String[] {"java.lang.String", "string", "java.lang.string"}),
    INTEGER(new String[] {"java.lang.Integer", "int", "integer", "java.lang.integer"}),
    LONG(new String[] {"java.lang.Long", "long", "java.lang.long"}),
    FLOAT(new String[] {"java.lang.Float", "float", "java.lang.float"}),
    DOUBLE(new String[] {"java.lang.Double", "double", "java.lang.double"}),
    MAP(new String[] {"java.util.Map", "map", "java.util.map"}),
    HASHMAP(new String[] {"java.util.HashMap", "map", "hashmap", "java.util.hashmap"}),
    TREEMAP(new String[] {"java.util.TreeMap", "treemap", "java.util.treemap"}),
    LIST(new String[] {"java.util.List", "list", "java.util.list"}),
    ARRAYLIST(new String[] {"java.util.ArrayList", "list", "arrayList", "java.util.arraylist"}),
    LINKEDLIST(new String[] {"java.util.LinkedList", "linkedlist", "java.util.linkedlist"}),
    BIGDECIMAL(new String[] {"java.math.BigDecimal", "bigdecimal", "java.math.bigdecimal"});

    private String[] names;

    private CommonType(String[] names) {
        this.names = names;
    }

    public String getClassName() {
        return this.names[0];
    }

    public static CommonType parseType(String name) {
        if (name != null) {
            name = name.toLowerCase();
        }
        for (CommonType commonType : CommonType.values()) {
            for (String tname : commonType.names) {
                if (tname.equals(name)) {
                    return commonType;
                }
            }
        }
        return null;
    }

    public static CommonType parseType(@SuppressWarnings("rawtypes") Class clz) {
        if (clz == null) {
            return null;
        }
        return parseType(clz.getName());
    }

    public static Object getValue(String typeName, String value) throws Throwable {
        try {
            CommonType type = CommonType.parseType(typeName);
            switch (type) {
                case STRING:
                    return value;
                case INTEGER:
                    return new Integer(value);
                case LONG:
                    return new Long(value);
                case FLOAT:
                    return new Float(value);
                case DOUBLE:
                    return new Double(value);
                case BOOLEAN:
                    return new Boolean(value);
                case BIGDECIMAL:
                    return new BigDecimal(value);
                case MAP:
                    return JSON.parseObject(value, new TypeReference<Map>() {
                    });
                case HASHMAP:
                    return JSON.parseObject(value, new TypeReference<HashMap>() {
                    });
                case TREEMAP:
                    return JSON.parseObject(value, new TypeReference<TreeMap>() {
                    });
                case LIST:
                    return JSON.parseObject(value, new TypeReference<List>() {
                    });
                case ARRAYLIST:
                    return JSON.parseObject(value, new TypeReference<ArrayList>() {
                    });
                case LINKEDLIST:
                    return JSON.parseObject(value, new TypeReference<LinkedList>() {
                    });
                default:
                    throw new RuntimeException("Unsupport Type : " + type);
            }
        } catch (Throwable t) {
            throw t;
        }
    }
}
