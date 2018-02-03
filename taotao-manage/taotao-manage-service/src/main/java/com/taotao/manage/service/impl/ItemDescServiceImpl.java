package com.taotao.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.manage.mapper.ItemDescMapper;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemDescService;

/**
 * 商品描述信息service实现
 * @author Ace
 *
 */
@Service
public class ItemDescServiceImpl extends BaseServiceImpl<ItemDesc> implements ItemDescService {

	//注入ItemDescMapper
	@Autowired
	private ItemDescMapper itemDescMapper;

}
