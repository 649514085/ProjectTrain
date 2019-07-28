package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sun.org.apache.regexp.internal.recompile;

import cn.techaction.dao.ActionCartDao;
import cn.techaction.pojo.ActionCart;

@Repository
public class ActionCartDaoImpl implements ActionCartDao {
	
	@Autowired
	QueryRunner queryRunner;

	@Override
	public ActionCart findCartByUserAndProductId(String userId, String productId) {
		String sql = "select * from carts where user_id = ? and product_id = ?";
		try {
			return queryRunner.query(sql, new BeanHandler<ActionCart>(ActionCart.class), userId, productId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int insertCart(ActionCart cart) {
		String sql = "insert into carts(user_id, product_id, product_name, quantity) values(?, ?, ?, ?)";
		Object[] params = {cart.getUser_id(), cart.getProduct_id(), cart.getProduct_name(), cart.getQuantity()};
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateCart(ActionCart actionCart) {
		String sql = "update carts set quantity = ? where user_id = ? and product_id = ?";
		Object[] params = {actionCart.getQuantity(), actionCart.getUser_id(), actionCart.getProduct_id()};
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<ActionCart> findCartByUserId(String user_id) {
		String sql = "select * from carts where user_id = ?";
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionCart>(ActionCart.class), user_id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int deleteCartByUserId(String user_id) {
		String sql = "delete from carts where user_id = ?";
		try {
			return queryRunner.update(sql, user_id);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateCartByUserAndProductId(ActionCart actionCart) {
		String sql = "update carts set quantity = ?";
		List<Object> params = new ArrayList<>();
		params.add(actionCart.getQuantity());
		if(actionCart.getChecked()!=null) {
			sql += " ,checked = ?";
			params.add(actionCart.getChecked());
		}
		sql += " where user_id = ? and product_id = ?";
		params.add(actionCart.getUser_id());
		params.add(actionCart.getProduct_id());
		try {
			return queryRunner.update(sql, params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int deleteCart(String user_id, String productId) {
		String sql = "delete from carts where user_id = ? and product_id = ?";
		try {
			return queryRunner.update(sql, user_id, productId);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int getCartsCountByUserId(String user_id) {
		String sql = "select count(*) as num from carts where user_id = ?";
		try {
			List<Long> rs = queryRunner.query(sql, new ColumnListHandler<Long>("num"), user_id);
			return rs.size()>0?rs.get(0).intValue():0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<ActionCart> findCheckedCartsByUserId(String user_id) {
		String sql = "select * from carts where user_id = ? and checked = 1";
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionCart>(ActionCart.class), user_id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int deleteCheckedCartsByUserId(String user_id) {
		String sql = "delete from carts where user_id = ? and checked = 1";
		try {
			return queryRunner.update(sql, user_id);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int clearCheckedStatus(String user_id) {
		String sql = "update carts set checked = 0 where user_id = ?";
		try {
			return queryRunner.update(sql, user_id);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
