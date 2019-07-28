package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.springframework.stereotype.Repository;

import cn.techaction.dao.ActionAdminDao;
import cn.techaction.pojo.Admin;
import cn.techaction.pojo.User;

@Repository
public class ActionAdminDaoImpl implements ActionAdminDao{
	@Resource
	private QueryRunner queryRunner;
	/**
	 * 检查管理员用户是否存在
	 */
	
	String para = "user_id, password, phone, email, sex, question, asw as answer";
	@Override
	public int checkAdminDaoByAccount(String account) {
		String sql = "select count(*) as num from administrators where account=?";
		try {
			List<Long> rs = queryRunner.query(sql, new ColumnListHandler<Long>("num"),account);
			return rs.size()>0?rs.get(0).intValue():0;
		}
		catch(SQLException e){
			e.printStackTrace();
			return 0;
		}	
	}
	
	/**
	 * 根据管理员用户名和密码找到用户，用于登录
	 */
	@Override
	public Admin findAdminByAccountAndPassword(String account,String password) {
		String sql = "select * from administrators where account=? and password=?";
		try {
			return queryRunner.query(sql, new BeanHandler<Admin>(Admin.class),account,password);
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}		
	}
	
	/**
	 * 找到所有用户，用于展示
	 */
	@Override
	public List<User> findAllUsers() {
		String sql = "select "+para+" from users";
		try {
			List<User> users = (List<User>) queryRunner.query(sql, new BeanListHandler<User>(User.class));
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 找到某个指定用户
	 */
	public List<User> findUserById(String user_id) {
		String sql = "select "+para+" from users where user_id like ?";
		try {
			List<User> users = queryRunner.query(sql, new BeanListHandler<User>(User.class), user_id);
			return users;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public User findUserByIdOne(String user_id) {
		String sql = "select "+para+" from users where user_id = ?";
		try {
			User user = queryRunner.query(sql, new BeanHandler<User>(User.class), user_id);

			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public int updateUserInfo(User user) {
		String sql = "update users set phone=?,email=?,sex=? where user_id = ?";
		List<Object> param = new ArrayList<Object>();
		param.add(user.getPhone());
		param.add(user.getEmail());
		System.out.print(user.getSex());
		param.add(user.getSex());
		param.add(user.getUser_id());
		try {
			return queryRunner.update(sql, param.toArray());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public int deleteUser(String user_id) {
		String sql = "delete from users where user_id=?";
		try {
			return queryRunner.update(sql, user_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
}
