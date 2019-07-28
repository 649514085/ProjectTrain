package cn.techaction.service;

import cn.techaction.common.SverResponse;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionOrderVo;

public interface ActionOrderService {

	/**
	 * 订单分页列表
	 * @param user_id
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public SverResponse<PageBean<ActionOrderVo>> findOrders(String user_id, Integer status, int pageNum, int pageSize);

	/**
	 * 生成订单
	 * @param user_id
	 * @param addrId
	 * @return
	 */
	public SverResponse<ActionOrderVo> generateOrder(String user_id, Integer addrId);

	/**
	 * 修改订单状态（未支付->已支付）
	 * @param user_id
	 * @param orderId
	 * @return
	 */
	public SverResponse<String> payOrder(String user_id, String orderId);

	/**
	 * 根据订单编号获取订单详情
	 * @param user_id
	 * @param orderId
	 * @return
	 */
	public SverResponse<ActionOrderVo> findOrderDetail(String user_id, String orderId);

	/**
	 * 取消订单
	 * @param user_id
	 * @param orderId
	 * @return
	 */
	public SverResponse<String> cancelOrder(String user_id, String orderId);

	/**
	 * 确认订单
	 * @param user_id
	 * @param orderId
	 * @return
	 */
	public SverResponse<String> confirmOrder(String user_id, String orderId);

}
