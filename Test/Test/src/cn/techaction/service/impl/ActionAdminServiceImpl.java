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
	 * ����Ա��¼
	 */
	@Override
	public SverResponse<Admin> doLogin(String account, String password) {
		//ͨ���û����жϹ���Ա�û��Ƿ����
		int rs = actionAdminDao.checkAdminDaoByAccount(account);
		//�������ڣ���ʾ��Ϣ
		if (rs==0) {
			return SverResponse.createByErrorMessage("�û�������");
		}
		//�����ڣ�ͨ���û�����������ҹ���Ա�û�
		String md5pwd = MD5Util.MD5Encode(password, "UTF-8", false);
		//System.out.print("���룺"+md5pwd);
		Admin admin = actionAdminDao.findAdminByAccountAndPassword(account, md5pwd);
		if (admin==null) {			
			return SverResponse.createByErrorMessage("�������");
		}
		admin.setPassword(StringUtils.EMPTY);
		return SverResponse.createRespBySuccess("��¼�ɹ���", admin);
	}
	
	/**
	 * �û�������-�û�չʾ-���������û�
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
	 * �û�������-�û�չʾ-�����û�
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
	 * �û�������-�����û���Ϣ
	 */
	public SverResponse<String> updateUserInfo(ActionUserVo userVo){
		User user = userVoToUser(userVo);
		
		int rs = actionAdminDao.updateUserInfo(user);
		if (rs>0) {
			return SverResponse.createRespBySuccess("�û���Ϣ�޸ĳɹ���");
		}
		return SverResponse.createByErrorMessage("�û���Ϣ�޸�ʧ�ܣ�");
	}
	
	/**
	 * �û�������-ɾ���û�
	 */
	public SverResponse<String> deleteUser(String user_id){
		//�����û������ж���
		List<Order> orders = orderDao.findOrderByUid(user_id);
		//���û��ж���������ɾ���û�
		if (orders.size()>0) {
			return SverResponse.createByErrorMessage("�û����ڹ����Ķ����޷�ɾ����");
		}
		else {
			int rs = actionAdminDao.deleteUser(user_id);
			if (rs>0) {
				return SverResponse.createRespBySuccess("�û�ɾ���ɹ���");
			}
			return SverResponse.createByErrorMessage("�û�ɾ��ʧ�ܣ�");
		}
	}
	
	public ActionUserVo userToUserVo(User user) {
		ActionUserVo uservo = new ActionUserVo();
		uservo.setUser_id(user.getUser_id());
		if (user.getSex()==1) {
			uservo.setSex("��");
		}else {
			uservo.setSex("Ů");
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
		if (userVo.getSex().equals("��")) {
			user.setSex(1);
		}
		if (userVo.getSex().equals("Ů")) {
			user.setSex(0);
		}
		return user;
	}
}
