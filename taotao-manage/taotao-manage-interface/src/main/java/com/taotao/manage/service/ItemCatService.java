package com.taotao.manage.service;

import java.util.List;

import com.taotao.manage.pojo.ItemCat;

/**
 * 商品类目service接口
 * @author Ace
 *
 */
public interface ItemCatService {

	/**
	 * 分页查询商品类目
	 * @param page 当前页号
	 * @param rows 页面大小
	 * @return
	 */
	public List<ItemCat> queryItemCatListByPage(Integer page, Integer rows);
}
