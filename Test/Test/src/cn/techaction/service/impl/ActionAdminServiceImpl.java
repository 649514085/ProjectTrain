package cn.techaction.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.techaction.service.ActionAdminService;
import cn.techaction.utils.MD5Util;
import cn.techaction.vo.ActionUserVo;
import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionAdminDao;
import cn.techaction.dao.ActionOrderDao;
import cn.techaction.pojo.Admin;
import cn.techaction.pojo.Order;
import cn.techaction.pojo.User;


@Service
public class ActionAdminServiceImpl implements ActionAdminService{

	@Autowired
	private ActionAdminDao actionAdminDao; 
	@Autowired
	private ActionOrderDao orderDao;
	/**
	 * 管理员登录
	 */
	@Override
	public SverResponse<Admin> doLogin(String account, String password) {
		//通过用户名判断管理员用户是否存在
		int rs = actionAdminDao.checkAdminDaoByAccount(account);
		//若不存在，提示消息
		if (rs==0) {
			return SverResponse.createByErrorMessage("用户不存在");
		}
		//若存在，通过用户名和密码查找管理员用户
		String md5pwd = MD5Util.MD5Encode(password, "UTF-8", false);
		//System.out.print("密码："+md5pwd);
		Admin admin = actionAdminDao.findAdminByAccountAndPassword(account, md5pwd);
		if (admin==null) {			
			return SverResponse.createByErrorMessage("密码错误！");
		}
		admin.setPassword(StringUtils.EMPTY);
		return SverResponse.createRespBySuccess("登录成功！", admin);
	}
	
	/**
	 * 用户管理功能-用户展示-查找所有用户
	 */
	public SverResponse<List<ActionUserVo>> findUserList(){
		List<User> users = actionAdminDao.findAllUsers();
		List<ActionUserVo> vos = Lists.newArrayList();
		//System.out.print(users.toString());
		for (User u : users) {
			vos.add(userToUserVo(u));
		}
		return SverResponse.createRespBySuccess(vos);
	}
	
	/**
	 * 用户管理功能-用户展示-查找用户
	 */
	public SverResponse<List<ActionUserVo>>  findUser(String user_id){
		String sql="";
		List<User> users = null;
		if (user_id!="") {
			sql = "%"+user_id+"%";
			System.out.print(sql);
		}
		users = actionAdminDao.findUserById(sql);
		List<ActionUserVo> vos = Lists.newArrayList();
		if (users.size()>0) {
			for (User u : users) {
				vos.add(userToUserVo(u));
			}
		}
		return SverResponse.createRespBySuccess(vos);
	}
	
	public SverResponse<ActionUserVo> findUserOne(String user_id){
		User user = actionAdminDao.findUserByIdOne(user_id);
		ActionUserVo userVo = userToUserVo(user);
		return SverResponse.createRespBySuccess(userVo);
	}
	
	/**
	 * 用户管理功能-更新用户信息
	 */
	public SverResponse<String> updateUserInfo(ActionUserVo userVo){
		User user = userVoToUser(userVo);
		
		int rs = actionAdminDao.updateUserInfo(user);
		if (rs>0) {
			return SverResponse.createRespBySuccess("用户信息修改成功！");
		}
		return SverResponse.createByErrorMessage("用户信息修改失败！");
	}
	
	/**
	 * 用户管理功能-删除用户
	 */
	public SverResponse<String> deleteUser(String user_id){
		//查找用户的所有订单
		List<Order> orders = orderDao.findOrderByUid(user_id);
		//若用户有订单，则不能删除用户
		if (orders.size()>0) {
			return SverResponse.createByErrorMessage("用户存在关联的订单无法删除！");
		}
		else {
			int rs = actionAdminDao.deleteUser(user_id);
			if (rs>0) {
				return SverResponse.createRespBySuccess("用户删除成功！");
			}
			return SverResponse.createByErrorMessage("用户删除失败！");
		}
	}
	
	public ActionUserVo userToUserVo(User user) {
		ActionUserVo uservo = new ActionUserVo();
		uservo.setUser_id(user.getUser_id());
		if (user.getSex()==1) {
			uservo.setSex("男");
		}else {
			uservo.setSex("女");
		}
		uservo.setPhone(user.getPhone());
		uservo.setEmail(user.getEmail());
		return uservo;
	}
	
	public User userVoToUser(ActionUserVo userVo) {
		User user = new User();
		user.setUser_id(userVo.getUser_id());
		user.setEmail(userVo.getEmail());
		user.setPhone(userVo.getPhone());
		if (userVo.getSex().equals("男")) {
			user.setSex(1);
		}
		if (userVo.getSex().equals("女")) {
			user.setSex(0);
		}
		return user;
	}
}
