package com.taotao.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;

/**
 * 商品类目service实现
 * @author Ace
 *
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	//注入ItemCatMapper
	@Autowired
	private ItemCatMapper itemCatMapper;
	
	@Override
	public List<ItemCat> queryItemCatListByPage(Integer page, Integer rows) {
		//设置分页参数
		PageHelper.startPage(page, rows);
		//调用itemCatMapper查询
		return itemCatMapper.selectAll();
	}

}
