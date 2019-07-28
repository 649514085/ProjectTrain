package cn.techaction.service;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.vo.ActionUserVo;

public interface UserService {
	/**
	 * �û���¼
	 * @param user_id
	 * @param password
	 * @return
	 */
	public SverResponse<User> doLogin(String user_id, String password);
	
	/**
	 * �û�ע��
	 * @param user
	 * @return
	 */
	public SverResponse<String> doRegister(User user);
	
	/**
	 * ��ϢУ����֤
	 * @param str
	 * @param type
	 * @return
	 */
	public SverResponse<String> checkValidation(String str, String type);
	
	/**
	 * �����û�������û�����
	 * @param user_id
	 * @return
	 */
	public SverResponse<User> findUserByUserId(String user_id);
	
	/**
	 * У���û������
	 * @param user_id
	 * @param question
	 * @param asw
	 * @return
	 */
	public SverResponse<String> checkUserAnswer(String user_id, String question, String asw);
	
	/**
	 * �����û�������
	 * @param user_id
	 * @param password
	 * @return
	 */
	public SverResponse<String> resetPassword(String user_id, String password);
	
	/**
	 * �����û���Ϣ
	 * @param userVo
	 * @return
	 */
	public SverResponse<User> updateUserInfo(ActionUserVo userVo);
	
	/**
	 * ��¼�û���������
	 * @param user
	 * @param newpwd
	 * @param oldpwd
	 * @return
	 */
	public SverResponse<String> updatePassword(User user, String newPassword, String oldPassword);
	
}
