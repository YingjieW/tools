package com.tools.util;

import com.tools.ztest.test.DeepClone;

import java.beans.Beans;
import java.beans.IntrospectionException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

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
            throw new IllegalArgumentException("source must be specified.");
        }
        if(target == null) {
            throw new IllegalArgumentException("target must be specified.");
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

        if(PropertyUtils.isCommonReferenceType(targetType)) {
            return deepCopyCommonReferenceType(sourceValue, targetType);
        }

        if(sourceValue instanceof Enum) {
            return sourceValue;
        }

        Object targetValue = targetType.newInstance();
        copyProperties(sourceValue, targetValue, true, true, true);

        return targetValue;
    }

    /**
     * 该方法也有待优化
     * @param sourceValue
     * @param targetType
     * @return
     */
    private static Object deepCopyCommonReferenceType(Object sourceValue, Class targetType)
            throws InstantiationException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        if(sourceValue == null) {
            throw new IllegalArgumentException("sourceValue must be specified.");
        }
        if(targetType == null) {
            throw new IllegalArgumentException("targetType must be specified.");
        }

        if(targetType == String.class) {
            return new String((String) sourceValue);
        }
        if(targetType == Integer.class) {
            return new Integer((Integer) sourceValue);
        }
        if(targetType == Long.class) {
            return new Long((Long) sourceValue);
        }
        if(targetType == Float.class) {
            return new Float((Float) sourceValue);
        }
        if(targetType == Double.class) {
            return new Double((Double) sourceValue);
        }
        if(targetType == BigDecimal.class) {
            return new BigDecimal(((BigDecimal) sourceValue).toString());
        }
        if(targetType == Boolean.class) {
            return new Boolean((String) sourceValue);
        }
        if(sourceValue.getClass() == ArrayList.class) {
            ArrayList sourceArrayList = (ArrayList) sourceValue;
            ArrayList targetArrayList = new ArrayList(sourceArrayList.size());
            for(int i = 0; i < sourceArrayList.size(); i++) {
                targetArrayList.add(deepCopy(sourceArrayList.get(i), sourceArrayList.get(i).getClass()));
            }
            return  targetArrayList;
        }
        if(sourceValue.getClass() == LinkedList.class) {
            LinkedList sourceLinkedList = (LinkedList) sourceValue;
            LinkedList targetLinkedList = new LinkedList();
            for(int i = 0; i< sourceLinkedList.size(); i++) {
                targetLinkedList.add(deepCopy(sourceLinkedList.get(i), sourceLinkedList.get(i).getClass()));
            }
            return  targetLinkedList;
        }
        if(sourceValue.getClass() == HashSet.class) {
            HashSet sourceHashSet = (HashSet) sourceValue;
            HashSet targetHashSet = new HashSet(sourceHashSet.size());
            Iterator iterator = sourceHashSet.iterator();
            Object sourceElement = null;
            while (iterator.hasNext()) {
                sourceElement = iterator.next();
                targetHashSet.add(deepCopy(sourceElement, sourceElement.getClass()));
            }
            return targetHashSet;
        }
        if(sourceValue.getClass() == TreeSet.class) {
            TreeSet sourceTreeSet = (TreeSet) sourceValue;
            TreeSet targetTreeSet = new TreeSet();
            Iterator iterator = sourceTreeSet.iterator();
            Object sourceElement = null;
            while (iterator.hasNext()) {
                sourceElement = iterator.next();
                targetTreeSet.add(deepCopy(sourceElement, sourceElement.getClass()));
            }
            return targetTreeSet;
        }
        if(sourceValue.getClass() == HashMap.class) {
            HashMap sourceHashMap = (HashMap) sourceValue;
            HashMap targetHashMap = new HashMap(sourceHashMap.size());
            Iterator iterator = sourceHashMap.keySet().iterator();
            Object key = null;
            Object value = null;
            while (iterator.hasNext()) {
                key = iterator.next();
                value = sourceHashMap.get(key);
                targetHashMap.put(deepCopy(key, key.getClass()), deepCopy(value, value.getClass()));
            }
            return targetHashMap;
        }
        if(sourceValue.getClass() == TreeMap.class) {
            TreeMap sourceTreeMap = (TreeMap) sourceValue;
            TreeMap targetTreeMap = new TreeMap();
            Iterator iterator = sourceTreeMap.keySet().iterator();
            Object key = null;
            Object value = null;
            while (iterator.hasNext()) {
                key = iterator.next();
                value = sourceTreeMap.get(key);
                targetTreeMap.put(deepCopy(key, key.getClass()), deepCopy(value, value.getClass()));
            }
            return targetTreeMap;
        }
        throw new RuntimeException("Unsupport common reference type: " + targetType.getName() + ", sourceValueType: " + sourceValue.getClass());
    }


    /**
     * target必须已实现java.lang.Serializable接口
     * 利用JVM实现深度克隆
     *
     * @param target
     * @return
     */
    public static Object clone(Object target) {

        if(!(target instanceof Serializable)) {
            throw new RuntimeException("target must implement Serializable Interface!");
        }

        Object result = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;

        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(target);
            objectOutputStream.flush();
            objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
            result = objectInputStream.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        DeepClone clone1 = new DeepClone("deepClone", 1, 97l, new Date());
        DeepClone clone2 = (DeepClone) BeanUtils.clone(clone1);


        System.out.println("===> " + (clone1 == clone2));
        System.out.println("===> " + (clone1.equals(clone2)));
        System.out.println("===> " + (clone1.name == clone2.name));
        System.out.println("===> " + (clone1.age == clone2.age));
        System.out.println("===> " + (clone1.score == clone2.score));
        System.out.println("===> " + (clone1.date == clone2.date));

        System.out.println();
        System.out.println("===> clone1: " + clone1);
        System.out.println("===> clone2: " + clone2);
        System.out.println();

        Thread.sleep(1000);

        clone1.name = "aaa";
        clone1.age = 2;
        clone1.score = 99l;
        clone1.date = new Date();

        System.out.println("===> clone1: " + clone1);
        System.out.println("===> clone2: " + clone2);

        System.out.println();
        System.out.println("===> " + (clone1 == clone2));
        System.out.println("===> " + (clone1.equals(clone2)));
        System.out.println("===> " + (clone1.name == clone2.name));
        System.out.println("===> " + (clone1.age == clone2.age));
        System.out.println("===> " + (clone1.score == clone2.score));
        System.out.println("===> " + (clone1.date == clone2.date));
    }
}
