package com.taotao.manage.service.redis;

/**
 * Redis Service接口
 * @author Ace
 *
 */
public interface JedisService {

	/**
	 * 设置一个字符串的键值对 
	 * @param key
	 * @param value 
	 * @return
	 */
	public String set(String key, String value);

	/**
	 * 设置一个字符串的键值对并同时设置过期时间
	 * @param key
	 * @param seconds
	 * @param value
	 * @return
	 */
	public String setex(String key, int seconds, String value);

	/**
	 * 设置key过期
	 * @param key
	 * @param seconds
	 * @return
	 */
	public Long expire(String key, int seconds);

	/**
	 * 获取key值
	 * @param key
	 * @return
	 */
	public String get(String key);

	/**
	 *  删除key
	 * @param key
	 * @return
	 */
	public Long del(String key);

	/**
	 * 自增
	 * @param key
	 * @return
	 */
	public Long incr(String key);
	
}

