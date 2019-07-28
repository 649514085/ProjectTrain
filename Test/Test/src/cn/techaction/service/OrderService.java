package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.OrderItem;
import cn.techaction.vo.OrderVo;

public interface OrderService {
	public SverResponse<List<OrderVo>> findOrders();
	public SverResponse<List<OrderVo>> findOrderByOid(String order_id);
	public SverResponse<List<OrderVo>> findOrderByUid(String order_id);
	public SverResponse<List<OrderItem>> findOrderItem(String order_id);
	
}
