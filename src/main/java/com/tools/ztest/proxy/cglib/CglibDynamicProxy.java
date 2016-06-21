package com.tools.ztest.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@SuppressWarnings("all")
public class CglibDynamicProxy implements MethodInterceptor {
    
    private Object target;
    
    public Object getProxyInstance(Object target) {
        this.target = target;
        // 声明增强类实例  
        Enhancer enhancer = new Enhancer();
        // 设置被代理类字节码，CGLIB根据字节码生成被代理类的子类
        enhancer.setSuperclass(target.getClass());
        // 设置要代理的拦截器，回调函数，即一个方法拦截   new MethodInterceptor()
        enhancer.setCallback(this);
        // 创建代理对象 实例
        return enhancer.create();
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("##### - before . ");
        Object result = proxy.invokeSuper(obj, args);
        System.out.println("##### - after . ");
        return result;
    }

}
