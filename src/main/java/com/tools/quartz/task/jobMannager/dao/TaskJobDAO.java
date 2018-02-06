package com.tools.quartz.task.jobMannager.dao;

import com.tools.quartz.task.jobMannager.entity.TaskEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskJobDAO {

    public int insertJob(TaskEntity taskEntity);

    public int updateJob(@Param(value = "taskKey") String taskKey, @Param(value = "lastStatus") String lastStatus, @Param(value = "newStatus") String newStatus);

    public int updateJobByKey(@Param(value = "taskKey") String taskKey, @Param(value = "newStatus") String newStatus);

    public TaskEntity queryTask(String taskKey);

    /**
     * 查询超时任务，过期时间10分钟
     * @return
     */
    public List<TaskEntity> queryOverTimeTask();

}
