/** 
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay) 
 */
package com.tools.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.util.Pool;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class RedisClientUtils {
	private static final Log LOG = LogFactory.getLog(RedisClientUtils.class);
	private static Pool<Jedis> pool;
	private static boolean newVersion = false;

	static {
		init();
	}

	/**
	 * 获取资源
	 * 
	 * @return
	 */
	public static Jedis getResource() {
		if (pool == null) {
			throw new RuntimeException("Redis Client not init!");
		}
		return pool.getResource();
	}

	/**
	 * 关闭资源
	 * 
	 * @param jedis
	 */
	public static void closeResource(Jedis jedis) {
		if (jedis != null) {
			if (jedis.isConnected()) {
				pool.returnResource(jedis);
			} else {
				pool.returnBrokenResource(jedis);
			}
		}
	}

	/**
	 * 从目录查找
	 * 
	 * @param path
	 * @return
	 */
	private static Properties getPropByClassPath(String path) {
		String baseDir = "";
		if (path != null && path.length() > 0 && !path.endsWith("/")) {
			baseDir =path+ "/";
		}
		Properties prop = null;
		try {
			InputStream is = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(baseDir + "redis-conf.properties");
			prop = new Properties();
			prop.load(is);
		} catch (Exception e) {
			return null;
		}
		return prop;
	}

	/**
	 * 初始化
	 */
	public synchronized static void init() {
		try {
			Properties prop = getPropByClassPath("runtimecfg");
			if (null == prop) {
				prop = getPropByClassPath(null);
			}

			if (prop.containsKey("mode")) {
				if ("sentinel".equals(prop.getProperty("mode"))) {
					// 初始化哨兵模式
					initSentinelsPool(prop);
				} else if ("cluster".equals(prop.getProperty("mode"))) {
					// 初始化集群模式
					initClusterPool(prop);
				} else {
					// 自动检测
					autoDetect(prop);
				}
			} else {
				// 自动检测
				autoDetect(prop);
			}
			newVersion = prop.containsKey("newVersion")
					&& "true".equals(prop.get("newVersion")) ? true : false;
			LOG.info("redis using newVerson:"+newVersion);
		} catch (Exception e) {
			LOG.error("init redis pool fail", e);
			throw new RuntimeException("init redis pool fail", e);
		}
	}

	/**
	 * 自动检测,多个地址为哨兵模式，单个地址为一般连接
	 * 
	 * @param prop
	 */
	private static void autoDetect(Properties prop) {
		String[] hostAndPorts = getHostAndPorts(prop);
		if (hostAndPorts.length > 1) {
			initSentinelsPool(prop);
		} else {
			initCommonPool(prop);
		}

	}

	/**
	 * 获取主机与端口号列表
	 * 
	 * @param prop
	 * @return
	 */
	private static String[] getHostAndPorts(Properties prop) {
		return prop.getProperty("sentinels").trim().split(",");
	}

	/**
	 * 初始化哨兵池
	 * 
	 * @param prop
	 */
	private static void initSentinelsPool(Properties prop) {
		JedisPoolConfig config = initPoolConfig(prop);
		Set<String> sentinels = new HashSet<String>(
				Arrays.asList(getHostAndPorts(prop)));
		pool = new JedisSentinelPool(prop.getProperty("masterName").trim(),
				sentinels, config, toInteger(prop, "timeout"));
	}

	/**
	 * 初始化集群池
	 * 
	 * @param prop
	 */
	private static void initClusterPool(Properties prop) {
		// wait to implement
		throw new RuntimeException("un implement cluster support!");
	}

	/**
	 * 初始化池配置
	 * 
	 * @param prop
	 * @return
	 */
	private static JedisPoolConfig initPoolConfig(Properties prop) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(toInteger(prop, "maxTotal"));
		config.setMaxIdle(toInteger(prop, "maxIdle"));
		config.setMaxWaitMillis(toInteger(prop, "maxWaitMillis"));
		config.setTestOnBorrow(toBoolean(prop, "testOnBorrow"));
		config.setTestOnReturn(toBoolean(prop, "testOnReturn"));
		return config;
	}

	/**
	 * 初始化哨兵池
	 * 
	 * @param prop
	 */
	private static void initCommonPool(Properties prop) {
		JedisPoolConfig config = initPoolConfig(prop);
		String[] hostAndPorts = getHostAndPorts(prop);
		String[] hostAndPort = hostAndPorts[0].split(":");
		String host = hostAndPort[0];
		int port = Integer.parseInt(hostAndPort[1]);
		pool = new JedisPool(config, host, port, toInteger(prop, "timeout"));

	}

	private static int toInteger(Properties prop, String key) {
		return Integer.parseInt(prop.getProperty(key).trim());
	}

	private static boolean toBoolean(Properties prop, String key) {
		return Boolean.valueOf(prop.getProperty(key).trim());
	}

	/**
	 * 是否是新版本的redis
	 * 
	 * @return
	 */
	public static boolean isNewVersion() {
		return newVersion;
	}

	/**
	 * 模板方法
	 * 
	 * @param rc
	 * @return
	 */
	public static <T> T call(RedisCall<T> rc) {
		Jedis jedis = null;
		try {
			jedis = RedisClientUtils.getResource();
			return rc.run(jedis);
		} finally {
			if (jedis != null) {
				RedisClientUtils.closeResource(jedis);
			}
		}

	}
}
