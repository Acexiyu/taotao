package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemDescService;

/**
 * 商品类目controller
 * @author Ace
 *
 */
@Controller
@RequestMapping("/item/desc")
public class ItemDescController {

	//注入itemDescService
	@Autowired
	private ItemDescService itemDescService;
	
	/**
	 * 根据titleId查询商品描述信息
	 * @param titleId
	 * @return
	 */
	@RequestMapping("/{titleId}")
	public ResponseEntity<ItemDesc> queryByTitleId(@PathVariable Long titleId) {
		try {
			ItemDesc itemDesc = itemDescService.queryById(titleId);
			return ResponseEntity.ok().body(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
