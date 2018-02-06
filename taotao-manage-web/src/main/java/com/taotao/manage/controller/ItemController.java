package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.vo.DataGridResult;
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
	
	/**
	 * 新增商品
	 * @param item
	 * @param desc
	 * @return
	 */
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public ResponseEntity<Void> updateItem(Item item ,@RequestParam(value="desc", required=false) String desc) {
		try {
			//调用service向数据库插入数据
			itemService.updateItem(item, desc);
			
			//插入成功，向客户端返回http响应状态码200
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//插入失败，向客户端返回http响应状态码500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * 新增商品
	 * @param item
	 * @param desc
	 * @return
	 */
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
	
	/**
	 * 根据id批量删除商品
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public ResponseEntity<Void> deleteItem(Long[] ids) {
		try {
			itemService.deleteByIds(ids);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * 查询商品列表
	 * @param title
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<DataGridResult> queryItemPageByTitleAsUpdatedDesc(@RequestParam(value="title", required=false) String title, 
			@RequestParam(value="page", defaultValue="1") Integer page, @RequestParam(value="rows", defaultValue="30") Integer rows) {
		try {
			DataGridResult result = itemService.queryItemPageByTitleAsUpdatedDesc(title, page, rows);
			
			return ResponseEntity.ok().body(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	@RequestMapping("/instock")
	public ResponseEntity<Void> instockItem(Long[] ids) {
		try {
			itemService.updateStatusByIds(2, ids);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@RequestMapping("/reshelf")
	public ResponseEntity<Void> reshelfItem(Long[] ids) {
		try {
			itemService.updateStatusByIds(1, ids);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
