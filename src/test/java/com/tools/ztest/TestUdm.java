package com.tools.ztest;

import com.alibaba.fastjson.JSON;
import com.tools.BaseTest;
import open.udm.client.context.DefaultUDMClientContext;
import open.udm.client.entity.BaseMainTaskEntity;
import open.udm.client.entity.BaseSubTaskEntity;
import open.udm.client.enums.TaskStatusEnum;
import open.udm.client.jobs.JobTaskUpdate;
import open.udm.client.persistence.MainTaskPersistence;
import open.udm.client.persistence.SubTaskPersistence;
import open.udm.client.utils.ThreadSafeDateUtils;
import open.udm.server.dto.ServerInfoDTO;
import open.udm.server.dto.TaskConfigDTO;
import open.udm.server.enums.TaskConfigStatusEnum;
import open.udm.server.enums.TaskDataTypeEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/9/5 下午5:11
 */
public class TestUdm extends BaseTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testCompensateSubTask() throws Exception {
        SubTaskPersistence subTaskPersistence = (SubTaskPersistence) beanFactory.getBean("subTaskPersistence");
        TaskStatusEnum[] taskStatusArr = {TaskStatusEnum.INIT, TaskStatusEnum.FAIL};
        Date endCreateTime = ThreadSafeDateUtils.getFetchDelayDate(24*60);
        Date startCreateTime = ThreadSafeDateUtils.getFetchDelayDate(endCreateTime, 24*7*60);
        List<BaseSubTaskEntity> taskEntityList =
                subTaskPersistence.queryByStatusAndCreateTime(BaseSubTaskEntity.class, startCreateTime, endCreateTime, taskStatusArr, 50);
        System.out.println(JSON.toJSONString(taskEntityList));
    }

    @Test
    public void testServerBetaFlag() {
        DefaultUDMClientContext defaultUDMClientContext = (DefaultUDMClientContext) beanFactory.getBean("defaultUDMClientContext");
        System.out.println("==============> " + defaultUDMClientContext.getServerBetaFlag());
    }

    @Test
    public void testTaskPersistence() throws Exception {
        MainTaskPersistence mainTaskPersistence = (MainTaskPersistence) beanFactory.getBean("mainTaskPersistence");
        BaseMainTaskEntity mainTaskEntity = mainTaskPersistence.queryById(BaseMainTaskEntity.class, "testing");
        System.out.println(JSON.toJSONString(mainTaskEntity));
        beanFactory.getBean("udmBeanFactoryUtils..");
    }

    @Test
    public void testMainTask() throws Exception {

    }
}
