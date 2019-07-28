package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.Order;
import cn.techaction.pojo.OrderItem;

public interface ActionOrderDao {
	public List<Order> findOrders();
	public List<Order> findOrdersByOid(String order_id);
	public List<Order> findOrderByUid(String user_id);
	public List<OrderItem> searchOrderItems(String order_id);
	
}
