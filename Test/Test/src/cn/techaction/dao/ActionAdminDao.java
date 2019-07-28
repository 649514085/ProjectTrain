package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.Admin;
import cn.techaction.pojo.User;

public interface ActionAdminDao {
	/**
	 * ������Ա�û��Ƿ����
	 * @param account
	 * @return
	 */
	public int checkAdminDaoByAccount(String account);
	
	/**
	 * ���ݹ���Ա�û����������ҵ��û�
	 * @param account
	 * @param passwrd
	 * @return
	 */
	public Admin findAdminByAccountAndPassword(String account,String passwrd);
	
	/**
	 * �ҵ������û�����չʾ
	 * @return
	 */
	public List<User> findAllUsers();
	
	/**
	 * �ҵ�ĳ���û�
	 * @param user_id
	 * @return
	 */
	public List<User> findUserById(String user_id);
	public User findUserByIdOne(String user_id);
	public int updateUserInfo(User user);
	public int deleteUser(String user_id);
}
