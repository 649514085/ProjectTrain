package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionOrderItem;

public interface ActionOrderItemDao {

	/**
	 * 根据订单id获得订单项
	 * @param order_id
	 * @return
	 */
	public List<ActionOrderItem> getItemsByOrderId(String order_id);

	/**
	 * 批量插入订单项
	 * @param orderItems
	 */
	public int[] batchInsert(List<ActionOrderItem> orderItems);
}
