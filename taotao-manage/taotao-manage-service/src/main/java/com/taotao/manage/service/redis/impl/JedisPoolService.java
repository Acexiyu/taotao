package com.taotao.manage.service.redis.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.manage.service.redis.JedisPoolFunction;
import com.taotao.manage.service.redis.JedisService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 单级版Jedis Service接口实现类
 * @author Ace
 *
 */
public class JedisPoolService implements JedisService {

	@Autowired
	private JedisPool jedisPool;
	
	public <T> T execute(JedisPoolFunction<Jedis, T> fun) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return fun.callback(jedis);
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
	@Override
	public String set(final String key, final String value) {
		return execute(new JedisPoolFunction<Jedis, String>() {
			@Override
			public String callback(Jedis jedis) {
				return jedis.set(key, value);
			}
		});
	}

	@Override
	public String setex(final String key, final int seconds, final String value) {
		return execute(new JedisPoolFunction<Jedis, String>() {
			@Override
			public String callback(Jedis jedis) {
				return jedis.setex(key, seconds, value);
			}
		});
	}

	@Override
	public Long expire(final String key, final int seconds) {
		return execute(new JedisPoolFunction<Jedis, Long>() {
			@Override
			public Long callback(Jedis jedis) {
				return jedis.expire(key, seconds);
			}
		});
	}

	@Override
	public String get(final String key) {
		return execute(new JedisPoolFunction<Jedis, String>() {
			@Override
			public String callback(Jedis jedis) {
				return jedis.get(key);
			}
		});
	}

	@Override
	public Long del(final String key) {
		return execute(new JedisPoolFunction<Jedis, Long>() {
			@Override
			public Long callback(Jedis jedis) {
				return jedis.del(key);
			}
		});
	}

	@Override
	public Long incr(final String key) {
		return execute(new JedisPoolFunction<Jedis, Long>() {
			@Override
			public Long callback(Jedis jedis) {
				return jedis.incr(key);
			}
		});
	}

}
