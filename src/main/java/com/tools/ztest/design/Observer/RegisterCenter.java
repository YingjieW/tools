package com.tools.ztest.design.Observer;

import com.yeepay.g3.utils.common.CollectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/30 上午9:54
 */
public class RegisterCenter {

    private static Map<Class, LinkedHashSet<Method>> observerMap = new HashMap<Class, LinkedHashSet<Method>>();

    public static void register(Class clazz, Method method) {
        LinkedHashSet<Method> methods = observerMap.get(clazz);
        if (methods == null) {
            methods = new LinkedHashSet<Method>();
            methods.add(method);
            observerMap.put(clazz, methods);
        } else {
            methods.add(method);
        }
    }

    public static void delete(Class clazz, Method method) {
        if (observerMap == null || observerMap.size() == 0 || observerMap.get(clazz) == null
                || CollectionUtils.isEmpty(observerMap.get(clazz))) {
            return;
        }
        observerMap.get(clazz).remove(method);
    }

    public static Map<Class, LinkedHashSet<Method>> getObserverMap() {
        return observerMap;
    }
}
