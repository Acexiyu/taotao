package com.taotao.manage.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.vo.DataGridResult;
import com.taotao.manage.mapper.ItemDescMapper;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

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

	@Override
	public DataGridResult queryItemPageByTitleAsUpdatedDesc(String title, Integer page, Integer rows) {
		Item item = new Item();
		item.setTitle(title);
		//设置分页查询
		PageHelper.startPage(page, rows);
		
		List<Item> list = itemMapper.queryItemListByUpdatedDesc(item);
		
		PageInfo<Item> pageInfo = new PageInfo<>(list);
		
		return new DataGridResult(pageInfo.getTotal(), pageInfo.getList());
	}
	
	/*@Override
	public DataGridResult queryItemPageByTitleAsUpdatedDesc(String title, Integer page, Integer rows) {
		//设置分页查询
		PageHelper.startPage(page, rows);
		
		//构建查询条件
		Example example = new Example(Item.class);
		
		if(StringUtils.isNotBlank(title)) {
			try {
				title = URLDecoder.decode(title, "utf-8");
				Criteria createCriteria = example.createCriteria();
				createCriteria.andLike("title", "%" + title + "%");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		//按照更新时间降序
		example.orderBy("updated").desc();
		
		List<Item> list = itemMapper.selectByExample(example);
		
		PageInfo<Item> pageInfo = new PageInfo<>(list);
		
		return new DataGridResult(pageInfo.getTotal(), pageInfo.getList());
	}*/

	@Override
	public Long updateItem(Item item, String desc) {
		this.updateSelective(item);
		
		//创建商品描述信息实体对象
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(itemDesc.getCreated());
		//向商品描述信息更新一条数据
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
		
		//返回该商品的id
		return item.getId();
	}

	@Override
	public void updateStatusByIds(Integer status, Long[] ids) {
		Item item = new Item();
		item.setStatus(status);
		
		Example example = new Example(Item.class);
		Criteria criteria = example.createCriteria();
		criteria.andIn("id", Arrays.asList(ids));
		
		itemMapper.updateByExampleSelective(item, example);
	}
	
	

}
