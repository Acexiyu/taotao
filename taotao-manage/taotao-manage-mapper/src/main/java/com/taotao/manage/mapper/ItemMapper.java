package com.taotao.manage.mapper;

import java.util.List;

import com.taotao.manage.pojo.Item;

import tk.mybatis.mapper.common.Mapper;

/**
 * 商品mapper
 * @author Ace
 *
 */
public interface ItemMapper extends Mapper<Item> {

	List<Item> queryItemListByUpdatedDesc(Item item);
}
