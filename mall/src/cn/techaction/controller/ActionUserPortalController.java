package cn.techaction.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.eventbus.SubscriberExceptionContext;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.service.UserService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.vo.ActionUserVo;

@Controller
@RequestMapping("/user")
public class ActionUserPortalController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * �û���¼
	 * @param user_id
	 * @param password
	 * @param httpSession
	 * @return
	 */
	
	@RequestMapping(value="/do_login.do", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<User> doLogin(String user_id, String password, HttpSession httpSession){
		SverResponse<User> response = userService.doLogin(user_id, password);
		if(response.isSuccess()) {
			//��¼�ɹ������û���Ϣ����session
			httpSession.setAttribute(ConstUtil.CUR_USER, response.getData());
		}
		return response;
	}
	
	/**
	 * �û�ע��
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/do_register.do", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> registerUser(User user){
		return userService.doRegister(user);
	}
	
	/**
	 * �����û�id��֤�û����һ���û�����
	 * @param user_id
	 * @return
	 */
	@RequestMapping(value = "/getuserbyuserid.do", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<User> getUserByUserId(String user_id){
		return userService.findUserByUserId(user_id);
	}
	
	/**
	 * ��ȡ��ǰ��¼����Ļ�����Ϣ
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/getuserinfo.do", method = RequestMethod.GET)
	@ResponseBody
	public SverResponse<User> getUserByUserId(HttpSession httpSession){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼���ٽ��в�����");
		}
		return userService.findUserByUserId(user.getUser_id());
	}
	
	/**
	 * ��֤�û�������ʾ�����
	 * @param user_id
	 * @param question
	 * @param asw
	 * @return
	 */
	@RequestMapping(value = "/checkuserasw.do", method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> checkUserAnswer(String user_id, String question, String asw){
		return userService.checkUserAnswer(user_id, question, asw);
	}
	
	/**
	 * ��������
	 * @param user_id
	 * @param newpwd
	 * @return
	 */
	@RequestMapping(value = "/resetpassword.do", method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> resetPassword(String user_id, String newpwd){
		return userService.resetPassword(user_id, newpwd);
	}
	
	/**
	 * �޸��û���������
	 * @param httpSession
	 * @param userVo
	 * @return
	 */
	@RequestMapping(value = "/updateuserinfo.do", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<User> updateUserInfo(HttpSession httpSession, ActionUserVo userVo){
		User curUser = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		System.out.println(userVo.getName()+" "+userVo.getAge());
		if(curUser == null) {
			return SverResponse.createByErrorMessage("�û���δ��¼��");
		}
		userVo.setUser_id(curUser.getUser_id());
		SverResponse<User> response = userService.updateUserInfo(userVo);
		if(response.isSuccess()) {
			//��дhttpSession
			httpSession.setAttribute(ConstUtil.CUR_USER, response.getData());
		}
		return response;
	}
	
	/**
	 * ��¼�û��޸�����
	 * @param httpSession
	 * @param newpwd
	 * @param oldpwd
	 * @return
	 */
	@RequestMapping(value="/updatepassword.do", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> updatePassword(HttpSession httpSession, String newpwd, String oldpwd){
		//�����ǵ�httpSessionȡ��
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼�����޸����룡");
		}
		SverResponse<String> result = userService.updatePassword(user, newpwd, oldpwd);
		//�޸ĳɹ������httpSession��׼�����µ�¼
		if(result.isSuccess()) {
			httpSession.removeAttribute(ConstUtil.CUR_USER);
		}
		return result;
	}
	
	/**
	 * �û��˳���¼
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/loginout.do")
	@ResponseBody
	public SverResponse<String> loginOut(HttpSession httpSession){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("��ǰ�����û���¼��");
		}
		httpSession.setAttribute(ConstUtil.CUR_USER, null);
		return SverResponse.createRespBySuccessMessage("�˳���¼�ɹ���");
	}
}
