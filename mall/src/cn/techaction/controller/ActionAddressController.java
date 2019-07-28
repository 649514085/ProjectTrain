package cn.techaction.controller;

import java.sql.Savepoint;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionAddress;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionAddressService;
import cn.techaction.utils.ConstUtil;

@Controller
@RequestMapping("/addr")
public class ActionAddressController {
	
	@Autowired
	private ActionAddressService actionAddressService;
	
	/**
	 * 新增地址或修改地址
	 * @param httpSession
	 * @param address
	 * @return
	 */
	@RequestMapping(value = "/saveaddr.do", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<List<ActionAddress>> saveAddress(HttpSession httpSession, ActionAddress address){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后再进行操作！");
		}
		address.setUser_id(user.getUser_id());
		//判断是修改还是添加
		SverResponse<String> result = null;
		if(address.getAddr_id()==null) {
			result = actionAddressService.addAddress(address);
		}else {
			result = actionAddressService.updateAddress(address);
		}
		//添加成功，返回当前用户的所有地址
		if(result.isSuccess()) {
			return actionAddressService.findAddrsByUserId(user.getUser_id());
		}
		return SverResponse.createByErrorMessage(result.getMsg());
	}
	
	/**
	 * 删除地址
	 * @param httpSession
	 * @param addr_id
	 * @return
	 */
	@RequestMapping(value="/deladdr.do", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<List<ActionAddress>> deleteAddress(HttpSession httpSession, Integer addr_id){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后再进行操作！");
		}
		//删除地址
		SverResponse<String> result = actionAddressService.delAddress(user.getUser_id(), addr_id);
		//删除成功后，返回当前用户所有地址
		if(result.isSuccess()) {
			return actionAddressService.findAddrsByUserId(user.getUser_id());
		}
		return SverResponse.createByErrorMessage(result.getMsg());
	}
	
	/**
	 * 设置默认地址
	 * @param httpSession
	 * @param addr_id
	 * @return
	 */
	@RequestMapping(value = "/setdefault.do", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<List<ActionAddress>> setDefault(HttpSession httpSession, Integer addr_id){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后再进行操作！");
		}
		SverResponse<String> result = actionAddressService.updateAddrDefaultStatus(user.getUser_id(), addr_id);
		if(result.isSuccess()) {
			return actionAddressService.findAddrsByUserId(user.getUser_id());
		}
		return SverResponse.createByErrorMessage(result.getMsg());
	}
	
	/**
	 * 查找登录用户的所有地址信息
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/findaddrs.do", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<List<ActionAddress>> findAddress(HttpSession httpSession){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后再进行操作！");
		}
		return actionAddressService.findAddrsByUserId(user.getUser_id());
	}
	
	/**
	 * 根据地址id获得一个地址的信息
	 * @param httpSession
	 * @param addr_id
	 * @return
	 */
	@RequestMapping(value="/findaddrbyaddrid", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<ActionAddress> findAddrByUserAndAddrId(HttpSession httpSession, Integer addr_id){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后再进行操作！");
		}
		return actionAddressService.findAddrByUserAndAddrId(user.getUser_id(), addr_id);
	}
}
