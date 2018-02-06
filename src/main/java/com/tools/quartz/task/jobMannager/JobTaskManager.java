package com.tools.quartz.task.jobMannager;

import com.tools.quartz.task.BaseTaskJobProxy;
import com.tools.quartz.task.jobMannager.dao.TaskJobDAO;
import com.tools.quartz.task.jobMannager.entity.TaskEntity;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;

public class JobTaskManager implements InitializingBean,ApplicationContextAware {

    @Autowired
    private TaskJobDAO taskJobDAO;

    private static final Logger logger = LoggerFactory.getLogger(JobTaskManager.class);

    private final Object LOCK = new Object();
    private final SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private Scheduler scheduler = null;


    private static final String GROUP_NAME="kai-my-task-group";


    private static final String JOB_METHOD_NAME="job";

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext=applicationContext;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            try {
                scheduler = schedulerFactory.getScheduler();
                // 获取所有bean name
                String[] beanNames = applicationContext.getBeanNamesForType(BaseTaskJobProxy.class);
                for (String beanName : beanNames) {
                    Class<?> targetClass = applicationContext.getType(beanName);
                    // 循环判断是否标记了TriggerType注解
                    if (targetClass.isAnnotationPresent(Job.class)) {
                        Object targetObject = applicationContext.getBean(beanName);
                        Job triggerType = targetClass.getAnnotation(Job.class);
                        // 获取时间表达式
                        String cronExpression = triggerType.cronExpression();
                        String targetMethod = JOB_METHOD_NAME;

                        // 注册定时器业务类
                        registerJobs(targetObject, targetMethod, beanName, cronExpression);
                        try {
                            taskJobDAO.insertJob(new TaskEntity().setTaskKey(targetObject.getClass().getSimpleName()).setTaskStatus(TaskEntity.JobStatusEnum.SLEEP.toString()));
                        } catch (DuplicateKeyException e) {
                             taskJobDAO.updateJobByKey(targetObject.getClass().getSimpleName(),TaskEntity.JobStatusEnum.SLEEP.toString());
                            }
                    }
                }



                scheduler.start();

            } catch (SchedulerException e) {
                logger.error("添加定时任务报错",e);
            }


        } catch (Exception e) {
            throw new BeanCreationException(e.getMessage());
        }
    }

    private void registerJobs(Object targetObject, String targetMethod, String beanName, String cronExpression) throws Exception {
        JobEntity jobEntity=new JobEntity();
        jobEntity.setCronExpression(cronExpression);
        jobEntity.setGroup(GROUP_NAME);
        jobEntity.setName(beanName);
        jobEntity.setTargetMethod(targetMethod);
        jobEntity.setTargetObject(targetObject);
        JobDetail newJobDetail = getJobDetail(jobEntity);
        Trigger newTrigger = getTrigger(newJobDetail, jobEntity);
        scheduler.scheduleJob(newJobDetail, newTrigger);
    }






    public JobDetail getJobDetail(JobEntity jobEntity) throws Exception {
        return getJobDetail(jobEntity.getTargetObject(), jobEntity.getTargetMethod(), jobEntity.getName(),
                jobEntity.getGroup(),  null);
    }

    public Trigger getTrigger(JobDetail jobDetail, JobEntity jobEntity) {
        return getTrigger(jobDetail, jobEntity.getName(), jobEntity.getGroup(),
                jobEntity.getCronExpression(), null);
    }

    public JobDetail getJobDetail(Object targetObject, String targetMethod, String name, String group,
                                  Object[] arguments) throws Exception {
        MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        jobDetailFactoryBean.setTargetObject(targetObject);
        jobDetailFactoryBean.setTargetMethod(targetMethod);
        jobDetailFactoryBean.setName(name);
        jobDetailFactoryBean.setGroup(group);
        jobDetailFactoryBean.setArguments(null);
        jobDetailFactoryBean.setConcurrent(false);
        jobDetailFactoryBean.afterPropertiesSet();
        return jobDetailFactoryBean.getObject();
    }

    public Trigger getTrigger(JobDetail jobDetail, String name, String group, String cronExpression, String description) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(jobDetail);
        cronTriggerFactoryBean.setCronExpression(cronExpression);
        cronTriggerFactoryBean.setName(name);
        cronTriggerFactoryBean.setGroup(group);
        cronTriggerFactoryBean.setDescription(description);
        cronTriggerFactoryBean.afterPropertiesSet();
        return cronTriggerFactoryBean.getObject();
    }



    public class JobEntity {

        /**
         * 分组
         */
        private String group;

        /**
         * 名称
         */
        private String name;

        /**
         * cron表达式
         */
        private String cronExpression;



        /**
         * 定时任务消费者
         */
        private Object targetObject;

        /**
         * 定时任务消费者执行方法
         */
        private String targetMethod;



        public JobEntity() {};

        public String getName() {
            return name;
        }

        public JobEntity setName(String name) {
            this.name = name;
            return this;
        }

        public String getCronExpression() {
            return cronExpression;
        }

        public JobEntity setCronExpression(String cronExpression) {
            this.cronExpression = cronExpression;
            return this;
        }

        public String getGroup() {
            return group;
        }

        public JobEntity setGroup(String group) {
            this.group = group;
            return this;
        }

        public Object getTargetObject() {
            return targetObject;
        }

        public JobEntity setTargetObject(Object targetObject) {
            this.targetObject = targetObject;
            return this;
        }

        public String getTargetMethod() {
            return targetMethod;
        }

        public JobEntity setTargetMethod(String targetMethod) {
            this.targetMethod = targetMethod;
            return this;
        }

    }


}
