package com.tools.action.ztest;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/9/5 下午2:58
 */
//@Component
public class TestBean  {

    @Autowired
    private Test test;

    public TestBean() {
//        System.out.println("=========> " + this.getClass().getName() + "  : " + ThreadSafeDateUtils.formatDateTimeMillis(new Date()));
    }

//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        System.out.println("---------> postProcessBeforeInitialization : "  + System.currentTimeMillis());
//        return null;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        System.out.println("---------> postProcessAfterInitialization start : "  + System.currentTimeMillis());
//        test.input = 100;
//        System.out.println("~~~~~~~~~> input: " + test.input );
//        System.out.println("---------> postProcessAfterInitialization end : "  + System.currentTimeMillis());
//        return null;
//    }
}
