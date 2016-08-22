package com.tools.ztest.lock;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Descripe: http://www.importnew.com/20865.html#comment-505775
 *
 * @author yingjie.wang
 * @since 16/8/22 下午6:59
 */
public class Cache {

    public static ReadWriteLock lock = new ReentrantReadWriteLock();

    public static Map<String, Object> cacheMap = new ConcurrentHashMap<String, Object>();

    public static Object get(String key) {
        // 上读锁
        lock.readLock().lock();
        Object result = null;
        try {
            result = cacheMap.get(key);
            System.out.println("Get value from cacheMap. key:[" + key + "], value:[" + result + "].");
            if(result == null) {
                lock.readLock().unlock();
                // 加写锁
                lock.writeLock().lock();
                // 获取缓存中最新内容
                result = cacheMap.get(key);
                System.out.println("Using writeLock, key: " + key + "], value:[" + result + "].");
                if(result == null) {
                    try {
                        // 用该语句替代对数据库的访问
                        result = "getDataFromDB.key[" + key + "]";
                        cacheMap.put(key, result);
                    } finally {
                        lock.writeLock().unlock();
                        System.out.println("AAA Release writeLock, key: " + key);
                    }
                } else {
                    lock.writeLock().unlock();
                    System.out.println("BBB Release writeLock, key: " + key);
                }
                lock.readLock().lock();
            }
        } finally {
            lock.readLock().unlock();
            System.out.println("Release readLock, key: " + key);
        }
        return result;
    }

    public void writeToDB(String key, Object value) {
        lock.writeLock().lock();
        try {
            // write to database
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static void main(String[] args) throws Exception {
        for(int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Random random = new Random();
//                    Object value = Cache.get("Thread-" + random.nextInt(100));
                    Object value = Cache.get("Thread-0");
                    System.out.println("* value - " + value);
                }
            }).start();
        }
    }
}
