package com.tools.enumtype;

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

    // primitive datatype
    PRIMITIVE_BOOLEAN(new String[] {"boolean"}),
    PRIMITIVE_INT(new String[] {"int"}),
    PRIMITIVE_LONG(new String[] {"long"}),
    PRIMITIVE_FLOAT(new String[] {"float"}),
    PRIMITIVE_DOUBLE(new String[] {"double"}),

    // reference datatype
    REFERENCE_BOOLEAN(new String[] {"java.lang.Boolean", "Boolean"}),
    REFERENCE_INTEGER(new String[] {"java.lang.Integer", "Integer"}),
    REFERENCE_LONG(new String[] {"java.lang.Long", "Long"}),
    REFERENCE_FLOAT(new String[] {"java.lang.Float", "Float"}),
    REFERENCE_DOUBLE(new String[] {"java.lang.Double", "Double"}),

    STRING(new String[] {"java.lang.String", "String"}),
    BIGDECIMAL(new String[] {"java.math.BigDecimal", "BigDecimal"}),

    MAP(new String[] {"java.util.Map", "Map"}),
    HASHMAP(new String[] {"java.util.HashMap", "HashMap"}),
    TREEMAP(new String[] {"java.util.TreeMap", "TreeMap"}),
    LIST(new String[] {"java.util.List", "List"}),
    ARRAYLIST(new String[] {"java.util.ArrayList", "ArrayList"}),
    LINKEDLIST(new String[] {"java.util.LinkedList", "LinkedList"});

    private String[] names;

    private CommonType(String[] names) {
        this.names = names;
    }

    public Class getCommonClass() throws ClassNotFoundException {
        switch (this) {
            case PRIMITIVE_INT:
                return int.class;
            case PRIMITIVE_LONG:
                return long.class;
            case PRIMITIVE_FLOAT:
                return float.class;
            case PRIMITIVE_DOUBLE:
                return double.class;
            case PRIMITIVE_BOOLEAN:
                return boolean.class;
            default:
                return Class.forName(this.names[0]);
        }
    }

    public static CommonType parseType(String name) {
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

    public static Object getValue(String typeName, String value)  {
        CommonType commonType = CommonType.parseType(typeName);
        switch (commonType) {
            case STRING:
                return value;
            case REFERENCE_INTEGER:
                return new Integer(value);
            case PRIMITIVE_INT:
                return Integer.parseInt(value);
            case REFERENCE_LONG:
                return new Long(value);
            case PRIMITIVE_LONG:
                return Long.parseLong(value);
            case REFERENCE_FLOAT:
                return new Float(value);
            case PRIMITIVE_FLOAT:
                return Float.parseFloat(value);
            case REFERENCE_DOUBLE:
                return new Double(value);
            case PRIMITIVE_DOUBLE:
                return Double.parseDouble(value);
            case REFERENCE_BOOLEAN:
                return new Boolean(value);
            case PRIMITIVE_BOOLEAN:
                return Boolean.parseBoolean(value);
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
                throw new RuntimeException("Unsupport CommonType : " + commonType);
        }
    }
}
