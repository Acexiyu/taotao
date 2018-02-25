package com.taotao.portal.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.portal.util.CookieUtils;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;

@RequestMapping("/user")
@Controller
public class UserController {
	
	private static final String COOKIE_NAME = "TT_TICKET";
	
	//Cookie有效时间
	private static final int COOKIE_MAX_AGE = 60*60;
	
	@Autowired
	private UserService userService;

	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/doRegister", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> register(User user) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", 500);
		try {
			userService.saveUser(user);
			result.put("status", 200);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("data", e.getMessage());
		}
		return ResponseEntity.ok(result);
	}
	
	/**
	 * 用户登录
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "doLogin", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> login(User user, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", 500);
		try {
			String ticket = userService.login(user);
			if(StringUtils.isNotBlank(ticket)) {
				result.put("status", 200);
				CookieUtils.setCookie(request, response, COOKIE_NAME, ticket, COOKIE_MAX_AGE);
			}else {
				result.put("status", 400);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(result);
	}
	
	/**
	 * 注销登录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		String ticket = CookieUtils.getCookieValue(request, COOKIE_NAME);
		if(StringUtils.isNotBlank(ticket)) {
			userService.logout(ticket);
			CookieUtils.deleteCookie(request, response, COOKIE_NAME);
		}
		return "redirect:/index.html";
	}
}
