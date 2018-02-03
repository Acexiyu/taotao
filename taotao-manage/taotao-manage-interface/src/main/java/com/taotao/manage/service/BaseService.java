package com.taotao.manage.service;

import java.io.Serializable;
import java.util.List;

/**
 * 通用模块service
 * @author Ace
 *
 */
public interface BaseService<T> {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public T queryById(Serializable id);
	
	/**
	 * 查询全部
	 * @return
	 */
	public List<T> queryAll();
	
	/**
	 * 根据条件查询记录总数
	 * @param t
	 * @return
	 */
	public long queryCountByWhere(T t);
	
	/**
	 * 根据条件查询列表
	 * @param t
	 * @return
	 */
	public List<T> queryListByWhere(T t);
	
	/**
	 * 分页查询列表
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<T> queryListByPage(Integer page, Integer rows);
	
	/**
	 * 选择性新增
	 * @param t
	 */
	public void saveSelective(T t);
	
	/**
	 * 选择性更新
	 * @param t
	 */
	public void updateSelective(T t);
	
	/**
	 * 根据id删除一条数据
	 * @param id
	 */
	public void deleteById(Serializable id);
	
	/**
	 * 根据id集合删除多条数据
	 * @param ids
	 */
	public void deleteByIds(Serializable[] ids);
}
