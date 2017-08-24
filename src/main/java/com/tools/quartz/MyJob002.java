package com.tools.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/8/21 下午5:34
 */
public class MyJob002 implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("************* Hello world, quartz002 " + new Date());
    }

    public void executeWithoutParams() {
        System.out.println("************* Without params, Hello world, quartz002, date: " + new Date());
    }

    public void executeWithParams(Integer i) {
        System.out.println("************* With params, Hello world, quartz002 , i: " + i + ", date: " + new Date());
    }
}
