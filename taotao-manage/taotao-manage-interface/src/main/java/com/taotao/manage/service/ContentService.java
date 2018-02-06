package com.taotao.manage.service;

import com.taotao.common.vo.DataGridResult;
import com.taotao.manage.pojo.Content;

/**
 * 内容service接口
 * @author Ace
 *
 */
public interface ContentService extends BaseService<Content> {


	/**
	 * 根据内容类别id分页查询并按更新时间降序排序
	 * @param categoryId 内容类别id
	 * @param page 当前页号
	 * @param rows 页大小
	 * @return
	 */
	DataGridResult queryContentByCategoryIdAsUpdatedDescToPage(Long categoryId, Integer page, Integer rows);

}
