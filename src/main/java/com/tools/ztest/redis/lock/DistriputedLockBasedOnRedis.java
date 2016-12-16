package com.tools.ztest.redis.lock;

import com.tools.utils.PlatformUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/12/15 下午10:09
 */
public class DistriputedLockBasedOnRedis extends AbstractLock {

    private static final Logger logger = LoggerFactory.getLogger(DistriputedLockBasedOnRedis.class);

    private static final String JEDIS_HOST = "127.0.0.1";

    private static final int JEDIS_PORT = 6379;

    private static final ThreadLocal<Jedis> jedisThreadLocal = new ThreadLocal<Jedis>();

    private static final String DEFAULT_VALUE = PlatformUtils.MACAddress() + " - " + PlatformUtils.JVMPid();

    private static JedisPool jedisPool;

    static {
        init();
    }

    public static void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(5);
        config.setMaxTotal(5);
        config.setMaxWaitMillis(60*1000);
        config.setTestOnBorrow(false);
        jedisPool = new JedisPool(config, JEDIS_HOST, JEDIS_PORT);
    }

    private boolean checkInit() {
        return jedisPool != null;
    }

    public DistriputedLockBasedOnRedis() {}

    public DistriputedLockBasedOnRedis(String key, int expireTime) {
        super(key, expireTime);
    }

    private Jedis getJedis() {
        Jedis jedis = jedisThreadLocal.get();
        if (jedis == null) {
            jedis = jedisPool.getResource();
            jedisThreadLocal.set(jedis);
        }
        return jedis;
    }

    /**
     * 没有实现ReentrantLock, 不过在这里简要介绍一下ReentrantLock的实现思路:
     * 0.原理:setnx(key, lockInfo.toString()),在客户端校验锁是否超时
     * 1.setnx()
     * 2.如果结果为1,获取锁,并返回
     * 3.如果结果为0,锁已经被占用,下面判断是否当前线程占用锁
     * 4.获取lockInfo
     * 5.若lockInfo==null,跳过本次循环
     * 6.若lockInfo!=null,判断是否超时
     * 7.如果超时,跳过本次循环
     * 8.如果未超时,更新lockInfo→newLockInfo,然后set(key,newLockInfo.toString())
     * 但。。。。。。事实证明这个思路是错误的 /(ㄒoㄒ)/~~, 因为8.不能保证原子性。
     *
     * @param key
     * @param expireTime
     * @param interrupt 是否响应中断
     * @return
     * @throws InterruptedException
     */
    @Override
    protected boolean lock(String key, int expireTime, boolean interrupt) throws InterruptedException {
        if (interrupt) {
            checkInterruption();
        }

        Jedis jedis = getJedis();

        if (jedis.setnx(key, DEFAULT_VALUE) == 1) {
            jedis.expire(key, expireTime);
            return true;
        }

        return false;
    }

    private void checkInterruption() throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException("Current thread is interrupted.");
        }
    }

    @Override
    public void unlock() {
        Jedis jedis = getJedis();
        jedis.del(key);
    }

//    @Override
//    protected boolean isHeldByCurrentThread() {
//        return false;
//    }
}
