package com.tools.quartz.task.jobMannager;

import com.tools.quartz.task.BaseTaskJobProxy;
import com.tools.quartz.task.jobMannager.dao.TaskJobDAO;
import com.tools.quartz.task.jobMannager.entity.TaskEntity;
import com.yeepay.g3.utils.common.log.Logger;
import com.yeepay.g3.utils.common.log.LoggerFactory;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Job(cronExpression = "0 0/30 * * * ? ")
@Component
public class JobMonitor implements InitializingBean {

    private static final Logger LOGGER= LoggerFactory.getLogger(JobMonitor.class);

    @Autowired
    private TaskJobDAO tastJobDAO;

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread( new Runnable(){
            @Override
            public void run() {
                LOGGER.info("启动定时任务监听");
                while (true) {
                    try {
                        Thread.sleep(30*60*1000);
                        List<TaskEntity> taskEntities = tastJobDAO.queryOverTimeTask();
                        if (CollectionUtils.isNotEmpty(taskEntities)) {
                            LOGGER.info("定时超时任务处理", taskEntities.size());
                            for (TaskEntity taskEnity :
                                    taskEntities) {
                                try {
                                    tastJobDAO.updateJobByKey(taskEnity.getTaskKey(), TaskEntity.JobStatusEnum.SLEEP.name());
                                } catch (Exception e) {
                                    LOGGER.error("超时处理报错", e);
                                }
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.error("超时检查定时失败", e);
                    }
                }
            }
        }).start();


    }
}
