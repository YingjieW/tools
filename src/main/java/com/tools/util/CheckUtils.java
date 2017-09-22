package com.tools.util;

import org.springframework.util.StringUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class CheckUtils {

    private static final String PARAM_NOT_NULL_MSG = "  can not be null!";

    /**
     * 验证对象是否为NULL,空字符串，空数组，空的Collection或Map(只有空格的字符串也认为是空串)
     * @param obj 被验证的对象
     * @param message 异常信息
     */
    @SuppressWarnings("rawtypes")
    public static void notEmpty(Object obj, String message) {
        if (obj == null){
            throw new IllegalArgumentException(message + " must be specified");
        }
        if (obj instanceof String && obj.toString().trim().length()==0){
            throw new IllegalArgumentException(message + " must be specified");
        }
        if (obj.getClass().isArray() && Array.getLength(obj)==0){
            throw new IllegalArgumentException(message + " must be specified");
        }
        if (obj instanceof Collection && ((Collection)obj).isEmpty()){
            throw new IllegalArgumentException(message + " must be specified");
        }
        if (obj instanceof Map && ((Map)obj).isEmpty()){
            throw new IllegalArgumentException(message + " must be specified");
        }
    }
    
    /**
     * 判断参数否非空
     * @param obj
     * @return
     */
    public static boolean isNull(Object obj){
        if (obj == null){
            return true;
        }
        return false;
    }

    public static boolean isNotNull(Object o) {
        return !isNull(o);
    }

    public static boolean isNull(Object... o) {
        boolean result = false;
        if (null != o) {
            for (Object object : o) {
                if (isNull(object)) {
                    result = true;
                    break;
                }
            }
        } else {
            result = true;
        }
        return result;
    }
    
    /**
     * 判断参数是否非NULL,空字符串，空数组，空的Collection或Map(只有空格的字符串也认为是空串)
     * @param obj
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object obj) {
        if (obj == null)
            return true;
        if ((obj instanceof String) && obj.toString().trim().length() == 0)
            return true;
        if (obj.getClass().isArray() && Array.getLength(obj) == 0)
            return true;
        if ((obj instanceof Collection) && ((Collection) obj).isEmpty())
            return true;
        return (obj instanceof Map) && ((Map) obj).isEmpty();
    }


    /**
     * 验证对象数组非空
     *
     * @param o
     * @return
     * @throws
     */
    public static void isNullAndThrowExp(String[] msg, Object... o) {
        if (null != o) {
            for (int i = 0; i < o.length; i++) {
                if (isNull(o[i])) {
                    throw new RuntimeException((i < msg.length ? msg[i] : "") + PARAM_NOT_NULL_MSG);
                }
            }
        }
    }

    public static void isNullAndThrowExp(String msg, Object o) {
        if (null != o) {
            if (isNull(o)) {
                throw new RuntimeException(msg + PARAM_NOT_NULL_MSG);
            }
        }

    }

    /**
     * 获得非空验证参数,为空时以默认值替换
     *
     * @param defaultValue
     * @return
     */
    public static String getNotEmptValue(String checkValue, String defaultValue) {
        if (StringUtils.isEmpty(checkValue)) {
            return defaultValue;
        }
        return checkValue;
    }

    /**
     * 判断左枚举是否与右边的集合元素有一个匹配
     *
     * @param left
     * @param right
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean inRightEnums(Enum left, Enum... right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }

        for (Enum enum1 : right) {
            if (left.equals(enum1)) {
                return true;
            }
        }
        return false;
    }
}
