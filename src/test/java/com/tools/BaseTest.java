package com.tools;

import junit.framework.TestCase;
import org.junit.Before;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/6/24 下午3:00
 */
public class BaseTest extends TestCase {

    private static ApplicationContext ctx;

    public static AutowireCapableBeanFactory beanFactory;
    
    @Before
    public void setUp() throws Exception {
        ctx = new FileSystemXmlApplicationContext("classpath:spring-mvc.xml");
        beanFactory = ctx.getAutowireCapableBeanFactory();
    }
}
