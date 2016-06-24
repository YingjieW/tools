package com.tools.ztest;

import com.tools.BaseTest;
import com.tools.ztest.facade.RmiMockTester;
import com.tools.ztest.reflect.enumtype.CommonType;
import org.junit.Before;
import org.junit.Test;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/6/24 下午3:08
 */
public class TestRmiMockTester extends BaseTest {

    RmiMockTester rmiMockTester;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        rmiMockTester = (RmiMockTester) beanFactory.getBean("rmiMockTester");
    }

    @Test
    public void testGetInt() throws Exception {
        int i = rmiMockTester.getInt();
        System.out.println("###   int: " + i);
    }

    @Test
    public void testGetEnum() throws Exception {
        CommonType commonType = rmiMockTester.getEnum();
        System.out.println("### CommonType: " + commonType.toString());
    }
}
