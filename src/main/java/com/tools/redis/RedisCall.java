/** 
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay) 
 */
package com.tools.redis;

import redis.clients.jedis.Jedis;

/**
 */
public interface RedisCall<T> {
	/**
	 * 操作
	 * 
	 * @param jedis
	 * @return
	 */
	T run(Jedis jedis);
}
