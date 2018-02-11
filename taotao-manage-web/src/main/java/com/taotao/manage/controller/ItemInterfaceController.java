package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;

/**
 * 商品控制器接口
 * @author Ace
 *
 */
@RestController
@RequestMapping("/item/interface")
public class ItemInterfaceController {

	@Autowired
	private ItemService itemService;
	
	/**
	 * 新增商品
	 * @param item 商品对象
	 * @return 
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> saveItem(Item item) {
		
		try {
			itemService.saveSelective(item);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * 更新商品
	 * @param item 商品对象
	 * @return 
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> updateItem(Item item) {
		
		try {
			if(item.getId() > 0) {
				itemService.updateSelective(item);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * 根据id查询商品信息
	 * @param itemId 商品id
	 * @return 商品对象
	 */
	@RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
	public ResponseEntity<Item> queryItemByItemId(@PathVariable("itemId") Long itemId) {
		
		try {
			Item item = itemService.queryById(itemId);
			return ResponseEntity.ok(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	/**
	 * 删除商品
	 * @param ids 商品id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteItem(@RequestParam(value="ids", required = false) Long[] ids) {
		
		try {
			if(ids != null && ids.length > 0) {
				itemService.deleteByIds(ids);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
