package com.tools.ztest.redis;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/12/12 下午4:10
 */
public class RedisIDGeneratorTest {
    public static void main(String[] args) throws Exception {
        RedisLock redisLock = new RedisLock("idGenerator", 5);
        RedisIDGenerator redisIDGenerator1 = new RedisIDGenerator(redisLock);
        RedisIDGenerator redisIDGenerator2 = new RedisIDGenerator(redisLock);
        RedisIDGenerator redisIDGenerator3 = new RedisIDGenerator(redisLock);
        RedisIDGeneratorThread thread1 = new RedisIDGeneratorThread(redisIDGenerator1, "Thread001");
        RedisIDGeneratorThread thread2 = new RedisIDGeneratorThread(redisIDGenerator2, "Thread002");
        RedisIDGeneratorThread thread3 = new RedisIDGeneratorThread(redisIDGenerator3, "Thread003");
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
