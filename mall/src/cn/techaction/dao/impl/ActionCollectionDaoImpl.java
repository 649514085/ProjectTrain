package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionCollectionDao;
import cn.techaction.pojo.ActionCollection;
import cn.techaction.pojo.ActionProduct;

@Repository
public class ActionCollectionDaoImpl implements ActionCollectionDao {

	@Autowired
	QueryRunner queryRunner;
	
	@Override
	public int insertOneCollection(String userId, String productId) {
		String sql = "insert into collects(user_id, product_id) values(?, ?)";
		try {
			return queryRunner.update(sql, userId, productId);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int findCollections(String userId, String productId) {
		String sql = "select count(*) as num from collects where user_id = ? and product_id = ?";
		try {
			List<Long> rs = queryRunner.query(sql, new ColumnListHandler<Long>("num"), userId, productId);
			return rs.size()>0?rs.get(0).intValue():0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<ActionCollection> findCollectionsByUserId(String user_id) {
		String sql = "select * from collects where user_id = ?";
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionCollection>(ActionCollection.class), user_id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int deleteOneCollection(String user_id, String productId) {
		String sql = "delete from collects where user_id = ? and product_id = ?";
		try {
			return queryRunner.update(sql, user_id, productId);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
