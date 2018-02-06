package com.tools.quartz.task;

import com.tools.quartz.task.jobMannager.dao.TaskJobDAO;
import com.tools.quartz.task.jobMannager.dao.TaskRecordDAO;
import com.tools.quartz.task.jobMannager.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BaseTaskJobProxy {


    private static final Logger logger = LoggerFactory.getLogger(BaseTaskJobProxy.class);

    @Autowired
    private TaskJobDAO taskJobDAO;

    @Autowired
    private TaskRecordDAO taskRecordDAO;

    public TaskJobDAO getTaskJobDAO() {
        return taskJobDAO;
    }

    /**
     * 业务处理类
     */
    public  abstract  void execute();


    /**
     *定时任务执行方法
     */
    public void job(){
        try {
            boolean before = before();
            if(before) {
                try {
                    execute();
                } catch (Exception e) {
                    logger.error("定时执行方法异常",e);
                }
                after();
            }
        } catch (Exception e) {
            logger.error("定时执行方法系统异常,结束任务",e);
            taskJobDAO.updateJobByKey(this.getClass().getSimpleName(), TaskEntity.JobStatusEnum.SLEEP.name());
        }
    }


    public boolean before(){
        int i = 0;
        try {
            taskRecordDAO.insertRecord(this.getClass().getSimpleName(),getMinuTime(new Date()));
            i = taskJobDAO.updateJob(this.getClass().getSimpleName(), TaskEntity.JobStatusEnum.SLEEP.name(), TaskEntity.JobStatusEnum.WORKING.name());
        }catch (org.springframework.dao.DuplicateKeyException de){
            //do nothing
            return false;
        }catch (Exception e) {
            logger.error("定时任务前处理执行失败",e);
        }
        return i>0;
    }

    public void after(){
        int i = taskJobDAO.updateJob(this.getClass().getSimpleName(), TaskEntity.JobStatusEnum.WORKING.name(), TaskEntity.JobStatusEnum.SLEEP.name());
    }

    /**
     * 获取服务器分单位时间锁
     * @param date
     * @return
     */
    private static String getMinuTime(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmm");
        String format = sdf.format(date);
        return format;
    }



}

