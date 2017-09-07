package com.tools.quartz;

import com.alibaba.fastjson.JSON;
import org.quartz.*;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/8/23 上午11:01
 */
public class TimerManager01 {

    private static final String INIT_NAME = "init_name";
    private static final String INIT_GROUP = "init_group";
    private static final String INIT_METHOD = "toString";
    private static final String INIT_CRON_EXPRESSION = "0 0 0 1 * ?";
    private static final Object LOCK = new Object();
    private static boolean initialized = false;
    private static Scheduler scheduler = null;
    private static final Map<String, TimerEntity01> TIMER_ENTITY_MAP = new HashMap<String, TimerEntity01>();

    static {
        try {
            JobDetail jobDetail = getJobDetail(new Object(), INIT_METHOD, INIT_NAME, INIT_GROUP, null);
            Trigger trigger = getTrigger(jobDetail, INIT_NAME, INIT_GROUP, INIT_CRON_EXPRESSION, null);
            SchedulerFactoryBean schedulerFactoryBean = getScheduleFactoryBean(trigger);
            scheduler = schedulerFactoryBean.getScheduler();
            scheduler.deleteJob(jobDetail.getKey());
            scheduler.start();
            jobDetail = null;
            trigger = null;
            schedulerFactoryBean = null;
            initialized = true;
        } catch (Exception e) {
            initialized = false;
            e.printStackTrace();
        }
    }

    private static void checkInitialized() throws RuntimeException {
        if (!initialized) {
            throw new RuntimeException("TimerManager not initialized!");
        }
    }

    public static JobDetail getJobDetail(TimerEntity01 timerEntity01) throws Exception {
        return getJobDetail(timerEntity01.getTargetObject(), timerEntity01.getTargetMethod(), timerEntity01.getName(),
                timerEntity01.getGroup(), timerEntity01.getArguments());
    }

    public static Trigger getTrigger(JobDetail jobDetail, TimerEntity01 timerEntity01) {
        return getTrigger(jobDetail, timerEntity01.getName(), timerEntity01.getGroup(),
                timerEntity01.getCronExpression(), null);
    }

    public static JobDetail getJobDetail(Object targetObject, String targetMethod, String name, String group,
                                          Object[] arguments) throws Exception {
        MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        jobDetailFactoryBean.setTargetObject(targetObject);
        jobDetailFactoryBean.setTargetMethod(targetMethod);
        jobDetailFactoryBean.setName(name);
        jobDetailFactoryBean.setGroup(group);
        jobDetailFactoryBean.setArguments(arguments);
        jobDetailFactoryBean.setConcurrent(false); // test this
        jobDetailFactoryBean.afterPropertiesSet();
        return jobDetailFactoryBean.getObject();
    }

    public static Trigger getTrigger(JobDetail jobDetail, String name, String group, String cronExpression, String description) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(jobDetail);
        cronTriggerFactoryBean.setCronExpression(cronExpression);
        cronTriggerFactoryBean.setName(name);
        cronTriggerFactoryBean.setGroup(group);
        cronTriggerFactoryBean.setDescription(description);
        cronTriggerFactoryBean.afterPropertiesSet();
        return cronTriggerFactoryBean.getObject();
    }

    public static SchedulerFactoryBean getScheduleFactoryBean(Trigger trigger) throws Exception {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(trigger);
        schedulerFactoryBean.afterPropertiesSet();
        return schedulerFactoryBean;
    }

    public static void scheduleJob(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public static void deleteJob(JobDetail jobDetail) throws SchedulerException {
        scheduler.deleteJob(jobDetail.getKey());
    }

    public static boolean createOrUpdateJob(TimerEntity01 timerEntity01) {
        if (timerEntity01 == null) {
            return false;
        }
        try {
            synchronized (LOCK) { // 也可以不加锁,因为外层调用方当前只有一个线程
                TimerEntity01 timerEntity011 = TIMER_ENTITY_MAP.get(timerEntity01.getGroup() + timerEntity01.getName());
                if (timerEntity01.equals(timerEntity011)) {
                    return true;
                }
                JobDetail newJobDetail = getJobDetail(timerEntity01);
                Trigger newTrigger = getTrigger(newJobDetail, timerEntity01);

//                JobDetail newJobDetail = JobBuilder.newJob(MyJob001.class).withIdentity("001", "001").build();


                System.out.println("*** jobDetail: " + JSON.toJSONString(newJobDetail));
                System.out.println("*** newTrigger: " + JSON.toJSONString(newTrigger));
                scheduler.scheduleJob(newJobDetail, newTrigger);
                TIMER_ENTITY_MAP.put(timerEntity01.getGroup() + timerEntity01.getName(), timerEntity01);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteJob(TimerEntity01 timerEntity01) throws SchedulerException {
        if (timerEntity01 == null) {
            return false;
        }
        synchronized (LOCK) {
            TriggerKey triggerKey = TriggerKey.triggerKey(timerEntity01.getName(), timerEntity01.getGroup());
            // 停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(timerEntity01.getName(), timerEntity01.getGroup()));
            TIMER_ENTITY_MAP.remove(timerEntity01.getGroup() + timerEntity01.getName());
        }
        return true;
    }

    public static void test() throws Exception {
        List<String> groupList = scheduler.getTriggerGroupNames();
        System.out.println(JSON.toJSONString(groupList));
        List<JobExecutionContext> jobExecutionContextList = scheduler.getCurrentlyExecutingJobs();
        for (JobExecutionContext context : jobExecutionContextList) {
            System.out.println("...." + context.getJobDetail().getKey().toString());
        }
    }
}
