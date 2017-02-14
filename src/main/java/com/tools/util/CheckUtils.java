package com.tools.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class CheckUtils {

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
}
