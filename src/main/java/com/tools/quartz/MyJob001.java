package com.tools.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Date;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/8/15 下午5:43
 */
public class MyJob001 implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("============> Hello world, quartz001 " + new Date());
    }

    public void executeWithoutParams() {
        System.out.println("============> Without params, Hello world, quartz001 " + new Date());
    }

    public void executeWithParams(Integer i) {
        System.out.println("============> With params, Hello world, quartz001 , i: " + i + ", date: " + new Date());
    }

    public static void main(String[] args) throws Exception {
        test002();
    }

    private static void test001() throws Exception {
//        JobDetail jobDetail001 = new JobDetail();
//        jobDetail001.setName("job_name_001");
//        jobDetail001.setJobClass(MyJob001.class);
//        jobDetail001.setGroup("job_group_001");
//
//        CronTriggerBean cronTrigger001 = new CronTriggerBean();
//        cronTrigger001.setJobDetail(jobDetail001);
//        cronTrigger001.setCronExpression("0/1 * * * * ?");
//        cronTrigger001.setName(jobDetail001.getName());
//        cronTrigger001.setGroup(jobDetail001.getGroup());
//        cronTrigger001.setJobName(jobDetail001.getName());
//        cronTrigger001.setJobGroup(jobDetail001.getGroup());
//
//        JobDetail jobDetail002 = new JobDetail();
//        jobDetail002.setName("job_name_002");
//        jobDetail002.setJobClass(MyJob002.class);
//        jobDetail002.setGroup("job_group_002");
//
//        CronTriggerBean cronTrigger002 = new CronTriggerBean();
//        cronTrigger002.setJobDetail(jobDetail002);
//        cronTrigger002.setCronExpression("0/1 * * * * ?");
//        cronTrigger002.setName(jobDetail002.getName());
//        cronTrigger002.setGroup(jobDetail002.getGroup());
//        cronTrigger002.setJobName(jobDetail002.getName());
//        cronTrigger002.setJobGroup(jobDetail002.getGroup());
//
//        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
//        schedulerFactory.setTriggers(cronTrigger001, cronTrigger002);
//        schedulerFactory.afterPropertiesSet();
//
//        Scheduler scheduler = schedulerFactory.getScheduler();
//        scheduler.start();
//
//        TimeUnit.SECONDS.sleep(10);
//        System.out.println();
//
//        cronTrigger001.setCronExpression("0/2 * * * * ?");
//        scheduler.deleteJob(cronTrigger002.getName(), cronTrigger002.getGroup());
    }

    private static void test002() throws Exception {
        MyJob001 myJob001 = new MyJob001();
        String name = "name_myJob001";
        String group = "group_myJob001";
        Object[] arguments = {Integer.MAX_VALUE};
        MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        jobDetailFactoryBean.setTargetObject(myJob001);
        jobDetailFactoryBean.setTargetMethod("executeWithParams");
        jobDetailFactoryBean.setName(name);
        jobDetailFactoryBean.setGroup(group);
        jobDetailFactoryBean.setArguments(arguments);
        jobDetailFactoryBean.afterPropertiesSet();

        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(jobDetailFactoryBean.getObject());
        cronTriggerFactoryBean.setCronExpression("0/1 * * * * ? *");
        cronTriggerFactoryBean.setName(name);
        cronTriggerFactoryBean.setGroup(group);
        cronTriggerFactoryBean.afterPropertiesSet();

        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(cronTriggerFactoryBean.getObject());
        schedulerFactoryBean.afterPropertiesSet();

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.start();
    }
}
