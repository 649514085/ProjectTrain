package cn.techaction.service;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.vo.ActionUserVo;

public interface UserService {
	/**
	 * 用户登录
	 * @param user_id
	 * @param password
	 * @return
	 */
	public SverResponse<User> doLogin(String user_id, String password);
	
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	public SverResponse<String> doRegister(User user);
	
	/**
	 * 信息校验验证
	 * @param str
	 * @param type
	 * @return
	 */
	public SverResponse<String> checkValidation(String str, String type);
	
	/**
	 * 根据用户名获得用户对象
	 * @param user_id
	 * @return
	 */
	public SverResponse<User> findUserByUserId(String user_id);
	
	/**
	 * 校验用户问题答案
	 * @param user_id
	 * @param question
	 * @param asw
	 * @return
	 */
	public SverResponse<String> checkUserAnswer(String user_id, String question, String asw);
	
	/**
	 * 重置用户的密码
	 * @param user_id
	 * @param password
	 * @return
	 */
	public SverResponse<String> resetPassword(String user_id, String password);
	
	/**
	 * 更新用户信息
	 * @param userVo
	 * @return
	 */
	public SverResponse<User> updateUserInfo(ActionUserVo userVo);
	
	/**
	 * 登录用户重设密码
	 * @param user
	 * @param newpwd
	 * @param oldpwd
	 * @return
	 */
	public SverResponse<String> updatePassword(User user, String newPassword, String oldPassword);
	
}
