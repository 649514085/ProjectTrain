package cn.techaction.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.ResponseCode;
import cn.techaction.common.SverResponse;
import cn.techaction.pojo.Admin;
import cn.techaction.pojo.Type;
import cn.techaction.service.TypeService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.vo.TypeVo;

@Controller
@RequestMapping("mgr/type")
public class ActionTypeController {
	@Autowired
	private TypeService typeService;
	
	@ResponseBody
	@RequestMapping("/findroot.do")
	public SverResponse<List<Type>> findRootType(HttpSession session){
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return typeService.findRoot();
		}
	}
	
	@ResponseBody
	@RequestMapping("/findsubtype.do")
	public SverResponse<List<Type>> findSubType(HttpSession session,Integer type_id) {
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return typeService.findSubType(type_id);
		}
	}
	
	@ResponseBody
	@RequestMapping("/findrootvo.do")
	public SverResponse<List<TypeVo>> findRootTypeVo(HttpSession session){
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return typeService.findRootVo();
		}
	}
	
	@ResponseBody
	@RequestMapping("/findsubtypevo.do")
	public SverResponse<List<TypeVo>> findSubTypeVo(HttpSession session,Integer type_id) {
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return typeService.findSubTypeVo(type_id);
		}
	}
	
	@ResponseBody
	@RequestMapping("/findtypebyrank.do")
	public SverResponse<List<Type>> findTypeByRank(HttpSession session,Integer rank) {
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return typeService.findTypeByRank(rank);
		}
	}
	
	@ResponseBody
	@RequestMapping("/findtypebyname.do")
	public SverResponse<List<TypeVo>> findTypeByName(HttpSession session,String type_name) {
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return typeService.findTypeByName(type_name);
		}
	}
	
	@ResponseBody
	@RequestMapping("/findtypebytid.do")
	public SverResponse<List<TypeVo>> findTypeByTid(HttpSession session,Integer type_id) {
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return typeService.findTypeByTid(type_id);
		}
	}
	
	@ResponseBody
	@RequestMapping("/deletetype.do")
	public SverResponse<String> deleteType(HttpSession session,Integer type_id) {
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return typeService.delType(type_id);
		}
	}
	
	@ResponseBody
	@RequestMapping("/addtype.do")
	public SverResponse<String> addType(HttpSession session,TypeVo typeVo) {
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return typeService.addType(typeVo);
		}
	}
	
	@ResponseBody
	@RequestMapping("/updatetype.do")
	public SverResponse<String> updateType(HttpSession session,TypeVo typeVo) {
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return typeService.updateType(typeVo);
		}
	}
}
