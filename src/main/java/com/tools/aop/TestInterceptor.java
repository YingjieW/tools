package com.tools.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/5/27 下午2:35
 */
public class TestInterceptor implements MethodInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(TestInterceptor.class);

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        logger.info("###   BBBBBBeeeeeFFFFFFFFFFooooooooooooRRRRRRRRReeeeeee");
//        Object result = methodInvocation.proceed();
        String result = "rrrrrrrrresultttttttttt";
        logger.info("###   AAAAAAAAAffffffffffffffTTTTTTTTTTeeeeeeeeeRRRRRRR");
        return result;
    }
}
