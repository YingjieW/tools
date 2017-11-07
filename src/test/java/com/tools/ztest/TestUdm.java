package com.tools.ztest;

import com.alibaba.fastjson.JSON;
import com.tools.BaseTest;
import com.tools.util.BeanFactoryUtil;
import open.udm.client.context.DefaultUDMClientContext;
import open.udm.client.dto.MainTaskProcessDTO;
import open.udm.client.entity.BaseMainTaskEntity;
import open.udm.client.entity.BaseSubTaskEntity;
import open.udm.client.enums.TaskStatusEnum;
import open.udm.client.jobs.JobTaskUpdate;
import open.udm.client.persistence.MainTaskPersistence;
import open.udm.client.persistence.SubTaskPersistence;
import open.udm.client.processer.maintask.MainTaskProcessor;
import open.udm.client.utils.ThreadSafeDateUtils;
import open.udm.server.dto.ServerInfoDTO;
import open.udm.server.dto.TaskConfigDTO;
import open.udm.server.enums.TaskConfigStatusEnum;
import open.udm.server.enums.TaskDataTypeEnum;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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
    public void testLoadResources() throws Exception {
        String propPath = "runtimecfg/udm-client.properties";
        Properties prop = PropertiesLoaderUtils.loadAllProperties(propPath);
    }

    @Test
    public void testMainTaskProcess() throws Exception {
        MainTaskProcessor mainTaskProcessor = (MainTaskProcessor) beanFactory.getBean("mainTaskProcessor");
        MainTaskProcessDTO processDTO = new MainTaskProcessDTO();
        processDTO.setTaskConfigId("_task_config_id_test_001");
        processDTO.setAppId("_app_id_test");
        processDTO.setBatchSize(10);
        List<String> list = new ArrayList<>();
        list.add("Hello_world.");
        processDTO.setDatasource(list);
        processDTO.setControllerId(null);
        processDTO.setTaskConsumerClass("com.tools.action.udm.TaskProcessorImpl");
        processDTO.setTaskConsumerMax(1);
        processDTO.setTaskDataType(TaskDataTypeEnum.LIST);
        processDTO.setTaskPriority(5);
        System.out.println("**********************************************************");
        mainTaskProcessor.process(processDTO);
        System.out.println("**********************************************************");
    }

    @Test
    public void testSubTaskIpUpdate() throws Exception {
        SubTaskPersistence subTaskPersistence = (SubTaskPersistence) beanFactory.getBean("subTaskPersistence");
        Class clazz = BaseSubTaskEntity.class;
        String id = "DMSTC20171024115550824YLSLZdU";
        TaskStatusEnum newStatus = TaskStatusEnum.SUCCESS;
        TaskStatusEnum oldStatus = TaskStatusEnum.INIT;
        String remark = "Test_" + System.currentTimeMillis();
        String ip = "255.255.255.255";
        System.out.println("remark : " + remark);
//        int result = subTaskPersistence.updateStatusAndRemark(clazz, id, oldStatus, newStatus, remark, ip);
        int result = subTaskPersistence.updateStatusAndRemark(clazz, id, oldStatus, newStatus, remark);
        System.out.println("update : " + result);
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
        TaskConfigDTO taskConfigDTO = new TaskConfigDTO();
        taskConfigDTO.setId("cfg_20170905184800");
        taskConfigDTO.setAppId("app_20170905184800");
        taskConfigDTO.setTaskConsumersClass("com.tools.action.udm.TaskProcessorImpl");
        taskConfigDTO.setTaskDataType(TaskDataTypeEnum.FILE_UTF8);
        taskConfigDTO.setDatasource("/Users/YJ/Documents/generator/test.txt");
        taskConfigDTO.setTaskConsumersMax(3);
        taskConfigDTO.setBatchSize(5);
        taskConfigDTO.setCronExpression("0/1 * * * * ? *");
        taskConfigDTO.setTaskStatus(TaskConfigStatusEnum.ACTIVE);
        taskConfigDTO.setTaskPriority(1);
//        taskConfigDTO.setTaskType(open.udm.server.enums.TaskTypeEnum.TIMING);

        List<TaskConfigDTO> taskConfigDTOList = new ArrayList<TaskConfigDTO>();
        taskConfigDTOList.add(taskConfigDTO);

        JobTaskUpdate jobTaskUpdate = BeanFactoryUtil.getBeanByClass(JobTaskUpdate.class);
        DefaultUDMClientContext defaultUDMClientContext = BeanFactoryUtil.getBeanByClass(DefaultUDMClientContext.class);

        System.out.println("*******************************************");
        System.out.println("===> serverBetaFlag:" + defaultUDMClientContext.getServerBetaFlag());
        jobTaskUpdate.updateTask(taskConfigDTOList);
        System.out.println("===>sleeping 10s...");
        Thread.sleep(10*1000);
        System.out.println("===>sleeping is over...");

        ServerInfoDTO serverInfoDTO = new ServerInfoDTO();
        serverInfoDTO.setBeta(true);
        serverInfoDTO.setServerIp("172.19.40.87");
        List<ServerInfoDTO> serverInfoDTOList = new ArrayList<>();
        serverInfoDTOList.add(serverInfoDTO);
        System.out.println("===>.before - serverBetaFlag:" + defaultUDMClientContext.getServerBetaFlag());
//        defaultUDMClientContext.updateServerBetaFlag(serverInfoDTOList, null);
        System.out.println("===>.after  - serverBetaFlag:" + defaultUDMClientContext.getServerBetaFlag());
        jobTaskUpdate.updateTask(taskConfigDTOList);

        System.out.println("===>sleeping 10s....");
        Thread.sleep(10*1000);
        System.out.println("===>sleeping is over....");
        serverInfoDTO.setBeta(false);
        System.out.println("===>..before - serverBetaFlag:" + defaultUDMClientContext.getServerBetaFlag());
//        defaultUDMClientContext.updateServerBetaFlag(serverInfoDTOList, null);
        System.out.println("===>..after  - serverBetaFlag:" + defaultUDMClientContext.getServerBetaFlag());
        jobTaskUpdate.updateTask(taskConfigDTOList);
    }
}
