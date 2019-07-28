package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import cn.techaction.dao.ActionOrderDao;
import cn.techaction.pojo.Order;
import cn.techaction.pojo.OrderItem;

@Repository
public class ActionOrderDaoImpl implements ActionOrderDao{
	@Resource
	private QueryRunner queryRunner;
	
	@Override
	public List<Order> findOrders(){
		String sql = "select * from orders";
		try {
			List<Order>  orders= queryRunner.query(sql, new BeanListHandler<Order>(Order.class));
			return orders;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Order> findOrdersByOid(String order_id){
		String sql = "select * from orders where order_id=?";
		try {
			List<Order>  orders= queryRunner.query(sql, new BeanListHandler<Order>(Order.class),order_id);
			return orders;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public List<Order> findOrderByUid(String user_id){
		String sql = "select * from orders where user_id=?";
		try {
			List<Order>  orders= queryRunner.query(sql, new BeanListHandler<Order>(Order.class), user_id);
			return orders;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public List<OrderItem> searchOrderItems(String order_id){
		String sql = "select * from order_items where order_id=?";
		try {
			return queryRunner.query(sql, new BeanListHandler<OrderItem>(OrderItem.class), order_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
