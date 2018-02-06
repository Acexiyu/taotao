package com.taotao.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;

/**
 * 商品类目controller
 * @author Ace
 *
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

	//注入itemCatService
	@Autowired
	private ItemCatService itemCatService;
	
	/**
	 * 分页查询商品类目
	 * @param page 当前页号
	 * @param rows 页面大小（默认为20）
	 * @return
	 */
	@RequestMapping("/query/{page}")
	public ResponseEntity<List<ItemCat>> queryItemCatByPage(@PathVariable Integer page,
			@RequestParam(value="rows",defaultValue="20") Integer rows) {
		try {
			//List<ItemCat> list = itemCatService.queryItemCatListByPage(page, rows);
			List<ItemCat> list = itemCatService.queryListByPage(page, rows);
			//向客户端返回查询数据
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//出现异常，向客户端返回500状态码
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	/**
	 * 根据商品类目的父id查询类目列表
	 * @param parentId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ItemCat>> queryListByParentId(@RequestParam(value="id", defaultValue="0") Long parentId) {
		
		try {
			ItemCat itemCat = new ItemCat();
			itemCat.setParentId(parentId);
			
			List<ItemCat> itemCatList = itemCatService.queryListByWhere(itemCat);
			
			return ResponseEntity.ok(itemCatList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	/**
	 * 根据id查询商品类目
	 * @param id
	 * @return
	 */
	@RequestMapping("/{id}")
	public ResponseEntity<ItemCat> queryById(@PathVariable Long id) {
		try {
			ItemCat itemCat = itemCatService.queryById(id);
			return ResponseEntity.ok().body(itemCat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
