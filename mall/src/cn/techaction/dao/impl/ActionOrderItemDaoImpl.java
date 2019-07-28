package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;
import cn.techaction.dao.ActionOrderItemDao;
import cn.techaction.pojo.ActionOrderItem;

@Repository
public class ActionOrderItemDaoImpl implements ActionOrderItemDao {
	
	@Resource
	QueryRunner queryRunner;

	@Override
	public List<ActionOrderItem> getItemsByOrderId(String order_id) {
		String sql = "select * from order_items where order_id = ?";
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionOrderItem>(ActionOrderItem.class), order_id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int[] batchInsert(List<ActionOrderItem> orderItems) {
		String sql = "insert into order_items(order_id, product_id, quantity, price, product_name) values(?, ?, ?, ?, ?)";
		Object[][] params = new Object[orderItems.size()][];
		for(int i = 0;i<orderItems.size();i++) {
			ActionOrderItem item = orderItems.get(i);
			params[i] = new Object[] {
				item.getOrder_id(), item.getProduct_id(), item.getQuantity(), item.getPrice(), item.getProduct_name()
			};
		}
		try {
			return queryRunner.batch(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
