package com.tools.ztest.proxy.jdk;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/5/24 下午3:59
 */
public class JdkClient {

    public static void main(String[] args) throws Exception {
        // 创建目标类对象
        JdkRealSubject jdkRealSubject = new JdkRealSubject();
        // 创建代理类对象
        JdkDynamicProxy jdkDynamicProxy = new JdkDynamicProxy(jdkRealSubject);
        // 获取代理对象
        JdkInterface proxy = (JdkInterface)jdkDynamicProxy.getProxy();
        // 也可以通过下面这个方式获取代理对象, 其原理和getProxy()方法相同
//        JdkInterface proxy = (JdkInterface) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
//                JdkRealSubject.class.getInterfaces(), jdkDynamicProxy);

        proxy.visit();
        System.out.println("---- after");
        jdkRealSubject.testDynamic = "hello world";
        proxy.visit();

//        Method method = proxy.getClass().getMethod("toCapital", String.class);
//        System.out.println("*** " + method.invoke(proxy, "hello world."));
//        System.out.println("*** " + method.getDeclaringClass().getName());
//
//        JdkDynamicProxy jdkDynamicProxy1 = new JdkDynamicProxy(new JdkRealSubjectX());
//        JdkRealSubjectX jdkRealSubjectX = (JdkRealSubjectX) jdkDynamicProxy1.getProxy();
//        jdkRealSubjectX.visit();
    }
}
