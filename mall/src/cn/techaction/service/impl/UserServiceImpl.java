package cn.techaction.service.impl;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionUserDao;
import cn.techaction.pojo.User;
import cn.techaction.service.UserService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.utils.MD5Util;
import cn.techaction.utils.TokenCache;
import cn.techaction.vo.ActionUserVo;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private ActionUserDao actionUserDao;

	@Override
	public SverResponse<User> doLogin(String user_id, String password) {
		// TODO Auto-generated method stub
		//判断用户是否存在
		int rs = actionUserDao.checkUserByUserId(user_id);
		if(rs==0) {
			return SverResponse.createByErrorMessage("用户名不存在！");
		}
		//根据用户的用户名和密码查找用户
		String md5Pwd = MD5Util.MD5Encode(password, "utf-8", false);
		User user = actionUserDao.findUserByAccountAndPassword(user_id, md5Pwd);
		if(user==null) {
			return SverResponse.createByErrorMessage("密码错误！");
		}
		//将密码置空，防止泄密
		user.setPassword(StringUtils.EMPTY);
		return SverResponse.createRespBySuccess("登录成功！", user);
	}

	@Override
	public SverResponse<String> doRegister(User user) {
		// TODO Auto-generated method stub
		//检查用户名是否存在
		SverResponse<String> response = checkValidation(user.getUser_id(), ConstUtil.TYPE_ACCOUNT);
		if(!response.isSuccess()) {
			return response;
		}
		//检查邮箱是否被注册
		response = checkValidation(user.getEmail(), ConstUtil.TYPE_EMAIL);
		if(!response.isSuccess()) {
			return response;
		}
		//检查手机号码是否被注册
		response = checkValidation(user.getPhone(), ConstUtil.TYPE_PHONE);
		if(!response.isSuccess()) {
			return response;
		}
		
		//密码加密
		user.setPassword(MD5Util.MD5Encode(user.getPassword(), "utf-8", false));
		//执行注册
		int rs = actionUserDao.InsertUser(user);
		if(rs==0) {
			return SverResponse.createByErrorMessage("注册失败！");
		}
		return SverResponse.createRespBySuccessMessage("注册成功！");
	}

	@Override
	public SverResponse<String> checkValidation(String str, String type) {
		// TODO Auto-generated method stub
		//判断我们的字符串不为空type
		if(StringUtils.isNotBlank(type)) {
			if(ConstUtil.TYPE_ACCOUNT.equals(type)) {
				int rs = actionUserDao.checkUserByUserId(str);
				if(rs>0) {
					return SverResponse.createByErrorMessage("用户名已经存在！");
				}
			}
			if(ConstUtil.TYPE_EMAIL.equals(type)) {
				int rs = actionUserDao.checkUserByEmail(str);
				if(rs>0) {
					return SverResponse.createByErrorMessage("Email已经存在！");
				}
			}
			if(ConstUtil.TYPE_PHONE.equals(type)) {
				int rs = actionUserDao.checkUserPhone(str);
				if(rs>0) {
					return SverResponse.createByErrorMessage("手机号码已经存在！");
				}
			}
		}else {
			return SverResponse.createByErrorMessage("信息类别验证错误！");
		}
		return SverResponse.createRespBySuccessMessage("信息验证成功！");
	}

	@Override
	public SverResponse<User> findUserByUserId(String user_id) {
		// TODO Auto-generated method stub
		//通过用户名查找到用户
		User user = actionUserDao.findUserByUserId(user_id);
		if(user==null) {
			return SverResponse.createByErrorMessage("用户名错误！");
		}
		//将密码置空
		user.setPassword(StringUtils.EMPTY);
		//将安全问题答案置空
		user.setAsw(StringUtils.EMPTY);
		return SverResponse.createRespBySuccess(user);
	}

	@Override
	public SverResponse<String> checkUserAnswer(String user_id, String question, String asw) {
		// TODO Auto-generated method stub
		//获取校验的结果
		int rs = actionUserDao.checkUserAnswer(user_id, question, asw);
		if(rs>0) {
			//答案正确，生成token
			String token = UUID.randomUUID().toString();
			//放入缓存
			TokenCache.setCacheData(TokenCache.PREFIX+user_id, token);
			return SverResponse.createRespBySuccessMessage(token);
		}
		return SverResponse.createByErrorMessage("问题答案错误！");
	}

	@Override
	public SverResponse<String> resetPassword(String user_id, String password) {
		// TODO Auto-generated method stub
		//密码加密
		String md5Pwd = MD5Util.MD5Encode(password, "utf-8", false);
		//获得User对象
		User user = actionUserDao.findUserByUserId(user_id);
		//更新密码
		user.setPassword(md5Pwd);
		int rs = actionUserDao.updateUserInfo(user);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("密码修改成功！");
		}
		return SverResponse.createByErrorMessage("密码修改失败！");
	}

	@Override
	public SverResponse<User> updateUserInfo(ActionUserVo userVo) {
		//根据user_id获得我们的user对象
		User updateUser = actionUserDao.findUserByUserId(userVo.getUser_id());
		//更新修改我们的数据
		updateUser.setUser_id(userVo.getUser_id());
		updateUser.setEmail(userVo.getEmail());
		updateUser.setPhone(userVo.getPhone());
		updateUser.setAge(userVo.getAge());
		updateUser.setName(userVo.getName());
		//判断男女
		if(userVo.getSex().equals("男")) {
			updateUser.setSex(1);
		}else {
			updateUser.setSex(0);
		}
		int rs = actionUserDao.updateUserInfo(updateUser);
		if(rs>0) {
			return SverResponse.createRespBySuccess("用户信息修改成功", updateUser);
		}
		return SverResponse.createByErrorMessage("用户信息修改失败！");
	}

	@Override
	public SverResponse<String> updatePassword(User user, String newPassword, String oldPassword) {
		//防止越权，检测旧密码是否正确
		oldPassword = MD5Util.MD5Encode(oldPassword, "utf-8", false);
		int rs = actionUserDao.checkPassword(user.getUser_id(), oldPassword);
		if(rs==0) {
			return SverResponse.createByErrorMessage("原始密码错误！");
		}
		//将新密码插入数据库
		newPassword = MD5Util.MD5Encode(newPassword, "utf-8", false);
		user.setPassword(newPassword);
		rs = actionUserDao.updateUserInfo(user);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("密码修改成功！");
		}
		return SverResponse.createByErrorMessage("密码修改失败！");
	}
}
