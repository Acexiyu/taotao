package com.taotao.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.vo.DataGridResult;
import com.taotao.manage.mapper.ContentMapper;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.service.ContentService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 内容管理service实现
 * @author Ace
 *
 */
@Service
public class ContentServiceImpl extends BaseServiceImpl<Content> implements ContentService {

	//注入ContentMapper
	@Autowired
	private ContentMapper contentMapper;

	@Override
	public DataGridResult queryContentByCategoryIdAsUpdatedDescToPage(Long categoryId, Integer page, Integer rows) {
		Example example = new Example(Content.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("categoryId", categoryId);
		example.orderBy("updated").desc();
		
		PageHelper.startPage(page, rows);
		
		List<Content> list = contentMapper.selectByExample(example);
		
		PageInfo<Content> pageInfo = new PageInfo<>(list);
		
		return new DataGridResult(pageInfo.getTotal(), pageInfo.getList());
	}

}
