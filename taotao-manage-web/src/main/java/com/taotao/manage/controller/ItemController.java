package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;

/**
 * 商品controller
 * @author Ace
 *
 */
@Controller
@RequestMapping("/item")
public class ItemController {

	//注入ItemService
	@Autowired
	private ItemService itemService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> saveItem(Item item ,@RequestParam(value="desc", required=false) String desc) {
		try {
			//调用service向数据库插入数据
			itemService.saveItem(item, desc);
			
			//插入成功，向客户端返回http响应状态码200
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//插入失败，向客户端返回http响应状态码500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
