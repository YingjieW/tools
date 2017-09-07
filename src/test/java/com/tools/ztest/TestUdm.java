package com.tools.ztest;

import com.alibaba.fastjson.JSON;
import com.tools.BaseTest;
import open.udm.client.entity.BaseMainTaskEntity;
import open.udm.client.jobs.JobTaskUpdate;
import open.udm.client.persistence.MainTaskPersistence;
import open.udm.server.dto.TaskConfigDTO;
import open.udm.server.enums.TaskConfigStatusEnum;
import open.udm.server.enums.TaskDataTypeEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
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
    public void testTaskPersistence() throws Exception {
        MainTaskPersistence mainTaskPersistence = (MainTaskPersistence) beanFactory.getBean("mainTaskPersistence");
        BaseMainTaskEntity mainTaskEntity = mainTaskPersistence.queryById(BaseMainTaskEntity.class, "testing");
        System.out.println(JSON.toJSONString(mainTaskEntity));
        beanFactory.getBean("udmBeanFactoryUtils..");
    }

    @Test
    public void testMainTask() throws Exception {
        TaskConfigDTO taskConfigDTO = new TaskConfigDTO();
        taskConfigDTO.setId("cfg_20170905184800");
        taskConfigDTO.setAppId("app_20170905184800");
        taskConfigDTO.setTaskConsumersClass("com.tools.action.udm.TaskProcessorImpl");
        taskConfigDTO.setTaskDataType(TaskDataTypeEnum.FILE);
        taskConfigDTO.setDatasource("/Users/YJ/Documents/generator/20170414.txt");
        taskConfigDTO.setTaskConsumersMax(3);
        taskConfigDTO.setBatchSize(5);
        taskConfigDTO.setCronExpression("0/1 * * * * ? *");
        taskConfigDTO.setTaskStatus(TaskConfigStatusEnum.ACTIVE);

        List<TaskConfigDTO> taskConfigDTOList = new ArrayList<TaskConfigDTO>();
        taskConfigDTOList.add(taskConfigDTO);

        JobTaskUpdate jobTaskUpdate = (JobTaskUpdate) beanFactory.getBean("jobTaskUpdate");
        jobTaskUpdate.updateTask(taskConfigDTOList);
    }
}
