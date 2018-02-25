package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.redis.JedisService;
import com.taotao.sso.mapper.UserMapper;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final String TICKET_PREFIX = "TT_TICKET_";

	//ticket的用户信息在redis中的有效时间
	private static final int TICKET_TIME = 60*60;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private JedisService jedisService;

	@Override
	public Boolean check(String param, Integer type) {
		User user = new User();
		switch(type) {
			case 1 :
				user.setUsername(param);
			case 2 :
				user.setPhone(param);
			default :
				user.setEmail(param);
		}
		int count = userMapper.selectCount(user);
		return count == 0;
	}

	@Override
	public String QueryUserJsonStrByticket(String ticket) {
		String key = TICKET_PREFIX + ticket;
		String userJsonStr = jedisService.get(key);
		if(StringUtils.isNotBlank(userJsonStr)) {
			jedisService.expire(key, TICKET_TIME);
		}
		return userJsonStr;
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		userMapper.insertSelective(user);
	}

	@Override
	public String login(User user) throws Exception {
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		List<User> list = userMapper.select(user);
		String ticket = "";
		if(list != null && list.size() > 0) {
			User tmp = list.get(0);
			ticket = DigestUtils.md5Hex(tmp.getUsername());
			String key = TICKET_PREFIX + ticket;
			jedisService.setex(key, TICKET_TIME, MAPPER.writeValueAsString(tmp));
		}
		return ticket;
	}

	@Override
	public void logout(String ticket) {
		String key = TICKET_PREFIX + ticket;
		jedisService.del(key);
	}
}
