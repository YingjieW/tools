package com.tools.ztest.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/10/2 下午2:28
 */
public class CachedData {
    Object data;
    volatile boolean cacheValid;
    final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    void processCachedData() {
        rwl.readLock().lock();  // 1. 上读锁
        if (!cacheValid) {      // 2. 验证cacheValid
            // Must release read lock before acquiring write lock
            rwl.readLock().unlock(); // 3. 解除读锁
            rwl.writeLock().lock(); // 4. 上写锁
            try {
                // Recheck state because another thread might have acquired write lock and changed state before we did.
                if (!cacheValid) {    // 5. 验证cacheValid
                    data = getData();
                    cacheValid = true;
                }
                // Downgrade by acquiring read lock before releasing write lock
                rwl.readLock().lock(); // 6. 上读锁
            } finally {
                rwl.writeLock().unlock(); // Unlock write, still hold read // 7. 解除写锁
            }
        }
        try {
            use(data);
        } finally {
            rwl.readLock().unlock();//8. 解除读锁
        }
    }

    private Object getData() {
        return null;
    }

    private void use(Object data) {}
}
