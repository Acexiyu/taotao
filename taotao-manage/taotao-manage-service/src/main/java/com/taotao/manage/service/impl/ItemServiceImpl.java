package com.taotao.manage.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.manage.mapper.ItemDescMapper;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemService;

/**
 * 商品service实现
 * @author Ace
 *
 */
@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {

	//注入itemMapper
	@Autowired
	private ItemMapper itemMapper;
	
	//注入ItemDescMapper
	@Autowired
	private ItemDescMapper itemDescMapper;

	@Override
	public Long saveItem(Item item, String desc) {
		//向商品基本信息表插入一条数据
		this.saveSelective(item);
		
		//创建商品描述信息实体对象
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(itemDesc.getCreated());
		//向商品描述信息插入一条数据
		itemDescMapper.insert(itemDesc);
		
		//返回该商品的id
		return item.getId();
	}

}
