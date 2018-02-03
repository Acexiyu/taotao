package com.taotao.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转controller
 * @author Ace
 *
 */
@Controller
@RequestMapping("/page")
public class PageController {

	@RequestMapping("/{pageName}")
	public String toPage(@PathVariable String pageName) {
		return pageName;
	}
}
