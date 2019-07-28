package cn.techaction.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionCollectionService;
import cn.techaction.utils.ConstUtil;

@Controller
@RequestMapping("/collect")
public class ActionCollectionController {

	@Autowired
	private ActionCollectionService actionCollectionService;
	
	/**
	 * 添加商品到用户的收藏
	 * @param httpSession
	 * @param productId
	 * @return
	 */
	@RequestMapping("/addcollect.do")
	@ResponseBody
	public SverResponse<String> addCollection(HttpSession httpSession, String productId){
		//检查用户登录
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录之后再进行操作！");
		}
		return actionCollectionService.addCollection(user.getUser_id(), productId);
	}
	
	/**
	 * 查找一个用户的所有收藏
	 * @param httpSession
	 * @return
	 */
	@RequestMapping("/findcollects.do")
	@ResponseBody
	public SverResponse<List<ActionProduct>> findCollections(HttpSession httpSession){
		//检查用户登录
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录之后再进行操作！");
		}
		return actionCollectionService.findCollectionsByUserId(user.getUser_id());
	}
	
	/**
	 * 删除用户的一个收藏
	 * @param httpSession
	 * @param productId
	 * @return
	 */
	@RequestMapping("deletecollect.do")
	@ResponseBody
	public SverResponse<List<ActionProduct>> deleteCollection(HttpSession httpSession, String productId){
		//检查用户登录
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录之后再进行操作！");
		}
		return actionCollectionService.deleteCollection(user.getUser_id(), productId);
	}
	
	/**
	 * 检查该商品是否已经加入收藏
	 * @param httpSession
	 * @param productId
	 * @return
	 */
	@RequestMapping("/checkcollected.do")
	@ResponseBody
	public SverResponse<String> checkCollected(HttpSession httpSession, String productId){
		//检查用户登录
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录之后再进行操作！");
		}
		return actionCollectionService.checkCollected(user.getUser_id(), productId);
	}
	
}
