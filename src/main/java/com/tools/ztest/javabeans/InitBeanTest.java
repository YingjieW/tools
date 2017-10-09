package com.tools.ztest.javabeans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/10/4 下午12:52
 */
public class InitBeanTest implements InitializingBean, BeanPostProcessor, BeanFactoryPostProcessor, BeanFactoryAware,
        BeanNameAware, DisposableBean {

    public InitBeanTest() {
        System.out.println("=====> 调用InitBeanTest构造器...");
    }

    private String hello;

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
        System.out.println("=====> 调用setHello()...");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("=====> 调用BeanPostProcessor的postProcessBeforeInitialization()...");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("=====> 调用BeanPostProcessor的postProcessAfterInitialization()...");
        return bean;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("=====> 调用InitializingBean的afterPropertiesSet()...");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory paramConfigurableListableBeanFactory)
            throws BeansException {
        System.out.println("=====> 调用BeanFactoryPostProcessor的postProcessBeanFactory()...");

    }

    @Override
    public String toString() {
        return "HelloWorld [hello=" + hello + "]";
    }

    @Override
    public void setBeanName(String paramString) {
        System.out.println("=====> 调用BeanNameAware.setBeanName");

    }

    @Override
    public void setBeanFactory(BeanFactory paramBeanFactory) throws BeansException {
        System.out.println("=====> 调用BeanFactoryAware.setBeanFactory");

    }

    @Override
    public void destroy() throws Exception {
        System.out.println("=====> DisposableBean 接口 destroy方法");

    }

    public void init() throws Exception {
        System.out.println("=====> InitBeanTest类init 方法");

    }
}
