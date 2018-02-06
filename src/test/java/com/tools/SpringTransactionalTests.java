package com.tools;

import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

/**
 * 
 * @author：blakeyuan
 * @since：2015年1月4日 下午12:38:10
 * @version:
 */
@DirtiesContext
@ContextConfiguration(locations = {"classpath*:/spring-mvc.xml"})
@org.junit.runner.RunWith(value=SpringJUnit4ClassRunner.class)
@org.springframework.test.context.transaction.TransactionConfiguration(transactionManager="transactionManager",
defaultRollback=true)
public class SpringTransactionalTests extends AbstractTransactionalJUnit4SpringContextTests {

    protected DataSource dataSource;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
//        ConfigurationUtils.init();
//        RemoteServiceFactory.init();
        // LocalCacheUtils.init();
    }

    @Override
    @Autowired
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
        this.dataSource = dataSource;
    }

}
