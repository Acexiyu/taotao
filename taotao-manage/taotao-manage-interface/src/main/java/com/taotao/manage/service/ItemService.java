package com.taotao.manage.service;

import com.taotao.common.vo.DataGridResult;
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

	/**
	 * 根据商品标题分页查询并按照更新时间降序排序
	 * @param title
	 * @param page
	 * @param rows
	 * @return
	 */
	DataGridResult queryItemPageByTitleAsUpdatedDesc(String title, Integer page, Integer rows);

	/**
	 * 更新商品
	 * @param item
	 * @param desc
	 */
	Long updateItem(Item item, String desc);

	/**
	 * 批量上下架商品
	 * @param status 1：上架； 2：下架
	 * @param ids	商品ID
	 */
	void updateStatusByIds(Integer status, Long[] ids);

}
