package com.tools.quartz.task.jobMannager;

import com.tools.quartz.task.BaseTaskJobProxy;
import com.tools.quartz.task.jobMannager.entity.TaskEntity;
import com.yeepay.g3.utils.common.log.Logger;
import com.yeepay.g3.utils.common.log.LoggerFactory;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;


@Job(cronExpression = "0 0/30 * * * ? ")
@Component
public class JobMonitor extends BaseTaskJobProxy {

    private static final Logger LOGGER= LoggerFactory.getLogger(JobMonitor.class);

    @Override
    public void execute() {
        List<TaskEntity> taskEntities = getTastJobDAO().queryOverTimeTask();
        if(CollectionUtils.isNotEmpty(taskEntities)){
            LOGGER.info("定时超时任务处理",taskEntities.size());
            for (TaskEntity taskEnity:
                 taskEntities) {
                try {
                    getTastJobDAO().updateJobByKey(taskEnity.getTaskKey(),TaskEntity.JobStatusEnum.SLEEP.name());
                } catch (Exception e) {
                    LOGGER.error("超时处理报错",e);
                }
            }
        }
    }
}
