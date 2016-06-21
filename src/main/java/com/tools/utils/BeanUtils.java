package com.tools.utils;

import java.beans.Beans;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/6/17 下午7:27
 */
public class BeanUtils {


    /**
     * 将source的各个属性值拷贝至target
     * @param source
     * @param target
     * @throws IntrospectionException
     */
    public static void copyProperties(Object source, Object target) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        copyProperties(source, target, true, true);
    }

    /**
     * 将source的各个属性值拷贝至target
     * 注意：该方法的拷贝为浅拷贝
     * @param source
     * @param target
     * @param forcedConvert 是否强制类型转换（主要用于属性名相同，但属性类型不同的情况）
     * @param ignoreNullValue 是否忽略值为NULL的属性
     */
    public static void copyProperties(Object source, Object target, boolean forcedConvert, boolean ignoreNullValue)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        if(source == null) {
            throw new RuntimeException("source must be specified.");
        }
        if(target == null) {
            throw new RuntimeException("target must be specified.");
        }

        String propertyName = null;
        Object sourcePropertyValue = null;
        Object targetPropertyValue = null;
        Class targetPropertyType = null;
        Class targetClass = target.getClass();
        Map<String, Object> propertyNameValuePairs = PropertyUtils.getPropertyNameValuePairs(source);
        for(Map.Entry<String, Object> entry : propertyNameValuePairs.entrySet()) {
            propertyName = entry.getKey();
            sourcePropertyValue = entry.getValue();
            if(ignoreNullValue && sourcePropertyValue == null) {
                continue;
            }
            if(forcedConvert) {
                targetPropertyType = PropertyUtils.getPropertyType(targetClass, propertyName);
                if(null == targetPropertyType) {
                    continue;
                }
                // Beans.isInstanceOf()不支持基本数据类型的校验
                if(Beans.isInstanceOf(sourcePropertyValue, targetPropertyType)) {
                    targetPropertyValue = sourcePropertyValue;
                } else if(sourcePropertyValue instanceof String) {
                    targetPropertyValue = ConvertUtils.convert(targetPropertyType, (String)sourcePropertyValue);
                } else {
                    targetPropertyValue = sourcePropertyValue;
                    //throw new RuntimeException(propertyName + "[" + sourcePropertyValue + "] cannot be converted to " + targetPropertyType);
                }
            } else {
                targetPropertyValue = sourcePropertyValue;
            }
            PropertyUtils.setPropertyValue(target, propertyName, targetPropertyValue);
        }

    }
}
