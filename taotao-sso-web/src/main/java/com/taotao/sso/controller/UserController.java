package com.taotao.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.sso.service.UserService;

@RequestMapping("/user")
@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	/**
	 * 检查数据是否可用
	 * @param param 校验的数据
	 * @param type type为类型，可选参数1、2、3分别代表username、phone、email
	 * @return true：数据可用，false：数据不可用
	 */
	@RequestMapping(value = "/check/{param}/{type}", method = RequestMethod.GET)
	public ResponseEntity<String> check(@PathVariable("param")String param, @PathVariable("type")Integer type ,
			@RequestParam(value = "callback", required = false)String callback) {
		try {
			if(type >= 1 && type <= 3) {
				Boolean bool = userService.check(param, type);
				String result = callback + "(" + bool.toString() + ")";
				return ResponseEntity.ok(result);
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	/**
	 * 根据ticket查询用户
	 * @param ticket 用户登录凭证
	 * @return 用户信息json格式字符串
	 */
	@RequestMapping(value = "/{ticket}", method = RequestMethod.GET)
	public ResponseEntity<String> QueryUserByticket(@PathVariable("ticket")String ticket, 
			@RequestParam(value = "callback", required = false)String callback) {
		try {
			if(StringUtils.isNotBlank(ticket)) {
				String userJsonStr = userService.QueryUserJsonStrByticket(ticket);
				String result = callback + "(" + userJsonStr + ")";
				return ResponseEntity.ok(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
