package com.tools.action.ztest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/5/27 下午4:54
 */
@Component("test")
public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    public int input;

    public Test() {
//        System.out.println("=========> " + this.getClass().getName() + "  : " + ThreadSafeDateUtils.formatDateTimeMillis(new Date()));
    }

    public void printLog() {
        logger.info("###   currentTime: " + System.currentTimeMillis());
    }

    public static void testStatic() {
        logger.info("###   testStatic: " + System.currentTimeMillis());
    }

    public String testString() {
        return "String000001";
    }
}
