package com.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 跳转首页controller
 * @author Ace
 *
 */
@Controller
public class IndexController {
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public ModelAndView toIndex() {
		ModelAndView mv = new ModelAndView("index");
		
		return mv;
	}
}
