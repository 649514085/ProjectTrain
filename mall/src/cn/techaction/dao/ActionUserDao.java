package cn.techaction.dao;

import cn.techaction.pojo.User;

public interface ActionUserDao {
	/**
	 * 根据user_id判断用户是否存在
	 * @param user_id
	 * @return
	 */
	public int checkUserByUserId(String user_id);
	/**
	 * 根据用户名和密码查找用户
	 * @param user_id
	 * @param md5Pwd
	 * @return
	 */
	public User findUserByAccountAndPassword(String user_id, String password);
	
	/**
	 * 验证电子邮箱是否已经被注册
	 * @param str
	 * @return
	 */
	public int checkUserByEmail(String email);
	
	/**
	 * 验证手机是否已经被注册
	 * @param str
	 * @return
	 */
	public int checkUserPhone(String phone);
	
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	public int InsertUser(User user);
	
	/**
	 * 根据用户名查找用户
	 * @param user_id
	 * @return
	 */
	public User findUserByUserId(String user_id);
	
	/**
	 * 校验用户检查密码问题的答案
	 * @param user_id
	 * @param question
	 * @param asw
	 * @return
	 */
	public int checkUserAnswer(String user_id, String question, String asw);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);
	
	/**
	 * 验证用户密码是否已经存在且正确
	 * @param user_id
	 * @param oldPassword
	 * @return
	 */
	public int checkPassword(String user_id, String oldPassword);

}
