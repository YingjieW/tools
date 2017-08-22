package com.tools.quartz;

import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/8/15 下午5:44
 */
public class TimeJobManager {

    static {
        init();
    }

    private static SchedulerFactoryBean schedulerFactory;
    private static CronTriggerFactoryBean cronTrigger;

    public static void init() {
        schedulerFactory = new SchedulerFactoryBean();
        cronTrigger = new CronTriggerFactoryBean();
        cronTrigger.setCronExpression("0/1 * * * * ?");
//        schedulerFactory.setTriggers(cronTrigger);
    }
}
