package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.Admin;
import cn.techaction.pojo.User;

public interface ActionAdminDao {
	/**
	 * 检查管理员用户是否存在
	 * @param account
	 * @return
	 */
	public int checkAdminDaoByAccount(String account);
	
	/**
	 * 根据管理员用户名和密码找到用户
	 * @param account
	 * @param passwrd
	 * @return
	 */
	public Admin findAdminByAccountAndPassword(String account,String passwrd);
	
	/**
	 * 找到所有用户用于展示
	 * @return
	 */
	public List<User> findAllUsers();
	
	/**
	 * 找到某个用户
	 * @param user_id
	 * @return
	 */
	public List<User> findUserById(String user_id);
	public User findUserByIdOne(String user_id);
	public int updateUserInfo(User user);
	public int deleteUser(String user_id);
}
