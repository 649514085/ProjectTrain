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
		//�ж��û��Ƿ����
		int rs = actionUserDao.checkUserByUserId(user_id);
		if(rs==0) {
			return SverResponse.createByErrorMessage("�û��������ڣ�");
		}
		//�����û����û�������������û�
		String md5Pwd = MD5Util.MD5Encode(password, "utf-8", false);
		User user = actionUserDao.findUserByAccountAndPassword(user_id, md5Pwd);
		if(user==null) {
			return SverResponse.createByErrorMessage("�������");
		}
		//�������ÿգ���ֹй��
		user.setPassword(StringUtils.EMPTY);
		return SverResponse.createRespBySuccess("��¼�ɹ���", user);
	}

	@Override
	public SverResponse<String> doRegister(User user) {
		// TODO Auto-generated method stub
		//����û����Ƿ����
		SverResponse<String> response = checkValidation(user.getUser_id(), ConstUtil.TYPE_ACCOUNT);
		if(!response.isSuccess()) {
			return response;
		}
		//��������Ƿ�ע��
		response = checkValidation(user.getEmail(), ConstUtil.TYPE_EMAIL);
		if(!response.isSuccess()) {
			return response;
		}
		//����ֻ������Ƿ�ע��
		response = checkValidation(user.getPhone(), ConstUtil.TYPE_PHONE);
		if(!response.isSuccess()) {
			return response;
		}
		
		//�������
		user.setPassword(MD5Util.MD5Encode(user.getPassword(), "utf-8", false));
		//ִ��ע��
		int rs = actionUserDao.InsertUser(user);
		if(rs==0) {
			return SverResponse.createByErrorMessage("ע��ʧ�ܣ�");
		}
		return SverResponse.createRespBySuccessMessage("ע��ɹ���");
	}

	@Override
	public SverResponse<String> checkValidation(String str, String type) {
		// TODO Auto-generated method stub
		//�ж����ǵ��ַ�����Ϊ��type
		if(StringUtils.isNotBlank(type)) {
			if(ConstUtil.TYPE_ACCOUNT.equals(type)) {
				int rs = actionUserDao.checkUserByUserId(str);
				if(rs>0) {
					return SverResponse.createByErrorMessage("�û����Ѿ����ڣ�");
				}
			}
			if(ConstUtil.TYPE_EMAIL.equals(type)) {
				int rs = actionUserDao.checkUserByEmail(str);
				if(rs>0) {
					return SverResponse.createByErrorMessage("Email�Ѿ����ڣ�");
				}
			}
			if(ConstUtil.TYPE_PHONE.equals(type)) {
				int rs = actionUserDao.checkUserPhone(str);
				if(rs>0) {
					return SverResponse.createByErrorMessage("�ֻ������Ѿ����ڣ�");
				}
			}
		}else {
			return SverResponse.createByErrorMessage("��Ϣ�����֤����");
		}
		return SverResponse.createRespBySuccessMessage("��Ϣ��֤�ɹ���");
	}

	@Override
	public SverResponse<User> findUserByUserId(String user_id) {
		// TODO Auto-generated method stub
		//ͨ���û������ҵ��û�
		User user = actionUserDao.findUserByUserId(user_id);
		if(user==null) {
			return SverResponse.createByErrorMessage("�û�������");
		}
		//�������ÿ�
		user.setPassword(StringUtils.EMPTY);
		//����ȫ������ÿ�
		user.setAsw(StringUtils.EMPTY);
		return SverResponse.createRespBySuccess(user);
	}

	@Override
	public SverResponse<String> checkUserAnswer(String user_id, String question, String asw) {
		// TODO Auto-generated method stub
		//��ȡУ��Ľ��
		int rs = actionUserDao.checkUserAnswer(user_id, question, asw);
		if(rs>0) {
			//����ȷ������token
			String token = UUID.randomUUID().toString();
			//���뻺��
			TokenCache.setCacheData(TokenCache.PREFIX+user_id, token);
			return SverResponse.createRespBySuccessMessage(token);
		}
		return SverResponse.createByErrorMessage("����𰸴���");
	}

	@Override
	public SverResponse<String> resetPassword(String user_id, String password) {
		// TODO Auto-generated method stub
		//�������
		String md5Pwd = MD5Util.MD5Encode(password, "utf-8", false);
		//���User����
		User user = actionUserDao.findUserByUserId(user_id);
		//��������
		user.setPassword(md5Pwd);
		int rs = actionUserDao.updateUserInfo(user);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("�����޸ĳɹ���");
		}
		return SverResponse.createByErrorMessage("�����޸�ʧ�ܣ�");
	}

	@Override
	public SverResponse<User> updateUserInfo(ActionUserVo userVo) {
		//����user_id������ǵ�user����
		User updateUser = actionUserDao.findUserByUserId(userVo.getUser_id());
		//�����޸����ǵ�����
		updateUser.setUser_id(userVo.getUser_id());
		updateUser.setEmail(userVo.getEmail());
		updateUser.setPhone(userVo.getPhone());
		updateUser.setAge(userVo.getAge());
		updateUser.setName(userVo.getName());
		//�ж���Ů
		if(userVo.getSex().equals("��")) {
			updateUser.setSex(1);
		}else {
			updateUser.setSex(0);
		}
		int rs = actionUserDao.updateUserInfo(updateUser);
		if(rs>0) {
			return SverResponse.createRespBySuccess("�û���Ϣ�޸ĳɹ�", updateUser);
		}
		return SverResponse.createByErrorMessage("�û���Ϣ�޸�ʧ�ܣ�");
	}

	@Override
	public SverResponse<String> updatePassword(User user, String newPassword, String oldPassword) {
		//��ֹԽȨ�����������Ƿ���ȷ
		oldPassword = MD5Util.MD5Encode(oldPassword, "utf-8", false);
		int rs = actionUserDao.checkPassword(user.getUser_id(), oldPassword);
		if(rs==0) {
			return SverResponse.createByErrorMessage("ԭʼ�������");
		}
		//��������������ݿ�
		newPassword = MD5Util.MD5Encode(newPassword, "utf-8", false);
		user.setPassword(newPassword);
		rs = actionUserDao.updateUserInfo(user);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("�����޸ĳɹ���");
		}
		return SverResponse.createByErrorMessage("�����޸�ʧ�ܣ�");
	}
}
