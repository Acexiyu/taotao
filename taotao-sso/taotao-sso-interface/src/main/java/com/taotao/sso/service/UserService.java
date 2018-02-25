package com.taotao.sso.service;

import com.taotao.sso.pojo.User;

public interface UserService {

	/**
	 * 检查数据是否可用
	 * @param param 校验的数据
	 * @param type type为类型，可选参数1、2、3分别代表username、phone、email
	 * @return true：数据可用，false：数据不可用
	 */
	Boolean check(String param, Integer type);

	/**
	 * 根据ticket查询用户
	 * @param ticket 用户登录凭证
	 * @return 用户信息json格式字符串
	 */
	String QueryUserJsonStrByticket(String ticket);

	/**
	 * 新增用户
	 * @param user
	 */
	void saveUser(User user);

	/**
	 * 用户登录
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	String login(User user) throws Exception;

	/**
	 * 用户注销登录
	 * @param ticket
	 */
	void logout(String ticket);

	
}
