package com.tools.quartz.task.jobMannager.dao;

import org.apache.ibatis.annotations.Param;

public interface TaskRecordDAO {

    /**
     * 插入任务
     */
    public long insertRecord(@Param(value = "taskKey") String taskKey, @Param(value = "dateLock") String dateLock);
}
