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
     * 注意: 为浅拷贝
     * @param source
     * @param target
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IntrospectionException
     * @throws InvocationTargetException
     */
    public static void copyProperties(Object source, Object target)
            throws InstantiationException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        copyProperties(source, target, true, true, false);
    }

    /**
     * 将source的各个属性值拷贝至target
     * 注意: 为深拷贝
     * @param source
     * @param target
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IntrospectionException
     * @throws InvocationTargetException
     */
    public static void deepCopyProperties(Object source, Object target)
            throws InstantiationException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        copyProperties(source, target, true, true, true);
    }

    /**
     * 将source的各个属性值拷贝至target
     * @param source
     * @param target
     * @param forcedConvert 是否强制类型转换（主要用于属性名相同，但属性类型不同的情况）
     * @param ignoreNullValue 是否忽略值为NULL的属性
     * @param deepCopy 是否深拷贝
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IntrospectionException
     * @throws InvocationTargetException
     */
    public static void copyProperties(Object source, Object target, boolean forcedConvert, boolean ignoreNullValue, boolean deepCopy)
            throws InstantiationException, IllegalAccessException, IntrospectionException, InvocationTargetException {
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
                    if(deepCopy) {
                        targetPropertyValue = deepCopy(sourcePropertyValue, targetPropertyType);
                    } else {
                        targetPropertyValue = sourcePropertyValue;
                    }
                } else if(sourcePropertyValue instanceof String) {
                    targetPropertyValue = ConvertUtils.convert(targetPropertyType, (String)sourcePropertyValue);
                } else if(PropertyUtils.isPrimitiveType(targetPropertyType)){
                    targetPropertyValue = sourcePropertyValue;
                } else {
                    throw new RuntimeException(propertyName + "[" + sourcePropertyValue + "] cannot be converted to " + targetPropertyType);
                }
            } else {
                targetPropertyValue = sourcePropertyValue;
            }
            PropertyUtils.setPropertyValue(target, propertyName, targetPropertyValue);
        }
    }

    /**
     * 深复制
     * @param sourceValue
     * @param targetType
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IntrospectionException
     * @throws InvocationTargetException
     */
    private static Object deepCopy(Object sourceValue, Class targetType)
            throws InstantiationException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        if(PropertyUtils.isPrimitiveType(targetType)) {
            return sourceValue;
        }

        Object targetValue = targetType.newInstance();
        copyProperties(sourceValue, targetValue, true, true, true);

        return targetValue;
    }
}
