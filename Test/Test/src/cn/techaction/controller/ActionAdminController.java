package cn.techaction.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.catalina.LifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.ResponseCode;
import cn.techaction.common.SverResponse;
import cn.techaction.pojo.Admin;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionAdminService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.vo.ActionUserVo;

@Controller
@RequestMapping("/mgr/admin")
public class ActionAdminController {
	@Autowired
	private ActionAdminService AdminService;
	
	/**
	 * ����Ա��¼
	 * @param session
	 * @param account
	 * @param password
	 * @return
	 */
	@RequestMapping("/login.do")
	@ResponseBody
	public SverResponse<Admin> doLogin(HttpSession session,String account,String password) {
		//ͨ������Service��ķ������ж��Ƿ��ܵ�¼
		SverResponse<Admin> response = AdminService.doLogin(account, password);
		//���ܵ�¼������Ϣ����session��
		if (response.isSuccess()) {
			session.setAttribute(ConstUtil.CUR_USER, response.getData());
			return response;
		}
		//�����ܵ�¼����Ҫ������ʾ
		else {
			return response;
		}
	}
	
	/**
	 * չʾ�����û�
	 * @param session
	 * @return
	 */
	@RequestMapping("/finduserlist.do")
	@ResponseBody
	public SverResponse<List<ActionUserVo>> getUserDetail(HttpSession session) {
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//δ��¼
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "���¼���ٽ��в���");
		}
		//�ѵ�¼
		else {
			return AdminService.findUserList();
		}
	}
	
	/**
	 * ģ����ѯ�������û�ID�ҵ��û�
	 * @param session
	 * @return
	 */
	@RequestMapping("/finduser.do")
	@ResponseBody
	public SverResponse<List<ActionUserVo>> findUser(HttpSession session,String user_id) {
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//δ��¼
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "���¼���ٽ��в���");
		}
		//�ѵ�¼
		else {
			return AdminService.findUser(user_id);
		}
	}
	
	@RequestMapping("/finduserone.do")
	@ResponseBody
	public SverResponse<ActionUserVo> findUserOne(HttpSession session,String user_id) {
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//δ��¼
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "���¼���ٽ��в���");
		}
		//�ѵ�¼
		else {
			return AdminService.findUserOne(user_id);
		}
	}
	
	/**
	 * �����û���Ϣ
	 * @param session
	 * @return
	 */
	@RequestMapping("/updateuser.do")
	@ResponseBody
	public SverResponse<String> updateUser(HttpSession session, ActionUserVo userVo) {
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//δ��¼
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "���¼���ٽ��в���");
		}
		//�ѵ�¼
		else {
			return AdminService.updateUserInfo(userVo);
		}
	}
	
	@RequestMapping("/deleteuser.do")
	@ResponseBody
	public SverResponse<String> deleteUser(HttpSession session,String user_id) {
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//δ��¼
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "���¼���ٽ��в���");
		}
		//�ѵ�¼
		else {
			return AdminService.deleteUser(user_id);
		}
	}
}
