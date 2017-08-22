//package com.tools.quartz;
//
//import org.quartz.JobDataMap;
//import org.quartz.Scheduler;
//
///**
// * Description:
// *
// * @author yingjie.wang
// * @since 17/8/21 下午4:23
// */
//public class TimerManager {
//
//    private Scheduler scheduler;
//
//    /**
//     * 创建或者修改
//     * @param timer
//     * @param paramsMap 参数
//     * @param isStateFull 是否是一个同步定时任务，true：同步，false：异步
//     * true setStatefulMethodInvokingJob(xxx.class)   StatefulMethodInvokingJob
//     * false setMethodInvokingJob(xxx.class)   MethodInvokingJob
//     *
//     * @return
//     */
//    public  boolean createOrUpdateTimer(TimerEntity timer,
//                                        JobDataMap paramsMap, boolean isStateFull) {
//        if (timer == null) {
//            return false;
//        }
//        try {
//            //让trigger的group 等于 jobDetail的
//            //jobDetail 和 trigger(这个可以有多个) 一对一 方便管理，操作也简单
//            CronTrigger trigger = (CronTrigger) scheduler
//                    .getTrigger(timer.getTriggerName(),
//                            timer.getGroup());
//            // 如果不存在该trigger则创建一个
//            if (null == trigger) {
//                JobDetail jobDetail = null;
//                if (isStateFull) {
//                    jobDetail = new JobDetail(timer.getName(),
//                            timer.getGroup(),
//                            timer.getStatefulMethodInvokingJob());
//                } else {
//                    jobDetail = new JobDetail(timer.getName(),timer.getGroup(),
//                            timer.getMethodInvokingJob());
//                }
//                jobDetail.setDescription(timer.getDescription());
//                jobDetail.setJobDataMap(paramsMap);
//                trigger = new CronTrigger(timer.getTriggerName(),
//                        timer.getGroup(),
//                        timer.getCronExpression());
//
//                scheduler.scheduleJob(jobDetail, trigger);
//            } else {
//                //Trigger已存在，那么更新相应的定时设置
//                trigger.setCronExpression(timer.getCronExpression());
//                scheduler.rescheduleJob(trigger.getName(), trigger.getGroup(),
//                        trigger);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//
//
//
//
//    /**
//     * 得到一个job的详细信息 jobDetail对象
//     * @param timerName
//     * @param timerGroup
//     * @return
//     */
//    public JobDetail getJobDetail(String timerName, String timerGroup) {
//        if(null == timerName || null == timerGroup ||
//                "".equals(timerName.trim()) || "".equals(timerGroup.trim())){
//            return null;
//        }
//        try {
//            return scheduler.getJobDetail(timerName, timerGroup);
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//    /**
//     * 得到一个job的Trigger
//     * @param timerName
//     * @param timerGroup
//     * @return
//     */
//    public Trigger getJobTrigger(String timerName, String timerGroup) {
//        if(null == timerName || null == timerGroup ||
//                "".equals(timerName.trim()) || "".equals(timerGroup.trim())){
//            return null;
//        }
//        try {
//            return scheduler.getTrigger("TriggerName_"+timerName,timerGroup);
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//    /**
//     * 得到一个job的Trigger
//     * @param timerName
//     * @param timerGroup
//     * @return
//     */
//    public Trigger getJobTrigger(TimerEntity timer) {
//        if(timer == null){
//            return null;
//        }
//        try {
//            return scheduler.getTrigger(timer.getTriggerName(), timer.getGroup());
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//    /**
//     * 停止一个定时器
//     * 这里我们采用pauseTriggerGroup 方式下面才能通过getPausedTriggerGroups().iterator()获取停止了的定时器
//     * @param timerTriggerGroup
//     * @return
//     */
//    public boolean pauseTimer(String timerTriggerGroup){
//        try {
//            scheduler.pauseTriggerGroup(timerTriggerGroup);
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//    /**
//     * 重新启动一个定时器(采用和上面 xxxTriggerGroup 同样方式 ，如不停止和重启不对应那么无效)
//     * @param timerTriggerGroup
//     * @return
//     */
//    public boolean resumeTimer(String timerTriggerGroup){
//        try {
//            scheduler.resumeTriggerGroup(timerTriggerGroup);
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//    /**
//     * 获取全部的定时器
//     * @return
//     */
//    public List<JobDetail> getAllToJobDetail(){
//        List<JobDetail> list = new ArrayList<JobDetail>();
//        try {
//
//            for(String jobGroup:scheduler.getJobGroupNames()){
//                for(String jobName:scheduler.getJobNames(jobGroup)){
//                    list.add(scheduler.getJobDetail(jobName, jobGroup));
//                }
//            }
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//    /**
//     * 获取全部的定时器，此方法在 getRuningTimer() 里面调用一下,其他地方无任何意义（没法区分状态）
//     * 请使用 下面的 getAll()
//     * @return
//     */
//    public List<TimerEntity> getAllToTimerEntity(){
//        List<TimerEntity> list = new ArrayList<TimerEntity>();
//        try {
//            TimerEntity timer = null;
//            JobDetail job = null;
//            for(String jobGroup:scheduler.getJobGroupNames()){
//                for(String jobName:scheduler.getJobNames(jobGroup)){
//                    job = scheduler.getJobDetail(jobName, jobGroup);
//                    timer = new TimerEntity();
//                    CronTrigger trigger = (CronTrigger)getJobTrigger(jobName, jobGroup);
//                    timer.setCronExpression(trigger.getCronExpression());
//                    timer.setDescription(job.getDescription());
//                    timer.setName(job.getName());
//                    //timer.setStatus() 这里默认全部为0 原生的trigger和jobDetail没有状态
//                    timer.setGroup(job.getGroup());
//                    list.add(timer);
//                }
//            }
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//    /**
//     * 获取全部
//     * @return
//     */
//    public List<TimerEntity> getAll(){
//        List<TimerEntity> list = getRuningTimer();
//        list.addAll(getPausedTimer());
//        return list;
//    }
//
//    /**
//     * 获取停止了的定时器
//     * @return
//     */
//    public List<TimerEntity> getPausedTimer(){
//        List<TimerEntity> list = new ArrayList<TimerEntity>();
//        //获取Paused 停止了的trigger（触发器）
//        JobDetail job = null;
//        TimerEntity timer = null;
//        try {
//            Iterator<String> i = scheduler.getPausedTriggerGroups().iterator();
//            while(i.hasNext()){
//                String jobGroup = i.next();
//                for(String jobName:scheduler.getJobNames(jobGroup)){
//                    job = scheduler.getJobDetail(jobName, jobGroup);
//                    timer = new TimerEntity();
//                    timer.setCronExpression(((CronTrigger)getJobTrigger(jobName, jobGroup)).getCronExpression());
//                    timer.setDescription(job.getDescription());
//                    timer.setName(job.getName());
//                    timer.setStatus(1); //设置状态为1  禁止
//                    timer.setGroup(job.getGroup());
//                    list.add(timer);
//
//                }
//            }
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//            return null;
//        }
//        return list;
//    }
//    /**
//     * 获取正在运行的
//     * @return
//     */
//    public List<TimerEntity> getRuningTimer(){
//        //先获取所有的定时器，然后再从里面删除掉被停止的，剩下的就是正在执行的
//        //这里重写 实体类的equals方法，里面不要加status
//        List<TimerEntity> list = getAllToTimerEntity();
//        list.removeAll(getPausedTimer());
//        return list;
//    }
//
//
//    /**
//     * 删除一个定时器，不能恢复
//     * @param jobName 名称
//     * @param jobGroup 分组
//     * @return 成功true 失败false
//     */
//    public boolean deleteTimer(String timerName,String timerGroup){
//        try {
//            scheduler.deleteJob(timerName, timerGroup);
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//    /**
//     * 删除一个定时器，不能恢复
//     * @param timer  自己封装的实体类
//     * @return
//     */
//    public boolean deleteTimer(TimerEntity timer){
//        try {
//            scheduler.deleteJob(timer.getName(), timer.getGroup());
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//
//}
