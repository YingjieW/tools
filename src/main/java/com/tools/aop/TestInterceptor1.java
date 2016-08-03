package com.tools.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/3 下午2:31
 */
public class TestInterceptor1 implements MethodInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(TestInterceptor1.class);

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        logger.info("=======================>>>>>TestInterceptor1 Start.");
        Object result = methodInvocation.proceed();
        logger.info("=======================>>>>>TestInterceptor1 End.");
        return result;
    }
}
