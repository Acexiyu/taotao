package com.taotao.manage.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.manage.utils.PicUploadUtils;
import com.taotao.manage.vo.PicUploadResult;

/**
 * 图片上传处理器
 * @author Ace
 *
 */
@Controller
@RequestMapping("/pic")
public class PicUploadController {
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@RequestMapping(value = "/upload", produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String upload(@RequestParam(value="uploadFile") MultipartFile multipartFile) throws JsonProcessingException {
		
		PicUploadResult result = PicUploadUtils.upload(multipartFile);
		
		//返回图片上传处理结果对象
		return  MAPPER.writeValueAsString(result);
	}
	
}
