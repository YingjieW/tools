package com.tools.ztest.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/5/24 下午3:59
 */
public class JdkDynamicProxy implements InvocationHandler {

    private Object target;

    JdkDynamicProxy(Object target) {
        super();
        this.target = target;
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                target.getClass().getInterfaces(), this);
    }

    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        System.out.println("BBBBBBOREEEEEEEEE");
        Object result = method.invoke(this.target, objects);
        System.out.println("AAAAAAFTERRRRRRRR");
        return result;
    }


}
