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
     * 单纯地从软件的使用上讲,redis是线程安全的,(比如线程不安全的HashMap,在多线程下可能会造成死循环)。
     * 但是, redis如果使用不规范, 也是会造成数据不一致的。
     * 数据不一致与线程安全是两个概念。
     *
     * Redis is a single-threaded server. It is not designed to benefit from multiple CPU cores.
     * People are supposed to launch several Redis instances to scale out on several cores if needed.
     * It is not really fair to compare one single Redis instance to a multi-threaded data store.
     * So as every command gets queued in a single thread you should be ok as there will never be
     * two commands executing in parallel.
     * 翻译：redis是单线程服务器。它的设计并不能从多核cpu中受益。人们可以安装多个redis实例来提高多核cpu的利用率。
     * 拿redis更多线程存储服务器做对比是不公平的。每个请求被缓存在一个线程中，一个时间只能有一个线程在处理请求。
     *
     * redis为什么是单线程?
     * redis基本是内存操作，在IO和网络操作的时候，多线程的程序可以很好的利用CPU时间。
     * 那在基本是内存操作的情况下，单线程程序应该可以充分利用CPU时间了。
     */

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
        System.out.println("===> operateKey()");
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
        System.out.println();
    }

    public void operateString() throws Exception {
        System.out.println("===> operateString()");
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
        System.out.println("===> update key003: " + jedis.set("key003", "value003_new"));

        System.out.println("===> UPDATE......");
        System.out.println("===> update key001: " + jedis.set("key001", "value001_update"));
        System.out.println("===> key001: " + jedis.get("key001"));
        System.out.println("===> append to key002: " + jedis.append("key002", "_append_test"));
        System.out.println("===> key002: " + jedis.get("key002"));

        System.out.println("===> MULTIPLE OPERATION......");
        System.out.println("===> add multiply: " + jedis.mset("key201","value201", "key202","value202","key203","value203","key204","value204"));
        System.out.println("===> get all: " + jedis.mget("key201","key202","key203","key204"));

        // jedis具备的功能shardedJedis也同样具备
        System.out.println("======> shardedJedis:");
        jedis.flushDB();
        /**
         * SETNX works exactly like set(String, String) with the only difference that if the
         * key already exists no operation is performed.
         * SETNX actually means "SET if Not eXists".
         */
        System.out.println("===> add key301: " + shardedJedis.setnx("key301", "value301"));
        System.out.println("===> add key302: " + shardedJedis.setnx("key302", "value302"));
        System.out.println("===> update key302: " + shardedJedis.setnx("key302", "value302_new"));
        System.out.println("===> get key301: " + shardedJedis.get("key301"));
        System.out.println("===> get key302: " + shardedJedis.get("key302"));
        System.out.println("===> add key303: " + shardedJedis.setnx("key303","value303"));
        System.out.println("===> set expire time of key303: " + shardedJedis.expire("key303", 2));
        System.out.println("===> get key303: " + shardedJedis.get("key303"));
        Thread.sleep(3000);
        System.out.println("===> get key303 again: " + shardedJedis.get("key303"));
        System.out.println("===> getSet key302: " + shardedJedis.getSet("key302", "key302_getSet"));
        System.out.println("===> get key302: " + shardedJedis.get("key302"));
        System.out.println("===> get substring of key302: " + shardedJedis.getrange("key302", 0, 3));
        System.out.println("===> ");
        System.out.println();
    }

    public void operateList() throws Exception {

        System.out.println("===> operateList()");
        System.out.println("===> flush: " + jedis.flushDB());

        System.out.println("===> ADD.....");
        System.out.println("===> lpush: " + jedis.lpush("stringList", "str01"));
        jedis.lpush("stringList", "str02");
        jedis.lpush("stringList", "str03");
        jedis.lpush("stringList", "str04");
        jedis.lpush("stringList", "str05");
        jedis.lpush("stringList", "str03");
        jedis.lpush("stringList", "str03");
        System.out.println("===> stringList: " + jedis.lrange("stringList", 0, -1));

        System.out.println("===> DEL.....");
        System.out.println("===> del from stringList: " + jedis.lrem("stringList", 1, "str03"));
        System.out.println("===> stringList: " + jedis.lrange("stringList", 0, -1));
        System.out.println("===> del from stringList: " + jedis.lrem("stringList", 2, "str03"));
        System.out.println("===> stringList: " + jedis.lrange("stringList", 0, -1));
        System.out.println("===> trim_0_3: " + jedis.ltrim("stringList", 0, 2));
        System.out.println("===> stringList: " + jedis.lrange("stringList", 0, -1));
        System.out.println("===> lpop: " + jedis.lpop("stringList"));
        System.out.println("===> stringList: " + jedis.lrange("stringList", 0, -1));

        System.out.println("===> UPDATE.....");
        System.out.println("===> update: " + jedis.lset("stringList", 0, "hello list!"));
        System.out.println("===> stringList: " + jedis.lrange("stringList", 0, -1));

        System.out.println("===> length of stringList: " + jedis.llen("stringList"));
        System.out.println("===>");
        System.out.println();
    }

    public void operateSet() throws Exception {

        System.out.println("===> operateSet()");
        System.out.println("===> flush: " + jedis.flushDB());

        System.out.println("===> ADD.....");
        System.out.println("===> add element001: " + jedis.sadd("sets", "element001"));
        System.out.println("===> add element002: " + jedis.sadd("sets", "element002"));
        System.out.println("===> add element003: " + jedis.sadd("sets", "element003"));
        System.out.println("===> add element004: " + jedis.sadd("sets", "element004"));
        System.out.println("===> sets: " + jedis.smembers("sets"));

        System.out.println("===> DEL.....");
        System.out.println("===> rem element003: " + jedis.srem("sets", "element003"));
        System.out.println("===> sets: " + jedis.smembers("sets"));

        System.out.println("===> SEARCH.....");
        System.out.println("===> element001: " + jedis.sismember("sets", "element001"));
        System.out.println("===> element003: " + jedis.sismember("sets", "element003"));

        System.out.println("===> GET ALL.....");
        Set<String> set = jedis.smembers("sets");
        for (String str : set) {
            System.out.println("---> " + str);
        }

        System.out.println("===> SET OPERATION...");
        jedis.sadd("sets1", "element001");
        jedis.sadd("sets1", "element002");
        jedis.sadd("sets1", "element003");
        jedis.sadd("sets2", "element002");
        jedis.sadd("sets2", "element003");
        jedis.sadd("sets2", "element004");
        System.out.println("===> sets1: " + jedis.smembers("sets1"));
        System.out.println("===> sets2: " + jedis.smembers("sets2"));
        System.out.println("===> intersection of sets1 and sets2: " + jedis.sinter("sets1", "sets2"));
        System.out.println("===> union of sets1 and sets2: " + jedis.sunion("sets1", "sets2"));
        System.out.println("===> diff of sets1 and sets2: " + jedis.sdiff("sets1", "sets2"));
        System.out.println("===> diff of sets2 and sets1: " + jedis.sdiff("sets2", "sets1"));

        System.out.println("===>");
        System.out.println();
    }

    public void operateSortedSet() throws Exception {

        System.out.println("===> operateSortedSet()");
        System.out.println("===> flush: " + jedis.flushDB());

        System.out.println("===> ADD.....");
        System.out.println("===> zadd element001: " + jedis.zadd("zset", 7, "element001"));
        System.out.println("===> zadd element002: " + jedis.zadd("zset", 8, "element002"));
        System.out.println("===> zadd element003: " + jedis.zadd("zset", 2, "element003"));
        System.out.println("===> zadd element004: " + jedis.zadd("zset", 3, "element004"));
        System.out.println("===> zset: " + jedis.zrange("zset", 0, -1));
        System.out.println("===> card zset: " + jedis.zcard("zset"));
        System.out.println("===> count between 1 and 5: " + jedis.zcount("zset", 1, 5));
        System.out.println("===> score of element004: " + jedis.zscore("zset", "element004"));
        System.out.println("===> range between 1 and 2: " + jedis.zrange("zset", 1, 2));

        System.out.println("===>");
        System.out.println();
    }

    public void operateHash() throws Exception {

        System.out.println("===> operateHash()");
        System.out.println("===> flush: " + jedis.flushDB());

        System.out.println("===> ADD.....");
        System.out.println("===> add key001_value001: " + jedis.hset("hashs", "key001", "value001"));
        System.out.println("===> add key002_value002: " + jedis.hset("hashs", "key002", "value002"));
        System.out.println("===> add key003_value003: " + jedis.hset("hashs", "key003", "value003"));
        //System.out.println("===> add key004_value004: " + jedis.hset("hashs", "key004", "value004"));
        System.out.println("===> add key004_1004: " + jedis.hincrBy("hashs", "key004", 1004));
        System.out.println("===> hashs: " + jedis.hvals("hashs"));

        List<String> list = jedis.hvals("hashs");
        for (String str : list) {
            System.out.println("---> " + str);
        }

        System.out.println("===> DEL.....");
        System.out.println("===> del key002: " + jedis.hdel("hashs", "key002"));
        System.out.println("===> hashs: " + jedis.hvals("hashs"));

        System.out.println("===> UPDATE.....");
        System.out.println("===> update key001: " + jedis.hset("hashs", "key001", "value001_new"));
        System.out.println("===> update key004: " + jedis.hincrBy("hashs", "key004", 96));
        System.out.println("===> hashs: " + jedis.hvals("hashs"));

        System.out.println("===> SEARCH.....");
        System.out.println("===> exists key003: " + jedis.hexists("hashs", "key003"));
        System.out.println("===> get key004: " + jedis.hget("hashs", "key004"));
        System.out.println("===> mget key001,key002,key003,key004,key999: " + jedis.hmget("hashs", "key001", "key002", "key003", "key004", "key999"));
        System.out.println("===> keys: " + jedis.hkeys("hashs"));
        System.out.println("===> values: " + jedis.hvals("hashs"));

        System.out.println("===>");
        System.out.println();
    }

    public static void main(String[] args) throws Throwable {
        RedisClient redisClient = new RedisClient();
        redisClient.operateSortedSet();
    }
}
