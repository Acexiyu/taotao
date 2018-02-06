package com.taotao.manage.service;

import java.util.List;

import com.taotao.manage.pojo.ContentCategory;

/**
 * 内容分类service接口
 * @author Ace
 *
 */
public interface ContentCategoryService extends BaseService<ContentCategory> {

	/**
	 * 根据父类目id查询所有子节点
	 * @param parentId
	 * @return
	 */
	List<ContentCategory> queryContentCategoryByParentId(Long parentId);

	/**
	 * 新增内容分类
	 * @param contentCategory
	 */
	ContentCategory addContentCategory(ContentCategory contentCategory);

	/**
	 * 删除内容分类与其下所有子节点
	 * @param contentCategory
	 */
	void deleteContentCategory(ContentCategory contentCategory);

}
