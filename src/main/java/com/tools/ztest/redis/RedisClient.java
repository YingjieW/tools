package com.tools.ztest.redis;

import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/12/9 下午2:45
 */
public class RedisClient {

    /**
     * Jedis作为推荐的java语言redis客户端
     * ShardedJedis是基于一致性哈希算法实现的分布式Redis集群客户端；
     */

    // 非切片额客户端连接
    private Jedis jedis;
    // 非切片连接池
    private JedisPool jedisPool;
    // 切片额客户端连接
    private ShardedJedis shardedJedis;
    // 切片连接池
    private ShardedJedisPool shardedJedisPool;

    public RedisClient() {
        initialPool();
        initialShardedPool();
        shardedJedis = shardedJedisPool.getResource();
        jedis = jedisPool.getResource();
    }

    /**
     * 初始化非切片池
     */
    private void initialPool() {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(5);
        config.setTestOnBorrow(false);
        jedisPool = new JedisPool(config,"127.0.0.1",6379);
    }

    /**
     * 初始化切片池
     */
    private void initialShardedPool() {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(5);
        config.setTestOnBorrow(false);
        // slave链接
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        shards.add(new JedisShardInfo("127.0.0.1", 6379, "master"));
        // 构造池
        shardedJedisPool = new ShardedJedisPool(config, shards);
    }

    public void operateKey() throws Exception {
        System.out.println("===> ");
        System.out.println("===> flush: " + jedis.flushDB());
        System.out.println("===> key999: " + shardedJedis.exists("key999"));
        System.out.println("===> set(key001,value001): " + shardedJedis.set("key001", "value001"));
        System.out.println("===> key001: " + shardedJedis.exists("key001"));
        System.out.println("===> key001: " + shardedJedis.get("key001"));
        System.out.println("===> set(key002,value002): " + shardedJedis.set("key002", "value002"));
        System.out.println("===> GET ALL: ");
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(" --> " + key + " : " + jedis.get(key));
        }
        System.out.println("===> del key002: " + jedis.del("key002"));
        System.out.println("===> key002 eixsts or not: " + jedis.exists("key002"));
        System.out.println("===> set expire: " + jedis.expire("key001", 3));
        Thread.sleep(1000);
        System.out.println("===> time remaining: " + jedis.ttl("key001"));
        System.out.println("===> persist key001: " + jedis.persist("key001"));
        System.out.println("===> time remaining: " + jedis.ttl("key001"));
        System.out.println("===> type of value: " + jedis.type("key001"));
        System.out.println("===> ");
    }

    public void operateString() throws Exception {
        System.out.println("===> ");
        System.out.println("===> flush: " + jedis.flushDB());

        System.out.println("===> ADD......");
        jedis.set("key001", "value001");
        jedis.set("key002", "value002");
        jedis.set("key003", "value003");
        System.out.println("===> key001: " + jedis.get("key001"));
        System.out.println("===> key002: " + jedis.get("key002"));
        System.out.println("===> key003: " + jedis.get("key003"));

        System.out.println("===> DEL......");
        System.out.println("===> del key003: " + jedis.del("key003"));
        System.out.println("===> key003: " + jedis.get("key003"));

        System.out.println("===> UPDATE......");
        System.out.println("===> update key001: " + jedis.set("key001", "value001_update"));
        System.out.println("===> key001: " + jedis.get("key001"));
        System.out.println("===> append to key002: " + jedis.append("key002", "_append_test"));
        System.out.println("===> key002: " + jedis.get("key002"));

        System.out.println("===> MULTIPLE OPERATION......");
        System.out.println("===> add multiply: " + jedis.mset("key201","value201", "key202","value202","key203","value203","key204","value204"));
        System.out.println("===> get all: " + jedis.mget("key201","key202","key203","key204"));
        System.out.println("===> ");
    }

    public static void main(String[] args) throws Throwable {
        RedisClient redisClient = new RedisClient();
        redisClient.operateString();
    }
}
