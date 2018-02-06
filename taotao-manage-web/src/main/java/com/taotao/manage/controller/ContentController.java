package com.taotao.manage.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.vo.DataGridResult;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.service.ContentService;

/**
 * 内容controller
 * @author Ace
 *
 */

@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	/**
	 * 根据内容类别id分页查询并按更新时间降序排序
	 * @param categoryId 内容类别id
	 * @param page 当前页号
	 * @param rows 页大小
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<DataGridResult> queryContentByCategoryIdAsUpdatedDescToPage(@RequestParam(value = "categoryId", defaultValue = "0") Long categoryId, 
			@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "rows", defaultValue = "20") Integer rows) {
		try {
			DataGridResult result = contentService.queryContentByCategoryIdAsUpdatedDescToPage(categoryId, page, rows);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> saveContent(Content content) {
		try {
			contentService.saveSelective(content);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * 更新广告内容
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ResponseEntity<Void> updateContent(Content content) {
		try {
			contentService.updateSelective(content);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	/**
	 * 批量删除广告
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> deleteContent(@RequestParam(value = "ids") Long[] ids) {
		Map<String, Object> result = new HashMap<>();
		try {
			contentService.deleteByIds(ids);
			result.put("status", 200);
		} catch (Exception e) {
			result.put("status", 500);
			result.put("msg", e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.ok(result);
	}
	
}
