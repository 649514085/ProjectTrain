package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import cn.techaction.dao.ActionOrderDao;
import cn.techaction.pojo.ActionOrder;

@Repository
public class ActionOrderDaoImpl implements ActionOrderDao {

	@Resource
	private QueryRunner queryRunner;

	@Override
	public int getTotalRecord(String user_id, Integer status) {
		String sql = "select count(order_id) as num from orders where user_id = ?";
		List<Object> params = new ArrayList<>();
		params.add(user_id);
		if(status >= 0) {
			sql += " and status = ?";
			params.add(status);
		}else {
			sql+= " and status != 4";
		}
		try {
			return queryRunner.query(sql, new ColumnListHandler<Long>("num"), params.toArray()).get(0).intValue();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<ActionOrder> findOrders(String user_id, Integer status, int startIndex, int pageSize) {
		String sql = "select * from orders where user_id = ?";
		List<Object> params = new ArrayList<>();
		params.add(user_id);
		if(status>=0) {
			sql+=" and status = ?";
			params.add(status);
		}else {
			sql+= " and status != 4";
		}
		sql += " order by create_time desc limit ?,?";
		params.add(startIndex);
		params.add(pageSize);
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionOrder>(ActionOrder.class), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int insertOrder(ActionOrder order) {
		String sql = "insert into orders(order_id, user_id, addr_id, status, amount, create_time, pay_time, delivery_time, finish_time)"
				+ " values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] params = {order.getOrder_id(),order.getUser_id(), order.getAddr_id(), order.getStatus(), order.getAmount(),
				order.getCreate_time(), order.getPay_time(), order.getDelivery_time(), order.getFinish_time()};
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public ActionOrder findOrderByUserAndOrderId(String user_id, String orderId) {
		String sql = "select * from orders where user_id = ? and order_id = ?";
		try {
			return queryRunner.query(sql, new BeanHandler<ActionOrder>(ActionOrder.class), user_id, orderId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public int updateOrder(ActionOrder updateOrder) {
		String sql = "update orders set order_id = ?";
		List<Object> params = new ArrayList<>();
		params.add(updateOrder.getOrder_id());
		if(!StringUtils.isEmpty(updateOrder.getUser_id())) {
			sql += ", user_id = ?";
			params.add(updateOrder.getUser_id());
		}
		if(updateOrder.getAddr_id()!=null) {
			sql += ", addr_id = ?";
			params.add(updateOrder.getAddr_id());
		}
		if(updateOrder.getStatus()!=null) {
			sql += ", status = ?";
			params.add(updateOrder.getStatus());
		}
		if(updateOrder.getAmount()!=null) {
			sql += ", amount = ?";
			params.add(updateOrder.getAmount());
		}
		if(updateOrder.getPay_time()!=null) {
			sql += ", pay_time = ?";
			params.add(updateOrder.getPay_time());
		}
		if(updateOrder.getDelivery_time()!=null) {
			sql += ", delivery_time = ?";
			params.add(updateOrder.getDelivery_time());
		}
		if(updateOrder.getFinish_time()!=null) {
			sql += ", finish_time = ?";
			params.add(updateOrder.getFinish_time());
		}
		sql += " where order_id = ?";
		params.add(updateOrder.getOrder_id());
		
		try {
			return queryRunner.update(sql, params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
