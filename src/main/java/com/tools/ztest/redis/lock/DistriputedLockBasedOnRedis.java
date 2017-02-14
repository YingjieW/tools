package com.tools.ztest.redis.lock;

import com.tools.util.PlatformUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.ConcurrentLinkedQueue;

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

    private static final String DEFAULT_VALUE = PlatformUtils.MACAddress() + "-" + PlatformUtils.JVMPid();

    private static final int JEDIS_MAX_TOTAL = 10;

//    private static final ConcurrentHashMap<Jedis, Boolean> JEDIS_MAP = new ConcurrentHashMap<Jedis, Boolean>(JEDIS_MAX_TOTAL);

    private static final ConcurrentLinkedQueue jedisQueue = new ConcurrentLinkedQueue();

    private static JedisPool jedisPool;

    static {
        init();
    }

    public static void init() {
        System.out.println("DEFAULT_VALUE: " + DEFAULT_VALUE);
        JedisPoolConfig config = new JedisPoolConfig();
        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        config.setMaxIdle(20);
        config.setMaxTotal(100);
        //最小空闲连接数, 默认0
        config.setMinIdle(0);
        config.setMaxWaitMillis(-1);
        // 当pool中jedis示例耗尽后,是否block线程
        config.setBlockWhenExhausted(false);
        config.setTestOnBorrow(true);
        //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        config.setMinEvictableIdleTimeMillis(1);
        jedisPool = new JedisPool(config, JEDIS_HOST, JEDIS_PORT);

        for (int i = 0; i < JEDIS_MAX_TOTAL; i++) {
            Jedis jedis = jedisPool.getResource();
            jedisQueue.add(jedis);
        }
    }

    private boolean checkInit() {
        return jedisPool != null;
    }

    public DistriputedLockBasedOnRedis() {}

    public DistriputedLockBasedOnRedis(String key, int expireTime) {
        super(key, expireTime);
    }

    private Jedis getThreadLocalJedis() {
        Jedis jedis = jedisThreadLocal.get();
        if (jedis == null) {
            jedis = getJedis();
            jedisThreadLocal.set(jedis);
        }
        return jedis;
    }

    private Jedis getJedis() {
        if (jedisQueue.size() == 0) {
            throw new RuntimeException("no available jedis.");
        }
        return (Jedis) jedisQueue.poll();
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

        Jedis jedis = getThreadLocalJedis();

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
        this.getThreadLocalJedis().del(key);
        jedisQueue.add(jedisThreadLocal.get());
        jedisThreadLocal.remove();
    }

//    @Override
//    protected boolean isHeldByCurrentThread() {
//        return false;
//    }
}
