package com.tools.spring;

import com.tools.BaseTest;
import com.tools.entity.People;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/3/23 下午6:06
 */
public class TestPeople extends BaseTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testPeople() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/test/people.xml");
        People p = (People)ctx.getBean("id...");
        System.out.println(p.getId());
        System.out.println(p.getName());
        System.out.println(p.getAge());
    }
}
