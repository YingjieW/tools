package com.tools.quartz;

import com.alibaba.fastjson.JSON;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/8/23 上午10:54
 */
public class TimerEntity01 {

    /**
     * 分组
     */
    private String group;

    /**
     * 名称
     */
    private String name;

    /**
     * cron表达式
     */
    private String cronExpression;

    /**
     * 定时入参
     */
    private Object[] arguments;

    /**
     * 是否允许并发
     */
    private boolean canConcurrent;

    /**
     *
     */
    private Object targetObject;

    /**
     *
     */
    private String targetMethod;

    /**
     * 状态: 废弃、暂停时直接从scheduler中删除
     */
    private String status;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        TimerEntity01 other = (TimerEntity01) obj;
        if (group == null) {
            if (other.group != null) {
                return false;
            }
        } else if (!group.equals(other.group)) {
            return false;
        }

        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }

        if (cronExpression == null) {
            if (other.cronExpression != null) {
                return false;
            }
        } else if (!cronExpression.equals(other.cronExpression)) {
            return false;
        }

        if (!isArgumentsEqual(arguments, other.arguments)) {
            return false;
        }

        if (targetObject == null) {
            if (other.targetObject != null) {
                return false;
            }
        } else if (!targetObject.equals(other.targetObject)) {
            return false;
        }

        if (targetMethod == null) {
            if (other.targetMethod != null) {
                return  false;
            }
        } else if (!targetMethod.equals(other.targetMethod)) {
            return false;
        }

        // skip 'concurrent'

        return true;
    }


    private static boolean isArgumentsEqual(Object[] arguments0, Object[] arguments1) {
        if (arguments0 == null) {
            return arguments1 == null;
        }

        if (arguments1 == null) {
            return  arguments0 == null;
        }

        if (arguments0.length != arguments1.length) {
            return false;
        }

        for (int i = 0; i < arguments0.length; i++) {
            if (!arguments0[i].equals(arguments1[i])) {
                return false;
            }
        }

        return true;
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

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public boolean isCanConcurrent() {
        return canConcurrent;
    }

    public void setCanConcurrent(boolean canConcurrent) {
        this.canConcurrent = canConcurrent;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }

    public String getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }
}
