package com.tools.quartz.task.jobMannager.entity;

import java.util.Date;

public class TaskEntity {

    private String taskKey;

    private String taskStatus;

    private Date createTime;

    private Date lastUpdateTime;

    private Date LastServiceTime;


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getTaskKey() {
        return taskKey;
    }

    public TaskEntity setTaskKey(String taskKey) {
        this.taskKey = taskKey;
        return this;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public TaskEntity setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
        return this;
    }




    /**
     * 任务状态
     */
    public static  enum JobStatusEnum{

        DESTROY,

        SLEEP,

        WORKING;

    }
}
