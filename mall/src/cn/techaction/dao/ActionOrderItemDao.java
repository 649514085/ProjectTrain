package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionOrderItem;

public interface ActionOrderItemDao {

	/**
	 * ���ݶ���id��ö�����
	 * @param order_id
	 * @return
	 */
	public List<ActionOrderItem> getItemsByOrderId(String order_id);

	/**
	 * �������붩����
	 * @param orderItems
	 */
	public int[] batchInsert(List<ActionOrderItem> orderItems);
}
