package com.tools.ztest;

import com.tools.BaseTest;
import com.tools.ztest.javabeans.InitBeanTest;
import org.junit.Before;
import org.junit.Test;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/9/5 下午3:03
 */
public class TestBean extends BaseTest {
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testInstantiation() throws Exception {
        System.out.println(".....");
    }

    @Test
    public void testInitBean() throws Exception {
        InitBeanTest initBeanTest = (InitBeanTest) beanFactory.getBean("initBeanTest");
        System.out.println("===> initBeanTest: " + initBeanTest.getHello());
    }
}
