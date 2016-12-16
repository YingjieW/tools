package com.tools.ztest.redis.lock;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/12/16 下午2:51
 */
public class IDGeneratorTest {
    public static void main(String[] args) throws Exception {
        DistriputedLockBasedOnRedis lock = new DistriputedLockBasedOnRedis("idGenerator", 1);
        Thread[] threads = new Thread[500];

        for (int i = 0; i < 500; i++) {
            IDGeneratorThread idGeneratorThread = new IDGeneratorThread(new IDGenerator(lock), ("Thread" + i));
            threads[i] = idGeneratorThread;
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }
}
