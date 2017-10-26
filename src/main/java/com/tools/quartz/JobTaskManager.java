package com.tools.quartz;

import com.alibaba.fastjson.JSON;
import open.udm.client.entity.JobEntity;
import open.udm.client.processer.maintask.MainTaskProcessor;
import open.udm.client.processer.maintask.MainTaskProcessorImpl;
import open.udm.client.utils.CheckUtils;
import open.udm.client.utils.StringUtils;
import open.udm.server.dto.TaskConfigDTO;
import open.udm.server.enums.TaskConfigStatusEnum;
import open.udm.server.enums.TaskDataTypeEnum;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/8/29 下午3:40
 */
//@Component
public class JobTaskManager implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(open.udm.client.jobs.JobTaskManager.class);

//    @Autowired
    MainTaskProcessor mainTaskProcessor = new MainTaskProcessorImpl();

    private final String MAIN_TASK_PROCESSOR_METHOD_NAME = "process";
    private final Object LOCK = new Object();
    private final SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private Scheduler scheduler = null;
    private final Map<String, JobEntity> JOB_ENTITY_MAP = new HashMap<String, JobEntity>();

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            scheduler = schedulerFactory.getScheduler();
            scheduler.start();
        } catch (Exception e) {
            throw new BeanCreationException(e.getMessage());
        }
    }

    private void checkInitialized() throws RuntimeException {
        if (scheduler == null) {
            throw new RuntimeException("TimerManager not initialized!");
        }
    }


    /**
     * 创建定时任务
     * @param taskConfigDTO
     * @return
     */
    public boolean createOrUpdateJob(TaskConfigDTO taskConfigDTO) {
        checkTaskConfigDTO(taskConfigDTO);
        JobEntity jobEntity = new JobEntity(taskConfigDTO, mainTaskProcessor, MAIN_TASK_PROCESSOR_METHOD_NAME);
//        JobEntity jobEntity = new JobEntity(taskConfigDTO, new MyJob001(), "executeWithoutParams");
        return createOrUpdateJob(jobEntity);
    }

    private void checkTaskConfigDTO(TaskConfigDTO taskConfigDTO) {
        if (CheckUtils.isNull(taskConfigDTO, taskConfigDTO.getAppId(), taskConfigDTO.getId(), taskConfigDTO.getTaskStatus(),taskConfigDTO.getCronExpression())) {
            throw new IllegalArgumentException("TaskConfigDTO is illegal.");
        }
    }

    /**
     * 创建定时任务
     * @param jobEntity
     * @return
     */
    public boolean createOrUpdateJob(JobEntity jobEntity) {
        checkInitialized();
        if (jobEntity == null) {
            return false;
        }
        try {
            if (TaskConfigStatusEnum.FROZEN.equals(jobEntity.getJobStatus())) {
                return deleteJob(jobEntity);
            }
            synchronized (LOCK) {   // 也可以不加锁,因为udm外层调用方当前只有一个线程
                JobEntity that = JOB_ENTITY_MAP.get(jobEntity.getGroup() + jobEntity.getName());
                if (that == null) {
                    addJob(jobEntity);
                } else if (jobEntity.equals(that)) {
                    return true;
                } else {
                    deleteJob(jobEntity);
                    addJob(jobEntity);
                }
            }
        } catch (Exception e) {
            logger.error("createOrUpdateJob failed.", e);
            return false;
        }
        return true;
    }

    /**
     * 添加定时任务
     * @param jobEntity
     * @return
     * @throws Exception
     */
    public boolean addJob(JobEntity jobEntity) throws Exception {
        if (jobEntity == null) {
            return false;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("add job:{}", JSON.toJSONString(jobEntity));
        }
        synchronized (LOCK) {
            JobDetail newJobDetail = getJobDetail(jobEntity);
            Trigger newTrigger = getTrigger(newJobDetail, jobEntity);
            scheduler.scheduleJob(newJobDetail, newTrigger);
            JOB_ENTITY_MAP.put(StringUtils.concatenate(jobEntity.getGroup(), jobEntity.getName()), jobEntity);
        }
        return true;
    }

    /**
     * 删除定时任务
     * @param jobEntity
     * @return
     * @throws SchedulerException
     */
    public boolean deleteJob(JobEntity jobEntity) throws SchedulerException {
        if (jobEntity == null) {
            return false;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("delete job:{}", JSON.toJSONString(jobEntity));
        }
        synchronized (LOCK) {
            if (!JOB_ENTITY_MAP.containsKey(StringUtils.concatenate(jobEntity.getGroup(), jobEntity.getName()))) {
                if (logger.isDebugEnabled()) {
                    logger.debug("定时任务已删除,无需重复删除");
                }
                return true;
            }
            TriggerKey triggerKey = TriggerKey.triggerKey(jobEntity.getName(), jobEntity.getGroup());
            // 停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(jobEntity.getName(), jobEntity.getGroup()));
            JOB_ENTITY_MAP.remove(StringUtils.concatenate(jobEntity.getGroup(), jobEntity.getName()));
        }
        return true;
    }

    public JobDetail getJobDetail(JobEntity jobEntity) throws Exception {
        return getJobDetail(jobEntity.getTargetObject(), jobEntity.getTargetMethod(), jobEntity.getName(),
                jobEntity.getGroup(), jobEntity.getArguments());
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
        jobDetailFactoryBean.setArguments(arguments);
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

    public SchedulerFactoryBean getScheduleFactoryBean(Trigger trigger) throws Exception {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(trigger);
        schedulerFactoryBean.afterPropertiesSet();
        return schedulerFactoryBean;
    }

    public void scheduleJob(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public void deleteJob(JobDetail jobDetail) throws SchedulerException {
        scheduler.deleteJob(jobDetail.getKey());
    }

    public void clear() {
        try {
            synchronized (LOCK) {
                scheduler.clear();
                JOB_ENTITY_MAP.clear();
            }
        } catch (SchedulerException e) {
            logger.error("Clear scheduler exception:", e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
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
        taskConfigDTO.setTaskType(open.udm.server.enums.TaskTypeEnum.TIMING);

        JobTaskManager jobTaskManager = new JobTaskManager();
        jobTaskManager.afterPropertiesSet();
        jobTaskManager.createOrUpdateJob(taskConfigDTO);

        System.out.println("--- sleep 10s.");
        TimeUnit.SECONDS.sleep(10);
        System.out.println("--- sleep is over.");

        jobTaskManager.clear();

        System.out.println("--- sleep 3s.");
        TimeUnit.SECONDS.sleep(10);
        System.out.println("--- sleep is over.");

        taskConfigDTO.setCronExpression("0/2 * * * * ? *");
        jobTaskManager.createOrUpdateJob(taskConfigDTO);
        System.out.println("--- updated....");
    }
}
