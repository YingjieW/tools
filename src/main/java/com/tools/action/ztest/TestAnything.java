package com.tools.action.ztest;

import com.alibaba.fastjson.JSON;
import com.tools.action.udm.TaskProcessorImpl;
import com.tools.action.udm.UdmMainTaskEntity;
import com.tools.util.BeanFactoryUtil;
import com.tools.ztest.facade.RmiMockTester;
import com.tools.ztest.facade.impl.InterfaceTest;
import com.tools.ztest.javabeans.Dog;
import javassist.*;
import open.udm.client.jobs.JobTaskUpdate;
import open.udm.client.persistence.MainTaskPersistence;
import open.udm.client.processer.taskinfo.ModifyTaskInfoProcessor;
import open.udm.client.utils.BeanFactoryUtils;
import open.udm.server.dto.TaskConfigDTO;
import open.udm.server.dto.TaskInfoDTO;
import open.udm.server.enums.TaskConfigStatusEnum;
import open.udm.server.enums.TaskDataTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/4/29 下午2:23
 */
@Controller
@RequestMapping("/ztest")
public class TestAnything extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(TestAnything.class);

    @Autowired
    RmiMockTester rmiMockTester;
    @Autowired
    InterfaceTest interfaceTest;
    @Autowired
    MainTaskPersistence mainTaskPersistence;

    @RequestMapping("/home")
    public ModelAndView compress(HttpServletRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("ztest/home");

        // 测试代码 - start
        System.out.println("***************************** START *****************************");
        testUdm();
        System.out.println("*****************************  END  *****************************");
        // 测试代码 - end

        return mav;
    }

    @RequestMapping("/requestParam")
    public void requestParamTest(HttpServletRequest request, HttpServletResponse response, @RequestParam("param") String param) {
        logger.info("#######   params: " +  param);
        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.write("param : " + param);
        } catch (Throwable t) {
            logger.error("requestParamTest unknown error.", t);
        }
    }

    private void testMakeClass() {
        try {
            String className = "com.tools.ztest.javabeans.Dog";
            ClassPool classPool = ClassPool.getDefault();
            // 生成类
            CtClass ctClass = classPool.makeClass(className);
            // 添加属性
            CtField ctField = new CtField(CtClass.intType, "i", ctClass);
            ctClass.addField(ctField);
            // 添加方法
            CtMethod ctMethod = CtMethod.make("public String toString() {  return (\"Hello world. this.i = \" + this.i); }", ctClass);
            ctClass.addMethod(ctMethod);
            Class clazz = ctClass.toClass();
            logger.info("=====  clazz.newInstance: " + clazz.newInstance());
            logger.info("=====  clazz.getName: " + clazz.getName());
        } catch (Throwable t) {
            logger.error("testMakeClass error.", t);
        }
    }

    private void testGetClass() {
        try {
            String className = "com.tools.ztest.javabeans.Dog";
            ClassPool classPool = ClassPool.getDefault();
            // 将tomcat的路径注入到ClassPool中
            classPool.insertClassPath(new ClassClassPath(this.getClass()));
            CtClass ctClass = classPool.get(className);
            CtMethod ctMethod = ctClass.getDeclaredMethod("move");
            ctMethod.insertBefore("System.out.println(\"###  x = \" + x + \", y = \" + y);");
            ctMethod.insertBefore("System.out.println(\"##  dx = \" + dx + \",dy = \" + dy);");
            ctMethod.insertAfter("System.out.println(\"###  x = \" + x + \", y = \" + y);");
            Class clazz = ctClass.toClass();
            Dog dog = (Dog) clazz.newInstance();
            logger.info("=====   before: " + dog.toString());
            dog.move(1, 1);
            logger.info("=====   after : " + dog.toString());
        } catch (Throwable t) {
            logger.error("testGetClass error.", t);
        }
    }

    private void testGetBean() {
        String[] beanNames = {"testAnything", "beanFactoryUtil"};
        for (String beanName : beanNames) {
            try {
                Object o = BeanFactoryUtil.getBeanByName(beanName);
                System.out.println("---> beanName: " + beanName + ", o: " + (o == null ? null : o.getClass().getName()));
            } catch (Exception e) {
                logger.error("===> beanName:" + beanName + ", exception:" + e.getMessage(), e);
                System.out.println("\n");
            }
        }

        Class[] classes = {TestAnything.class, BeanFactoryUtil.class, TaskProcessorImpl.class};
        for (Class clazz : classes) {
            try {
                Object o = BeanFactoryUtil.getBeanByClass(clazz);
                System.out.println("---> clazz: " + clazz + ", o: " + (o == null ? null : o.getClass().getName()));
            } catch (Exception e) {
                logger.error("===> clazz:" + clazz + ", exception:" + e.getMessage(), e);
                System.out.println("\n");
            }
        }
    }

    private void testUdm() {
        TaskConfigDTO taskConfigDTO = new TaskConfigDTO();
        taskConfigDTO.setId("test_config_id_000");
        taskConfigDTO.setAppId("test_app_id");
        taskConfigDTO.setTaskConsumersClass("com.tools.action.udm.TaskProcessorImpl");
        taskConfigDTO.setTaskDataType(TaskDataTypeEnum.DB);
//        taskConfigDTO.setDatasource("/Users/YJ/Documents/generator/20170414.txt;/Users/YJ/Documents/generator/1051100010014250_0828.csv");
//        taskConfigDTO.setDatasource("/Users/YJ/Documents/generator/test01.txt");
        taskConfigDTO.setDatasource("select s.create_time, s.id, s.settle_amount from yqtaccounting.TBL_SETTLEMENT_ACCOUNTING s where 1=1 and s.create_time > '2017-07-01' and s.create_time < '2017-08-01' order by id with ur");
        taskConfigDTO.setTaskConsumersMax(3);
        taskConfigDTO.setBatchSize(5);
        taskConfigDTO.setCronExpression("0 1/1 * * * ? *");
        taskConfigDTO.setTaskParameter("2017-09-11");
        taskConfigDTO.setTaskPriority(5);
        taskConfigDTO.setTaskStatus(TaskConfigStatusEnum.ACTIVE);

        TaskConfigDTO taskConfigDTO1 = new TaskConfigDTO();
        taskConfigDTO1.setId("test_config_id_001");
        taskConfigDTO1.setAppId("test_app_id");
        taskConfigDTO1.setTaskConsumersClass("com.tools.action.udm.TaskProcessorImpl");
        taskConfigDTO1.setTaskDataType(TaskDataTypeEnum.FILE);
        taskConfigDTO1.setDatasource("/Users/YJ/Documents/generator/test.txt");
//        taskConfigDTO1.setDatasource("select s.* from yqtaccounting.TBL_SETTLEMENT_ACCOUNTING s where 1=1 and s.create_time > '2017-07-01' and s.create_time < '2017-08-01' with ur");
        taskConfigDTO1.setTaskConsumersMax(3);
        taskConfigDTO1.setBatchSize(50);
        taskConfigDTO1.setCronExpression("0 2/2 * * * ? *");
        taskConfigDTO1.setTaskParameter("2017-09-11");
        taskConfigDTO1.setTaskPriority(6);
        taskConfigDTO1.setTaskStatus(TaskConfigStatusEnum.ACTIVE);

        TaskConfigDTO taskConfigDTO2 = new TaskConfigDTO();
        taskConfigDTO2.setId("test_config_id_002");
        taskConfigDTO2.setAppId("test_app_id");
        taskConfigDTO2.setTaskConsumersClass("com.tools.action.udm.TaskProcessorImpl");
        taskConfigDTO2.setTaskDataType(TaskDataTypeEnum.DB);
        taskConfigDTO2.setDatasource("select create_time from yqtaccounting.TBL_SETTLEMENT_ACCOUNTING s where 1=1 and s.create_time > '2017-07-01' and s.create_time < '2017-08-01' with ur");
        taskConfigDTO2.setTaskConsumersMax(3);
        taskConfigDTO2.setBatchSize(50);
        taskConfigDTO2.setCronExpression("0 2/2 * * * ? *");
        taskConfigDTO2.setTaskParameter("2017-09-11");
        taskConfigDTO2.setTaskPriority(3);
        taskConfigDTO2.setTaskStatus(TaskConfigStatusEnum.ACTIVE);

        List<TaskConfigDTO> taskConfigDTOList = new ArrayList<TaskConfigDTO>();
        taskConfigDTOList.add(taskConfigDTO);
        taskConfigDTOList.add(taskConfigDTO1);
        taskConfigDTOList.add(taskConfigDTO2);

        JobTaskUpdate jobTaskUpdate = BeanFactoryUtils.getBeanByClass(JobTaskUpdate.class);
        jobTaskUpdate.updateTask(taskConfigDTOList);
//
//        TaskConfigDTO taskConfigDTO2 = new TaskConfigDTO();
//        taskConfigDTO2.setId("test_config_id_002");
//        taskConfigDTO2.setAppId("test_app_id");
//        taskConfigDTO2.setTaskConsumersClass("com.tools.action.udm.TaskProcessorImpl");
//        taskConfigDTO2.setTaskDataType(TaskDataTypeEnum.LIST);
//        taskConfigDTO2.setTaskConsumersMax(3);
//        taskConfigDTO2.setBatchSize(5);
//        taskConfigDTO2.setCronExpression("0 2/2 * * * ? *");
//        taskConfigDTO2.setTaskParameter("2017-09-05");
//        taskConfigDTO2.setTaskStatus(TaskConfigStatusEnum.ACTIVE);
//
//        List<String> list = new ArrayList<String>();
//        list.add("list_test_001");
//        list.add("list_test_002");
//        list.add("list_test_003");
//        MainTaskProcessor mainTaskProcessor = BeanFactoryUtils.getBeanByClass(MainTaskProcessor.class);
//        mainTaskProcessor.process(taskConfigDTO2, list);
    }

    private void testTaskModify() {
        TaskInfoDTO taskInfoDTO = new TaskInfoDTO();
        taskInfoDTO.setTableName("TBL_MAIN_TASK");
        taskInfoDTO.setId("DMMTC20170908193100033m8fZBBJ");
        taskInfoDTO.setTaskStatus("INIT");

        TaskInfoDTO taskInfoDTO1 = new TaskInfoDTO();
        taskInfoDTO1.setTableName("TBL_MAIN_TASK");
        taskInfoDTO1.setId("DMMTC20170908193100033m8fZBBJ");
        taskInfoDTO1.setDatasource("Test_modify_main_datasource");

        TaskInfoDTO taskInfoDTO2 = new TaskInfoDTO();
        taskInfoDTO2.setTableName("TBL_SUB_TASK");
        taskInfoDTO2.setId("DMSTC20170908193300689LH8MZ27");
        taskInfoDTO2.setTaskStatus("INIT");

        TaskInfoDTO taskInfoDTO3 = new TaskInfoDTO();
        taskInfoDTO3.setTableName("TBL_SUB_TASK");
        taskInfoDTO3.setId("DMSTC20170908193300689LH8MZ27");
        taskInfoDTO3.setDatasource("Test_modify_sub_datasource");

        ModifyTaskInfoProcessor modifyTaskInfoProcessor = BeanFactoryUtils.getBeanByClass(ModifyTaskInfoProcessor.class);
//        modifyTaskInfoProcessor.modifyMainTaskStatus(taskInfoDTO);
//        modifyTaskInfoProcessor.modifyMainTaskDatasource(taskInfoDTO1);
//        modifyTaskInfoProcessor.modifySubTaskStatus(taskInfoDTO2);
//        modifyTaskInfoProcessor.modifySubTaskDatasource(taskInfoDTO3);

        String controllerId = "test_controller_id";
        MainTaskPersistence mainTaskPersistence = BeanFactoryUtil.getBeanByClass(MainTaskPersistence.class);
        List<UdmMainTaskEntity> entityList = mainTaskPersistence.queryByControllerId(UdmMainTaskEntity.class, controllerId);
        System.out.println(" ***** entityList: " + JSON.toJSONString(entityList));
    }
}
