package com.tools.action.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/5/27 下午4:54
 */
@Component
public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);

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
