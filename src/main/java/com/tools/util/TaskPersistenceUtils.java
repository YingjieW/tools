package com.tools.util;

import com.tools.annotation.Column;
import com.tools.annotation.Table;
import org.springframework.util.CollectionUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/9/4 下午3:41
 */
public class TaskPersistenceUtils {

    // 本地缓存列名与属性名的对应关系
    private static final Map<String, String> COLUMN_PROPERTY_NAME_MAP = new ConcurrentHashMap<String, String>();
    // 本地缓存field
    private static final Map<Class, LinkedHashSet<Field>> FIELD_CACHE_MAP = new ConcurrentHashMap<Class, LinkedHashSet<Field>>();
    // 本地缓存columns名称
    private static final Map<Class, LinkedHashSet<String>> COLUMN_NAME_CACHE_MAP = new ConcurrentHashMap<Class, LinkedHashSet<String>>();


    /**
     * 将jdbcTemplate的map类型查询结果转为实体
     * @param columnValueMap
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T transMap2Bean(Map<String, Object> columnValueMap, Class<T> clazz) {
        try {
            Object obj = clazz.newInstance();
            Map<String, Object> propertyValueMap = transColumn2Property(columnValueMap);
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (propertyValueMap.containsKey(key)) {
                    Object value = propertyValueMap.get(key);
                    // 得到property对应的setter方法
                    Method setter = property.getWriteMethod();
                    Class[] classes = setter.getParameterTypes();
                    if (classes[0].isEnum()) {
                        value = Enum.valueOf(classes[0], value.toString());
                    } else if (classes[0] == Boolean.class || classes[0] == boolean.class) {
                        value = (Integer)value == 0 ? false : true;
                    }
                    setter.invoke(obj, value);
                }
            }
            return (T) obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> transColumn2Property(Map<String, Object> columnMap) {
        Map<String, Object> propertyMap = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : columnMap.entrySet()) {
            String propertyName = transColumn2Property(entry.getKey());
            propertyMap.put(propertyName, entry.getValue());
        }
        return propertyMap;
    }

    // hello_world → helloWorld
    private static String transColumn2Property(String column) {
        String propertyName = COLUMN_PROPERTY_NAME_MAP.get(column);
        if (StringUtils.isNotBlank(propertyName)) {
            return propertyName;
        }
        column = column.toLowerCase();
        String[] splits = column.split("_");
        if (splits.length == 1) {
            return column;
        }
        StringBuilder builder = new StringBuilder(splits[0]);
        for (int i = 1; i < splits.length; i++) {
            builder.append(upperFirstCase(splits[i]));
        }
        propertyName = builder.toString();

        return propertyName;
    }

    // hello → Hello
    private static String upperFirstCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }


    /**
     * 生成"ID,CREATE_TIME,LAST_MODIFY_TIME,..."格式的字段链
     * @param columnNames
     * @return
     */
    public static String getColumnNameChain(Set<String> columnNames) {
        StringBuilder sb = new StringBuilder();
        int columnCount = 0;
        for (String columnName : columnNames) {
            columnCount += 1;
            sb.append(columnName);
            if (columnCount < columnNames.size()) {
                sb.append(",");
            }
        }
        sb.append(" ");
        return sb.toString();
    }

    /**
     * 获取表名
     * @param clazz
     * @return
     */
    public static String resolveTableName(Class<?> clazz) {
        Table annotation = clazz.getAnnotation(Table.class);
        if (annotation != null) {
            return annotation.name();
        } else {
            throw new RuntimeException("未指定任务实体的表名,usage:请在任务实体类上加上标注@Table(name = \"表名\")!");
        }
    }

    /**
     * 解析带有@Column标注的属性，作为待持久化的字段
     *
     * @param obj
     * @return
     */
    public static LinkedHashMap<String, Object> resolveColumnsAndValues(Object obj) {
        LinkedHashMap<String, Object> columns = new LinkedHashMap<String, Object>();
        Class<?> clazz = obj.getClass();
        LinkedHashSet<Field> set = getPersistentFields(clazz);
        try {
            for (Field field : set) {
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    field.setAccessible(true);
                    columns.put(column.name(), field.get(obj));
                }
            }
        } catch (Throwable t) {
            throw new RuntimeException("持久化任务实体时，反射获取属性时出错", t);
        }
        if (columns.isEmpty()) {
            throw new RuntimeException(
                    "持久化任务实体时,未解析到任何需要持久化的属性值! usage:请在要持久化的属性上加上标注@Column(name = \"列名\")");
        }
        return columns;
    }

    /**
     * 获取待存储的属性
     *
     * @param clazz
     * @return
     */
    public static LinkedHashSet<Field> getPersistentFields(Class clazz) {
        LinkedHashSet<Field> set = FIELD_CACHE_MAP.get(clazz);
        if (set != null) {
            return set;
        } else {
            set = new LinkedHashSet<Field>();
            set = getAllFields(clazz, set);
            FIELD_CACHE_MAP.put(clazz, set);
            return set;
        }
    }

    /**
     * 迭代获取所有属性，包含父类的属性
     *
     * @param clazz
     * @param set
     * @return
     */
    public static LinkedHashSet getAllFields(Class clazz, LinkedHashSet set) {
        if (clazz == null || clazz == Object.class) {
            return set;
        }
        Field fields[] = clazz.getDeclaredFields();
        for (Field f : fields) {
            set.add(f);
        }
        return getAllFields(clazz.getSuperclass(), set);
    }

    /**
     * 获取所有列名
     * @param clazz
     * @return
     */
    public static LinkedHashSet<String> getColumnNames(Class clazz) {
        LinkedHashSet<String> columnNames = COLUMN_NAME_CACHE_MAP.get(clazz);
        if (!CollectionUtils.isEmpty(columnNames)) {
            return columnNames;
        }
        LinkedHashSet<Field> fields = getPersistentFields(clazz);
        Column column = null;
        for (Field field : fields) {
            column = field.getAnnotation(Column.class);
            if (column != null) {
                columnNames.add(column.name());
            }
        }
        if (CollectionUtils.isEmpty(columnNames)) {
            throw new RuntimeException("未解析到任何属性值! usage:请在要持久化的属性上加上标注@Column(name = \"列名\")");
        }
        COLUMN_NAME_CACHE_MAP.put(clazz, columnNames);
        return columnNames;
    }
}
