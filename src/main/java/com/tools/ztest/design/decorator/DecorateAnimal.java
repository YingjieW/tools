package com.tools.ztest.design.decorator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/3/3 下午11:38
 */
public class DecorateAnimal implements Animal {
    // 被包装的动物
    private Animal animal;
    // 使用哪一个包装器
    private Class<? extends Feature> clazz;

    public DecorateAnimal(Animal animal, Class<? extends Feature> clazz) {
        this.animal = animal;
        this.clazz = clazz;
    }

    @Override
    public void doStuff() {
        InvocationHandler handler = new InvocationHandler() {
            // 具体包装行为
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object result = null;
                if (Modifier.isPublic(method.getModifiers())) {
                    result = method.invoke(clazz.newInstance(), args);
                }
                animal.doStuff();
                return result;
            }
        };
        // 当前线程加载器
        ClassLoader classLoader = getClass().getClassLoader();
        Feature proxy = (Feature) Proxy.newProxyInstance(classLoader, clazz.getInterfaces(), handler);
        proxy.loadFeature();
    }
}
