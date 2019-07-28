package cn.techaction.dao;

import cn.techaction.pojo.User;

public interface ActionUserDao {
	/**
	 * ����user_id�ж��û��Ƿ����
	 * @param user_id
	 * @return
	 */
	public int checkUserByUserId(String user_id);
	/**
	 * �����û�������������û�
	 * @param user_id
	 * @param md5Pwd
	 * @return
	 */
	public User findUserByAccountAndPassword(String user_id, String password);
	
	/**
	 * ��֤���������Ƿ��Ѿ���ע��
	 * @param str
	 * @return
	 */
	public int checkUserByEmail(String email);
	
	/**
	 * ��֤�ֻ��Ƿ��Ѿ���ע��
	 * @param str
	 * @return
	 */
	public int checkUserPhone(String phone);
	
	/**
	 * �����û�
	 * @param user
	 * @return
	 */
	public int InsertUser(User user);
	
	/**
	 * �����û��������û�
	 * @param user_id
	 * @return
	 */
	public User findUserByUserId(String user_id);
	
	/**
	 * У���û������������Ĵ�
	 * @param user_id
	 * @param question
	 * @param asw
	 * @return
	 */
	public int checkUserAnswer(String user_id, String question, String asw);
	
	/**
	 * �����û���Ϣ
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);
	
	/**
	 * ��֤�û������Ƿ��Ѿ���������ȷ
	 * @param user_id
	 * @param oldPassword
	 * @return
	 */
	public int checkPassword(String user_id, String oldPassword);

}
