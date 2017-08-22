package com.tools.quartz;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/8/21 下午4:22
 */
public class TimerEntity {
    private String name; //名称
    private String group;//分组
    private String Description; //说明
    private String cronExpression; // 定时任务运行时间表达式
    private int status; // 任务的状态，0：启用；1：禁用
    private Class<?> statefulMethodInvokingJob;//同步的执行类，需要从StatefulMethodInvokingJob继承
    private Class<?> methodInvokingJob;//异步的执行类，需要从MethodInvokingJob继承
    public String getTriggerName(){
        return "TriggerName_"+this.getName();
    }
    /**
     * 这里重写equals方法，注意不要加入status字段的比较，等下在查询的时候有用
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TimerEntity other = (TimerEntity) obj;
        if (Description == null) {
            if (other.Description != null)
                return false;
        } else if (!Description.equals(other.Description))
            return false;
        if (cronExpression == null) {
            if (other.cronExpression != null)
                return false;
        } else if (!cronExpression.equals(other.cronExpression))
            return false;
        if (group == null) {
            if (other.group != null)
                return false;
        } else if (!group.equals(other.group))
            return false;
        if (methodInvokingJob == null) {
            if (other.methodInvokingJob != null)
                return false;
        } else if (!methodInvokingJob.equals(other.methodInvokingJob))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (statefulMethodInvokingJob == null) {
            if (other.statefulMethodInvokingJob != null)
                return false;
        } else if (!statefulMethodInvokingJob
                .equals(other.statefulMethodInvokingJob))
            return false;
        return true;
    }
    public String getCronExpression() {
        return cronExpression;
    }
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGroup() {
        return group;
    }
    public void setGroup(String group) {
        this.group = group;
    }
    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        Description = description;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public Class<?> getStatefulMethodInvokingJob() {
        return statefulMethodInvokingJob;
    }
    public void setStatefulMethodInvokingJob(Class<?> statefulMethodInvokingJob) {
        this.statefulMethodInvokingJob = statefulMethodInvokingJob;
    }
    public Class<?> getMethodInvokingJob() {
        return methodInvokingJob;
    }
    public void setMethodInvokingJob(Class<?> methodInvokingJob) {
        this.methodInvokingJob = methodInvokingJob;
    }
}
