package com.taotao.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.manage.mapper.ContentCategoryMapper;
import com.taotao.manage.pojo.ContentCategory;
import com.taotao.manage.service.ContentCategoryService;

/**
 * 内容分类service实现
 * @author Ace
 *
 */
@Service
public class ContentCategoryServiceImpl extends BaseServiceImpl<ContentCategory> implements ContentCategoryService {

	//注入ContentCategoryMapper
	@Autowired
	private ContentCategoryMapper contentCategoryMapper;

	@Override
	public List<ContentCategory> queryContentCategoryByParentId(Long parentId) {
		ContentCategory c = new ContentCategory();
		c.setParentId(parentId);
		List<ContentCategory> list = contentCategoryMapper.select(c);
		return list;
	}

	@Override
	public ContentCategory addContentCategory(ContentCategory contentCategory) {
		contentCategory.setIsParent(false);
		contentCategory.setSortOrder(100);
		
		this.saveSelective(contentCategory);
		
		ContentCategory parent = new ContentCategory();
		parent.setId(contentCategory.getParentId());
		parent.setIsParent(true);
		
		this.updateSelective(parent);
		
		return contentCategory;
	}

	@Override
	public void deleteContentCategory(ContentCategory contentCategory) {
		if(contentCategory.getParentId() == 0) {
			throw new RuntimeException("不能删除根节点");
		}
		
		List<Long> ids = new ArrayList<Long>();
		
		Long id = contentCategory.getId();
		
		ids.add(id);
		getContentCategoryChildrenIds(id, ids);
		
		this.deleteByIds(ids.toArray(new Long[] {}));
		
		contentCategory.setId(null);
		
		long count = this.queryCountByWhere(contentCategory);
		
		if(count == 0) {
			ContentCategory parent = new ContentCategory();
			parent.setId(contentCategory.getParentId());
			parent.setIsParent(false);
			this.updateSelective(parent);
		}
	}

	/**
	 * 获取该节点下所有的子孙节点的id
	 * @param parentId 目标节点id
	 * @param ids 存放id的集合
	 */
	private void getContentCategoryChildrenIds(Long id, List<Long> ids) {
		ContentCategory contentCategory = new ContentCategory();
		contentCategory.setParentId(id);
		
		List<ContentCategory> list = this.queryListByWhere(contentCategory);
		
		if(list != null && list.size() > 0) {
			for (ContentCategory children : list) {
				ids.add(children.getId());
				getContentCategoryChildrenIds(children.getId(), ids);
			}
		}
	}
	
	
	
	
}
