package com.tools.ztest.redis.lock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.tools.util.PlatformUtils;

import java.io.Serializable;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/12/14 下午10:48
 */
public class LockInfo implements Serializable {

    private static final long serialVersionUID = 1007118722109416652L;

    private String key;

    private int expireTime;

    private String macAddress;

    private long jvmProcessID;

    private long currentThreadID;

    private int count;

    private static final transient String LOCAL_MAC_ADDRESS = PlatformUtils.MACAddress();

    private static final transient int CURRENT_JVM_PROCESS_ID = PlatformUtils.JVMPid();

    private static final SimplePropertyPreFilter FILTER = new SimplePropertyPreFilter();

    static {
        FILTER.getExcludes().add("currentThread");
    }


    public LockInfo() {}

    public LockInfo(String key, int expireTime) {
        this.key = key;
        this.expireTime = expireTime;
        this.macAddress = LOCAL_MAC_ADDRESS;
        this.jvmProcessID = CURRENT_JVM_PROCESS_ID;
        this.currentThreadID = Thread.currentThread().getId();
        this.count = 1;
    }

    public LockInfo countIncrease() {
        if (count == Integer.MAX_VALUE) {
            throw new RuntimeException("Maximum lock count exceeded!");
        }
        count++;
        return this;
    }

    public LockInfo countDecrease() {
        if (count == 0) {
            return this;
        }
        count--;
        return this;
    }

    public boolean isCurrentThread() {
        return macAddress.equalsIgnoreCase(LOCAL_MAC_ADDRESS) &&
                jvmProcessID == CURRENT_JVM_PROCESS_ID && Thread.currentThread().getId() == currentThreadID;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, FILTER);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LockInfo lockInfo = (LockInfo) o;

        if (expireTime != lockInfo.getExpireTime()) {
            return false;
        }
        if (jvmProcessID != lockInfo.getJvmProcessID()) {
            return false;
        }
        if (currentThreadID != lockInfo.getCurrentThreadID()) {
            return false;
        }
        if (count != lockInfo.getCount()) {
            return false;
        }
        return macAddress != null ? macAddress.equals(lockInfo.getMacAddress()) : lockInfo.getMacAddress() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (expireTime ^ (expireTime >>> 32));
        result = 31 * result + (macAddress != null ? macAddress.hashCode() : 0);
        result = 31 * result + (int) (jvmProcessID ^ (jvmProcessID >>> 32));
        result = 31 * result + (int) (currentThreadID ^ (currentThreadID >>> 32));
        result = 31 * result + count;
        return result;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public long getJvmProcessID() {
        return jvmProcessID;
    }

    public void setJvmProcessID(long jvmProcessID) {
        this.jvmProcessID = jvmProcessID;
    }

    public long getCurrentThreadID() {
        return currentThreadID;
    }

    public void setCurrentThreadID(long currentThreadID) {
        this.currentThreadID = currentThreadID;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static void main(String[] args) throws Exception {
        LockInfo lockInfo = new LockInfo("k003", 333);
        System.out.println(lockInfo.toString());
    }
}
