package com.taotao.manage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.vo.DataGridResult;
import com.taotao.manage.mapper.ContentMapper;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.service.ContentService;
import com.taotao.manage.service.redis.JedisService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 内容管理service实现
 * @author Ace
 *
 */
@Service
public class ContentServiceImpl extends BaseServiceImpl<Content> implements ContentService {

	//门户系统大广告内容分类id
	@Value("${PORTAL_BIG_AD_CONTENT_CATEGORY_ID}")
	private Long PORTAL_BIG_AD_CONTENT_CATEGORY_ID;
	//门户系统大广告个数
	@Value("${PORTAL_BIG_AD_NUM}")
	private Integer PORTAL_BIG_AD_NUM;
	//门户系统大广告图片高度1 height
	@Value("${PORTAL_BIG_AD_HEIGHT}")
	private Integer PORTAL_BIG_AD_HEIGHT;
	//门户系统大广告图片高度2 heightB
	@Value("${PORTAL_BIG_AD_HEIGHTB}")
	private Integer PORTAL_BIG_AD_HEIGHTB;
	//门户系统大广告图片宽度1 width
	@Value("${PORTAL_BIG_AD_WIDTH}")
	private Integer PORTAL_BIG_AD_WIDTH;
	//门户系统大广告图片宽度2 widthB
	@Value("${PORTAL_BIG_AD_WIDTHB}")
	private Integer PORTAL_BIG_AD_WIDTHB;
	//门户系统大广告在redis的有效时间
	private int REDIS_BIG_AD_EXPIRE_TIME = 60*60*24;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	
	//注入ContentMapper
	@Autowired
	private ContentMapper contentMapper;
	
	@Autowired
	private JedisService jedisService;

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

	@Override
	public String queryPortalBigAdData() throws Exception {
		
		String bigAdDataJson;
		//先去redis中查找
		try {
			bigAdDataJson = jedisService.get("PORTAL_BIG_AD_DATA");
			//找到直接返回给客户端
			if(StringUtils.isNotBlank(bigAdDataJson)) {
				return bigAdDataJson;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//redis中找不到，去数据库中查找
		DataGridResult result = 
				queryContentByCategoryIdAsUpdatedDescToPage(PORTAL_BIG_AD_CONTENT_CATEGORY_ID, 1, PORTAL_BIG_AD_NUM);
		
		List<Content> rows = (List<Content>)result.getRows();
		
		List<Map<String, Object>> bigAdData = new ArrayList<>();
		
		//封装数据格式
		Map<String, Object> map = null;
		if(rows != null && rows.size() > 0) {
			for (Content content : rows) {
				map = new HashMap<String, Object>();
				map.put("alt", content.getTitle());
				map.put("height", PORTAL_BIG_AD_HEIGHT);
				map.put("heightB", PORTAL_BIG_AD_HEIGHTB);
				map.put("href", content.getUrl());
				map.put("src", content.getPic());
				map.put("srcB", content.getPic2());
				map.put("width", PORTAL_BIG_AD_WIDTH);
				map.put("widthB", PORTAL_BIG_AD_WIDTHB);
				bigAdData.add(map);
			}
		}
		
		//转换成json字符串
		bigAdDataJson = MAPPER.writeValueAsString(bigAdData);
		
		//设置到redis中
		try {
			jedisService.setex("PORTAL_BIG_AD_DATA", REDIS_BIG_AD_EXPIRE_TIME, bigAdDataJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//返回给客户端
		return bigAdDataJson;
	}

}
