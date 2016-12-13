package com.tools.ztest.redis;

import java.util.HashSet;
import java.util.Set;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/12/12 下午4:00
 */
public class RedisIDGenerator {

    private static Set<String> generatedIds = new HashSet<String>();

    private static long id = 0l;

    private final RedisLock lock;

    public RedisIDGenerator(RedisLock lock) {
        this.lock = lock;
    }

    public boolean idExists(String id) {
        return generatedIds.contains(id);
    }

    public void add(String id) {
        generatedIds.add(id);
    }

    public String getAndIncrement() {
        if (lock.tryLock()) {
            try {
                System.out.println("===> [" + Thread.currentThread().getName()  + "] get lock. id = " + id);
                return String.valueOf(id++);
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        return null;
    }
}
