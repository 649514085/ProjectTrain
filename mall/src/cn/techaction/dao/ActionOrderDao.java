package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionOrder;

public interface ActionOrderDao {

	/**
	 * 获取用户订单总数（各种状态）
	 * @param user_id
	 * @param status
	 * @return
	 */
	public int getTotalRecord(String user_id, Integer status);

	/**
	 * 获得用户订单分页列表
	 * @param user_id
	 * @param status
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<ActionOrder> findOrders(String user_id, Integer status, int startIndex, int pageSize);

	/**
	 * 插入订单
	 * @param order
	 * @return
	 */
	public int insertOrder(ActionOrder order);

	/**
	 * 根据用户和订单编号查询订单信息
	 * @param user_id
	 * @param orderId
	 * @return
	 */
	public ActionOrder findOrderByUserAndOrderId(String user_id, String orderId);

	/**
	 * 更新订单信息
	 * @param updateOrder
	 * @return
	 */
	public int updateOrder(ActionOrder updateOrder);

}
