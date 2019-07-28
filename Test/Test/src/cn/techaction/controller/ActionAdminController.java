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
	 * 管理员登录
	 * @param session
	 * @param account
	 * @param password
	 * @return
	 */
	@RequestMapping("/login.do")
	@ResponseBody
	public SverResponse<Admin> doLogin(HttpSession session,String account,String password) {
		//通过调用Service层的方法，判断是否能登录
		SverResponse<Admin> response = AdminService.doLogin(account, password);
		//若能登录，则将信息放在session中
		if (response.isSuccess()) {
			session.setAttribute(ConstUtil.CUR_USER, response.getData());
			return response;
		}
		//若不能登录，则要给出提示
		else {
			return response;
		}
	}
	
	/**
	 * 展示所有用户
	 * @param session
	 * @return
	 */
	@RequestMapping("/finduserlist.do")
	@ResponseBody
	public SverResponse<List<ActionUserVo>> getUserDetail(HttpSession session) {
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return AdminService.findUserList();
		}
	}
	
	/**
	 * 模糊查询：根据用户ID找到用户
	 * @param session
	 * @return
	 */
	@RequestMapping("/finduser.do")
	@ResponseBody
	public SverResponse<List<ActionUserVo>> findUser(HttpSession session,String user_id) {
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return AdminService.findUser(user_id);
		}
	}
	
	@RequestMapping("/finduserone.do")
	@ResponseBody
	public SverResponse<ActionUserVo> findUserOne(HttpSession session,String user_id) {
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return AdminService.findUserOne(user_id);
		}
	}
	
	/**
	 * 更新用户信息
	 * @param session
	 * @return
	 */
	@RequestMapping("/updateuser.do")
	@ResponseBody
	public SverResponse<String> updateUser(HttpSession session, ActionUserVo userVo) {
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return AdminService.updateUserInfo(userVo);
		}
	}
	
	@RequestMapping("/deleteuser.do")
	@ResponseBody
	public SverResponse<String> deleteUser(HttpSession session,String user_id) {
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return AdminService.deleteUser(user_id);
		}
	}
}
