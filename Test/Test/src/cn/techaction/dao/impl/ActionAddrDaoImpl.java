package cn.techaction.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sun.org.apache.bcel.internal.generic.NEW;

import cn.techaction.dao.ActionAddrDao;
import cn.techaction.pojo.Addrs;

@Repository
public class ActionAddrDaoImpl implements ActionAddrDao{

	@Autowired
	private QueryRunner queryRunner;
	
	@Override
	public Addrs findAddrs(Integer addr_id) {
		String sql = "select * from addrs where addr_id=?";
		try {
			return queryRunner.query(sql, new BeanHandler<Addrs>(Addrs.class), addr_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
