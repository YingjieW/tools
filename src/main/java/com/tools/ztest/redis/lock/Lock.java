package com.tools.ztest.redis.lock;

/**
 * Descripe: http://lixiaohui.iteye.com/blog/2320554
 *
 * @author yingjie.wang
 * @since 16/12/14 下午10:14
 */
public interface Lock {

    /**
     * 阻塞性地获取锁,超时自动返回,不响应中断
     * 单位: 秒
     *
     * @return
     */
    boolean tryLock();

    /**
     * 阻塞性地获取锁,超时自动返回,不响应中断
     * 单位: 秒
     *
     * @param key
     * @param expireTime
     * @return
     */
    boolean tryLock(String key, int expireTime);

    /**
     * 阻塞性地获取锁,超时自动返回,响应中断
     * 单位: 秒
     *
     * @param expireTime
     * @param key
     * @return
     * @throws InterruptedException
     */
    boolean tryLockInterruptibly(String key, int expireTime) throws InterruptedException;

    /**
     * 阻塞性地获取锁,超时自动返回,响应中断
     * 单位: 秒
     *
     * @return
     * @throws InterruptedException
     */
    boolean tryLockInterruptibly() throws InterruptedException;

    /**
     * 释放锁
     */
    void unlock();
}
