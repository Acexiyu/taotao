package com.taotao.search;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;
import com.taotao.search.service.SearchService;
import com.taotao.search.vo.SolrItem;

public class TaoTaoItemImport2SolrTest {
	
	private SearchService searchService;
	
	private ItemService itemService;

	@Before
	public void setUp() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/*.xml");
		searchService = context.getBean(SearchService.class);
		itemService = context.getBean(ItemService.class);
	}

	@Test
	public void saveOfUpdateSolrItemListTest() {
		try {
			List<Item> itemList = new ArrayList<Item>();
			int page = 1;
			int rows = 500;
			List<SolrItem> solrItemList = null;
			
			do {
				System.out.println("第" + page + "加载数据到索引库中...");
				itemList = itemService.queryListByPage(page, rows);
				
				if(itemList != null && itemList.size() > 0 ) {
					solrItemList = new ArrayList<SolrItem>();
					for (Item item : itemList) {
						SolrItem solrItem = new SolrItem();
						solrItem.setId(item.getId());
						solrItem.setImage(item.getImage());
						solrItem.setPrice(item.getPrice());
						solrItem.setStatus(item.getStatus());
						solrItem.setSellPoint(item.getSellPoint());
						solrItem.setTitle(item.getTitle());
						solrItemList.add(solrItem);
					}
				}
				
				searchService.saveOfUpdateSolrItemList(solrItemList);
				
				System.out.println("第" + page + "加载数据到索引库完毕。");
				page++;
				rows = itemList.size();
			} while (rows == 500);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
