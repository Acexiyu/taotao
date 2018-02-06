package com.taotao.manage.vo;

/**
 * 上传图片处理结果对象实体
 * @author Ace
 *
 */
public class PicUploadResult {

	private Integer error;
	private String url;
	private Integer heigth;
	private Integer width;
	public Integer getError() {
		return error;
	}
	public void setError(Integer error) {
		this.error = error;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getHeigth() {
		return heigth;
	}
	public void setHeigth(Integer heigth) {
		this.heigth = heigth;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
}
