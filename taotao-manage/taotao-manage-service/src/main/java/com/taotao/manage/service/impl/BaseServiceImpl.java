package com.taotao.manage.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.taotao.manage.pojo.BasePojo;
import com.taotao.manage.service.BaseService;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 通用模块service实现类
 * @author Ace
 *
 * @param <T>
 */
public class BaseServiceImpl<T extends BasePojo> implements BaseService<T> {

	//注入mapper	spring4.0以后，支持泛型注入
	@Autowired
	private Mapper<T> mapper;
	
	private Class<T> clazz;
	
	//在构造方法中初始化clazz，获取泛型的class
	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
		ParameterizedType pt = (ParameterizedType)this.getClass().getGenericSuperclass();
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0];
	}

	@Override
	public T queryById(Serializable id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<T> queryAll() {
		return mapper.selectAll();
	}

	@Override
	public long queryCountByWhere(T t) {
		return mapper.selectCount(t);
	}

	@Override
	public List<T> queryListByWhere(T t) {
		return mapper.select(t);
	}

	@Override
	public List<T> queryListByPage(Integer page, Integer rows) {
		//设置分页查询
		PageHelper.startPage(page, rows);
		
		return mapper.select(null);
	}

	@Override
	public void saveSelective(T t) {
		if(t.getCreated() == null) {
			t.setCreated(new Date());
		}
		if(t.getUpdated() == null) {
			t.setUpdated(t.getCreated());
		}
		mapper.insertSelective(t);
		
	}

	@Override
	public void updateSelective(T t) {
		t.setUpdated(new Date());
		mapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public void deleteById(Serializable id) {
		mapper.deleteByPrimaryKey(id);
		
	}

	@Override
	public void deleteByIds(Serializable[] ids) {
		Example example = new Example(this.clazz);
		Criteria criteria = example.createCriteria();
		//构造“ id in(1,2,3) 语句”
		criteria.andIn("id", Arrays.asList(ids));
		
		mapper.deleteByExample(example);
	}

}
