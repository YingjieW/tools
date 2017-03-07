package com.tools.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/3/4 下午3:07
 */
public class ReflectUtils {
    // 获得一个泛型类的实际泛型类型
    public static <T> Class<T> getGenericClassType(Class clz) {
        Type type = clz.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] types = parameterizedType.getActualTypeArguments();
            if (types != null && types.length > 0 && types[0] instanceof Class) {
                // 若有多个泛型参数, 依据位置索引返回
                return (Class) types[0];
            }
        }
        return (Class) Object.class;
    }
}
