package com.tools.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * Descripe: 仅支持JavaBean，不支持Map、Collection。
 *
 * @author yingjie.wang
 * @since 16/6/17 下午2:20
 */
public class PropertyUtils {

    private static Map propertyUtilsCache = new HashMap();

    private static final String GET = "get";

    private static final String SET = "set";

    private static final String IS = "is";

    private static final String KEY_PREFIX = "PropertyUtils";

    public static final List<Class> PRIMITIVE_TYPES = new ArrayList<Class>(8);

    public static final List<Class> COMMON_REFERENCE_TYPES = new ArrayList<Class>();

    static {
        // 基本数据类型有8种
        PRIMITIVE_TYPES.add(int.class);
        PRIMITIVE_TYPES.add(short.class);
        PRIMITIVE_TYPES.add(long.class);
        PRIMITIVE_TYPES.add(float.class);
        PRIMITIVE_TYPES.add(double.class);
        PRIMITIVE_TYPES.add(byte.class);
        PRIMITIVE_TYPES.add(char.class);
        PRIMITIVE_TYPES.add(boolean.class);

        // JVM提供的JavaBean中常用的引用数据类型(待进一步补充)
        COMMON_REFERENCE_TYPES.add(String.class);
        COMMON_REFERENCE_TYPES.add(Integer.class);
        COMMON_REFERENCE_TYPES.add(Long.class);
        COMMON_REFERENCE_TYPES.add(Float.class);
        COMMON_REFERENCE_TYPES.add(Double.class);
        COMMON_REFERENCE_TYPES.add(BigDecimal.class);
        COMMON_REFERENCE_TYPES.add(Boolean.class);
        COMMON_REFERENCE_TYPES.add(List.class);
        COMMON_REFERENCE_TYPES.add(ArrayList.class);
        COMMON_REFERENCE_TYPES.add(LinkedList.class);
        COMMON_REFERENCE_TYPES.add(Set.class);
        COMMON_REFERENCE_TYPES.add(HashSet.class);
        COMMON_REFERENCE_TYPES.add(TreeSet.class);
        COMMON_REFERENCE_TYPES.add(Map.class);
        COMMON_REFERENCE_TYPES.add(HashMap.class);
        COMMON_REFERENCE_TYPES.add(TreeMap.class);
    }

    /**
     * 删除缓存中的key
     * @param key
     */
    public static void removeCache(String key) {
        if(propertyUtilsCache.containsKey(key)) {
            propertyUtilsCache.remove(key);
        }
    }

    /**
     * 清空缓存
     */
    public static void clearCache() {
        propertyUtilsCache = new HashMap();
    }

    /**
     * 获取beanClass所有属性描述
     * @param beanClass
     * @return
     */
    public static PropertyDescriptor[] getPropertyDescriptors(Class beanClass) throws IntrospectionException {

        if(beanClass == null) {
            throw new IllegalArgumentException("Bean class must be specified.");
        }

        // 首先尝试从缓存中获取
        String key = KEY_PREFIX  + ".getPropertyDescriptors(" + beanClass.getName() + ")";
        PropertyDescriptor[] propertyDescriptors = (PropertyDescriptor[]) propertyUtilsCache.get(key);
        if(propertyDescriptors != null) {
            return propertyDescriptors;
        }

        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(beanClass);
        } catch (IntrospectionException e) {
            throw e;
        }
        propertyDescriptors = beanInfo.getPropertyDescriptors();
        if(propertyDescriptors == null) {
            propertyDescriptors = new PropertyDescriptor[0];
        }

        propertyUtilsCache.put(key, propertyDescriptors);

        return propertyDescriptors;
    }

    /**
     * 获取属性描述
     * @param beanClass
     * @param propertyName
     * @return
     * @throws IntrospectionException
     */
    public static PropertyDescriptor getPropertyDescriptor(Class beanClass, String propertyName) throws IntrospectionException {

        if(beanClass == null) {
            throw new IllegalArgumentException("beanClass must be specified.");
        }
        if(propertyName == null || propertyName.trim().length() == 0) {
            throw new IllegalArgumentException("propertyName must be specified.");
        }

        String key = KEY_PREFIX  + ".getPropertyDescriptor(" + beanClass.getName() + "," + propertyName + ")";
        PropertyDescriptor result = (PropertyDescriptor) propertyUtilsCache.get(key);
        if(result != null) {
            return result;
        }

        PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(beanClass);
        for(PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if(propertyDescriptor.getName().equals(propertyName)) {
                result = propertyDescriptor;
                break;
            }
        }

        if(result == null) {
            throw new RuntimeException("Cannot get propertyDescriptor of property[" + propertyName + "]");
        }

        propertyUtilsCache.put(key, result);
        return result;
    }

    /**
     * 获取beanClass所有属性名称（不包含class）
     * @param beanClass
     * @return
     */
    public static String[] getPropertyNames(Class beanClass) throws IntrospectionException {

        if(beanClass == null) {
            throw new IllegalArgumentException("Bean class must be specified.");
        }

        // 首先尝试从缓存中获取该属性
        String key = KEY_PREFIX  + ".getPropertyNames(" + beanClass.getName() + ")";
        String[] propertyNames = (String[]) propertyUtilsCache.get(key);
        if(propertyNames != null) {
            return propertyNames;
        }

        PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(beanClass);
        propertyNames = new String[propertyDescriptors.length];
        for(int i = 0; i < propertyDescriptors.length; i++) {
            propertyNames[i] = propertyDescriptors[i].getName();
        }

        int classIndex = Arrays.binarySearch(propertyNames, "class");
        if(classIndex > 0) {
            if(propertyNames.length == 1) {
                return new String[0];
            }
            String[] propertyNamesWithoutClass = new String[propertyNames.length - 1];
            System.arraycopy(propertyNames, 0, propertyNamesWithoutClass, 0, classIndex);
            System.arraycopy(propertyNames, classIndex+1, propertyNamesWithoutClass, classIndex, propertyNames.length-1-classIndex);
            propertyUtilsCache.put(key, propertyNamesWithoutClass);
            return propertyNamesWithoutClass;
        }

        propertyUtilsCache.put(key, propertyNames);
        return propertyNames;
    }

    /**
     * 获取对象(bean)的属性(propertyName)值
     * 仅支持JavaBean，不支持Map、Collection。
     * @param bean
     * @param propertyName
     * @return
     */
    public static Object getPropertyValue(Object bean, String propertyName) {

        if(bean == null) {
            throw new IllegalArgumentException("bean must be specified.");
        }
        if(propertyName == null || propertyName.trim().length() == 0) {
            throw new IllegalArgumentException("propertyName must be specified.");
        }

        // 首先尝试从缓存中获取
        Class beanClass = bean.getClass();
        String key = KEY_PREFIX  + ".getPropertyValue(" + beanClass.getName() + "," + propertyName + ")";
        Object propertyValue = propertyUtilsCache.get(key);
        if(propertyValue != null) {
            return propertyValue;
        }

        Method method = null;
        try {
            method = beanClass.getMethod(createMethodName(GET, propertyName));
            propertyValue = method.invoke(bean);
        } catch (Exception e1) {
            try {
                method = beanClass.getMethod(createMethodName(IS, propertyName));
                propertyValue =  method.invoke(bean);
            } catch (Exception e2) {
                try {
                    method = beanClass.getMethod(propertyName);
                    propertyValue =  method.invoke(bean);
                } catch (Exception e3) {
                    try {
                        Field field = beanClass.getField(propertyName);
                        propertyValue =  field.get(propertyName);
                    } catch (Exception e4) {
                        throw new RuntimeException("Cannot get value of property[" + propertyName + "]");
                    }
                }
            }
        }

        propertyUtilsCache.put(key, propertyValue);
        return propertyValue;
    }


    /**
     * 获取bean所有属性值（不包含class）
     * 仅支持JavaBean，不支持Map、Collection。
     * @param bean
     * @return
     * @throws IntrospectionException
     */
    public static Map<String, Object> getPropertyNameValuePairs(Object bean) throws IntrospectionException {

        if(bean == null) {
            throw new IllegalArgumentException("Bean must be specified.");
        }

        Class beanClass = bean.getClass();
        // 尝试从本地缓存中获取该属性
        String key = KEY_PREFIX  + ".getPropertyValues(" + beanClass.getName() + ")";
        Map<String, Object> propertyValues = (Map<String, Object>) propertyUtilsCache.get(key);
        if(propertyValues != null) {
            return propertyValues;
        }

        String[] propertyNames = getPropertyNames(beanClass);
        if(propertyNames.length == 0) {
            return new HashMap(0);
        }

        propertyValues = new HashMap<String, Object>(propertyNames.length);
        Object propertyValue = null;
        for(String propertyName : propertyNames) {
            propertyValue = getPropertyValue(bean, propertyName);
            propertyValues.put(propertyName, propertyValue);
        }

        propertyUtilsCache.put(key, propertyValues);
        return propertyValues;
    }

    /**
     * 给指定属性赋值
     * @param bean
     * @param propertyName
     * @param propertyValue
     */
    public static void setPropertyValue(Object bean, String propertyName, Object propertyValue) throws IntrospectionException, IllegalAccessException, InvocationTargetException{

        if(bean == null) {
            throw new IllegalArgumentException("bean must be specified.");
        }
        if(propertyName == null || propertyName.trim().length() == 0) {
            throw new IllegalArgumentException("propertyName must be specified.");
        }

        Class beanClass = bean.getClass();
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(bean.getClass(), propertyName);
        Method setMethod = propertyDescriptor.getWriteMethod();
        setMethod.invoke(bean, propertyValue);
    }

    /**
     * 构建方法名称
     * @param prefix
     * @param propertyName
     * @return
     */
    public static String createMethodName(String prefix, String propertyName) {
        return prefix + propertyName.toUpperCase().charAt(0) + propertyName.substring(1);
    }

    /**
     * 获取属性propertyName的类型
     * @param beanClass
     * @param propertyName
     * @return
     * @throws IntrospectionException
     */
    public static Class getPropertyType(Class beanClass, String propertyName) {

        if(beanClass == null) {
            throw new IllegalArgumentException("beanClass must be specified.");
        }
        if(propertyName == null || propertyName.trim().length() == 0) {
            throw new IllegalArgumentException("propertyName must be specified.");
        }

        PropertyDescriptor propertyDescriptor = null;
        try {
            propertyDescriptor = getPropertyDescriptor(beanClass, propertyName);
        } catch (Exception e) {
            return null;
        }

        return propertyDescriptor == null ? null : propertyDescriptor.getPropertyType();
    }

    /**
     * 判断是否为基本数据类型
     * @param propertyType
     * @return
     */
    public static boolean isPrimitiveType(Class propertyType) {
        if(propertyType == null) {
            return false;
        }
        return PRIMITIVE_TYPES.contains(propertyType);
    }

    /**
     * 判断是否为常见的引用类型
     * 该方法写的极烂,待优化
     * @param propertyType
     * @return
     */
    public static boolean isCommonReferenceType(Class propertyType) {
        if(propertyType == null) {
            return false;
        }
        return COMMON_REFERENCE_TYPES.contains(propertyType);
    }
}
