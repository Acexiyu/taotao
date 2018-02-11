package com.taotao.common.service.redis;

/**
 * JedisPool 方法处理
 * @author Ace
 *
 */
public interface JedisPoolFunction<T, E> {

	public E callback(T jedis);
}
