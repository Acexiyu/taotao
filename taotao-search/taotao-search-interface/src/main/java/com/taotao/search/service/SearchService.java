package com.taotao.search.service;

import java.util.List;

import com.taotao.search.vo.SolrItem;

public interface SearchService {
	
	/**
	 * 添加或新增索引数据列表
	 * @param solrItemList
	 * @throws Exception 
	 */
	void saveOfUpdateSolrItemList(List<SolrItem> solrItemList) throws Exception;

}
