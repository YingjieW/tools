package com.tools.ztest.redis.lock;

import java.util.HashSet;
import java.util.Set;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/12/16 下午2:36
 */
public class IDGenerator {

    private static Set<Long> generatedIds = new HashSet<Long>();

    private static long id = 0l;

    private final DistriputedLockBasedOnRedis lock;

    public IDGenerator(DistriputedLockBasedOnRedis lock) {
        this.lock = lock;
    }

    public boolean idExists(long id) {
        return generatedIds.contains(id);
    }

    public void add(long id) {
        generatedIds.add(id);
    }

    public long getAndIncrement() {
        if (lock.tryLock()) {
            try {
                System.out.println("===> [" + Thread.currentThread().getName()  + "] get lock. id = " + id);
                return id++;
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        return -1;
    }
}
