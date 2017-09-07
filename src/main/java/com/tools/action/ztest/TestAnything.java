package com.tools.action.ztest;

import com.tools.action.udm.TaskProcessorImpl;
import com.tools.util.BeanFactoryUtil;
import com.tools.ztest.facade.RmiMockTester;
import com.tools.ztest.facade.impl.InterfaceTest;
import com.tools.ztest.javabeans.Dog;
import javassist.*;
import open.udm.client.jobs.JobTaskUpdate;
import open.udm.client.utils.BeanFactoryUtils;
import open.udm.server.dto.TaskConfigDTO;
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

    @RequestMapping("/home")
    public ModelAndView compress(HttpServletRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("ztest/home");

        // 测试代码 - start
        testUdm();
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
        taskConfigDTO.setId("cfg_20170905184800");
        taskConfigDTO.setAppId("app_20170905184800");
        taskConfigDTO.setTaskConsumersClass("com.tools.action.udm.TaskProcessorImpl");
        taskConfigDTO.setTaskDataType(TaskDataTypeEnum.FILE);
        taskConfigDTO.setDatasource("/Users/YJ/Documents/generator/20170414.txt");
        taskConfigDTO.setTaskConsumersMax(3);
        taskConfigDTO.setBatchSize(5);
        taskConfigDTO.setCronExpression("0 1/1 * * * ? *");
        taskConfigDTO.setTaskStatus(TaskConfigStatusEnum.ACTIVE);

        List<TaskConfigDTO> taskConfigDTOList = new ArrayList<TaskConfigDTO>();
        taskConfigDTOList.add(taskConfigDTO);

        JobTaskUpdate jobTaskUpdate = BeanFactoryUtils.getBeanByClass(JobTaskUpdate.class);
        jobTaskUpdate.updateTask(taskConfigDTOList);
    }
}
