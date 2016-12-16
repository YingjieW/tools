package com.tools.ztest.redis.lock;

import org.apache.commons.lang3.StringUtils;

/**
 * Descripe: http://lixiaohui.iteye.com/blog/2320554
 *
 * @author yingjie.wang
 * @since 16/12/14 下午10:29
 */
public abstract class AbstractLock implements Lock {

    protected String key;

    protected int expireTime;

    public AbstractLock() {}

    public AbstractLock(String key, int expireTime) {
        this.key = key;
        this.expireTime = expireTime;
    }

    /**
     * 1.同一个jvm的多个线程使用不同的锁对象其实也是可以的, 这种情况下不需要保证可见性
     * 2.同一个jvm的多个线程使用同一个锁对象, 那可见性就必须要保证了.
     */
    protected volatile boolean locked;

    /**
     * 当前线程是否拥有锁
     *
     * @return
     */
//    protected abstract boolean isHeldByCurrentThread();

    /**
     * 阻塞式获取锁的实现
     * 单位: 秒
     *
     * @param key
     * @param expireTime
     * @param interrupt 是否响应中断
     * @return
     * @throws InterruptedException
     */
    protected abstract boolean lock(String key, int expireTime, boolean interrupt) throws InterruptedException;

    /**
     * 尝试获取锁,若获取不到,则立即返回,不阻塞
     * 单位: 秒
     *
     * @return
     */
    @Override
    public boolean tryLock() {
        return tryLock(this.key, this.expireTime);
    }

    /**
     * 尝试获取锁,若获取不到,则立即返回,不阻塞
     * 单位: 秒
     *
     * @param expireTime
     * @param key
     * @return
     */
    @Override
    public boolean tryLock(String key, int expireTime) {
        if (StringUtils.isBlank(key) || expireTime <= 0) {
            return false;
        }
        try {
            return lock(key, expireTime, false);
        } catch (Throwable t) {
            // do nothing...
        }
        return false;
    }

    /**
     * 阻塞性地获取锁,超时自动返回,响应中断
     * 单位: 秒
     *
     * @return
     * @throws InterruptedException
     */
    @Override
    public boolean tryLockInterruptibly() throws InterruptedException {
        return tryLockInterruptibly(this.key, this.expireTime);
    }

    /**
     * 阻塞性地获取锁,超时自动返回,响应中断
     * 单位: 秒
     *
     * @param expireTime
     * @param key
     * @return
     * @throws InterruptedException
     */
    @Override
    public boolean tryLockInterruptibly(String key, int expireTime) throws InterruptedException {
        if (StringUtils.isBlank(key) || expireTime <= 0) {
            return false;
        }
        return lock(key, expireTime, true);
    }
}
