package com.taotao.manage.service;

import com.taotao.manage.pojo.Item;

/**
 * 商品service接口
 * @author Ace
 *
 */
public interface ItemService extends BaseService<Item> {

	/**
	 * 新增商品
	 * @param item 商品基本信息
	 * @param desc 商品描述信息
	 * @return
	 */
	Long saveItem(Item item, String desc);

}
