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
	 * 用户登录
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
			//登录成功，将用户信息存入session
			httpSession.setAttribute(ConstUtil.CUR_USER, response.getData());
		}
		return response;
	}
	
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/do_register.do", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> registerUser(User user){
		return userService.doRegister(user);
	}
	
	/**
	 * 根据用户id验证用户并且获得用户对象
	 * @param user_id
	 * @return
	 */
	@RequestMapping(value = "/getuserbyuserid.do", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<User> getUserByUserId(String user_id){
		return userService.findUserByUserId(user_id);
	}
	
	/**
	 * 获取当前登录对象的基本信息
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/getuserinfo.do", method = RequestMethod.GET)
	@ResponseBody
	public SverResponse<User> getUserByUserId(HttpSession httpSession){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后再进行操作！");
		}
		return userService.findUserByUserId(user.getUser_id());
	}
	
	/**
	 * 验证用户密码提示问题答案
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
	 * 重置密码
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
	 * 修改用户个人资料
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
			return SverResponse.createByErrorMessage("用户尚未登录！");
		}
		userVo.setUser_id(curUser.getUser_id());
		SverResponse<User> response = userService.updateUserInfo(userVo);
		if(response.isSuccess()) {
			//重写httpSession
			httpSession.setAttribute(ConstUtil.CUR_USER, response.getData());
		}
		return response;
	}
	
	/**
	 * 登录用户修改密码
	 * @param httpSession
	 * @param newpwd
	 * @param oldpwd
	 * @return
	 */
	@RequestMapping(value="/updatepassword.do", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> updatePassword(HttpSession httpSession, String newpwd, String oldpwd){
		//将我们的httpSession取出
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后再修改密码！");
		}
		SverResponse<String> result = userService.updatePassword(user, newpwd, oldpwd);
		//修改成功后清空httpSession，准备重新登录
		if(result.isSuccess()) {
			httpSession.removeAttribute(ConstUtil.CUR_USER);
		}
		return result;
	}
	
	/**
	 * 用户退出登录
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/loginout.do")
	@ResponseBody
	public SverResponse<String> loginOut(HttpSession httpSession){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("当前并无用户登录！");
		}
		httpSession.setAttribute(ConstUtil.CUR_USER, null);
		return SverResponse.createRespBySuccessMessage("退出登录成功！");
	}
}
