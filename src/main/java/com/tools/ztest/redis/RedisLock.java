package com.tools.ztest.redis;

import redis.clients.jedis.*;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/12/12 下午2:25
 */
public class RedisLock {



    // 非切片额客户端连接
    private static Jedis defaultJedis;
    // 非切片连接池
    private static JedisPool jedisPool;
    // 本地连接池
    private ThreadLocal<Jedis> jedisThreadLocal = new ThreadLocal<Jedis>();

    static {
        init();
    }

    public static void init() {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(10);
        config.setMaxTotal(10);
        config.setMaxWaitMillis(100*1000);
        config.setTestOnBorrow(false);
        jedisPool = new JedisPool(config,"127.0.0.1",6379);
        defaultJedis = jedisPool.getResource();
        defaultJedis.flushDB();
    }

    private boolean checkInit() {
        return jedisPool != null;
    }

    private Jedis getJedis() {
//        if (defaultJedis != null) {
//            System.out.println("---> " + defaultJedis);
//            return defaultJedis;
//        }
        Jedis jedis = jedisThreadLocal.get();
        if (jedis == null) {
            jedis = jedisPool.getResource();
            jedisThreadLocal.set(jedis);
        }
        return jedis;
    }

    // key
    private String key;

    // value
    private String value;

    // 单位: 秒
    private int expireTime;

    public RedisLock(String key, int expireTime) {
        if (!checkInit()) {
            System.out.println("------ init jedis.....");
            this.init();
        }
        this.key = key;
        this.expireTime = expireTime;
    }

    public boolean tryLock() {
        String value = String.valueOf(System.currentTimeMillis());
        long result = this.getJedis().setnx(key, value);
        if (result == 1) {
            this.value = value;
            this.getJedis().expire(key, expireTime);
            return true;
        }
        return false;
    }

    public void unlock() {
        this.getJedis().del(key);
    }
}
