package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.springframework.stereotype.Repository;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.regexp.internal.recompile;

import cn.techaction.dao.ActionUserDao;
import cn.techaction.pojo.User;
import javafx.scene.Parent;
@Repository
public class ActionUserDaoImpl implements ActionUserDao {

	@Resource
	private QueryRunner queryRunner;

	@Override
	public int checkUserByUserId(String user_id) {
		String sql = "select count(user_id) as num from users where user_id = ?";
		try {
			List<Long> rs = queryRunner.query(sql, new ColumnListHandler<Long>("num"), user_id);
			return rs.size()>0?rs.get(0).intValue():0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public User findUserByAccountAndPassword(String user_id, String password) {
		String sql = "select * from users where user_id = ? and password = ?";
		try {
			return queryRunner.query(sql, new BeanHandler<User>(User.class), user_id, password);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int checkUserByEmail(String email) {
		String sql = "select count(user_id) as num from users where email = ?";
		try {
			List<Long> rs = queryRunner.query(sql, new ColumnListHandler<Long>("num"), email);
			return rs.size()>0?rs.get(0).intValue():0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int checkUserPhone(String phone) {
		String sql = "select count(user_id) as num from users where phone = ?";
		try {
			List<Long> rs = queryRunner.query(sql, new ColumnListHandler<Long>("num"), phone);
			return rs.size()>0?rs.get(0).intValue():0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int InsertUser(User user) {
		String sql = "insert into users(user_id, password, email, phone, question, asw) "
				+ "values(?, ?, ?, ?, ?, ?)";
		Object[] params = {user.getUser_id(), user.getPassword(), user.getEmail(), user.getPhone(), user.getQuestion(), user.getAsw()};
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public User findUserByUserId(String user_id) {
		String sql = "select * from users where user_id = ?";
		try {
			return queryRunner.query(sql, new BeanHandler<User>(User.class), user_id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int checkUserAnswer(String user_id, String question, String asw) {
		String sql = "select count(user_id) as num from users where "
				+ "user_id = ? and question = ? and asw = ?";
		System.out.println(user_id+question+asw);
		try {
			List<Long> rs = queryRunner.query(sql, new ColumnListHandler<Long>("num"), user_id, question, asw);
			return rs.size()>0?rs.get(0).intValue():0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateUserInfo(User user) {
		String sql = "update users set password = ?, email = ?, phone = ?, sex = ?, question = ?, asw = ?, age = ?, name = ?"
				+ " where user_id = ?";
		List<Object> params = new ArrayList<>();
		params.add(user.getPassword());
		params.add(user.getEmail());
		params.add(user.getPhone());
		params.add(user.getSex());
		params.add(user.getQuestion());
		params.add(user.getAsw());
		params.add(user.getAge());
		params.add(user.getName());
		params.add(user.getUser_id());
		try {
			return queryRunner.update(sql, params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}	
	}

	@Override
	public int checkPassword(String user_id, String oldPassword) {
		String sql = "select count(user_id) as num from users where user_id = ? and password = ?";
		try {
			List<Long> rs = queryRunner.query(sql, new ColumnListHandler<Long>("num"), user_id, oldPassword);
			return rs.size()>0?rs.get(0).intValue():0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
