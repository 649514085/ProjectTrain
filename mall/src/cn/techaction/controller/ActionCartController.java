package cn.techaction.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionCartService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.vo.ActionCartVo;

@Controller
@RequestMapping("/cart")
public class ActionCartController {
	
	@Autowired
	private ActionCartService actionCartService;
	
	/**
	 * 加入商品到购物车
	 * @param httpSession
	 * @param productId
	 * @param count
	 * @return
	 */
	@RequestMapping(value = "/savecart.do", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> addProductCart(HttpSession httpSession, String productId, Integer count){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后，再查看购物车！");
		}
		return actionCartService.saveOrUpdate(user.getUser_id(), productId, count);
	}
	
	/**
	 * 查看购物车
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/findallcarts.do")
	@ResponseBody
	public SverResponse<ActionCartVo> findAllCarts(HttpSession httpSession){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后，再查看购物车！");
		}
		return actionCartService.findAllCarts(user.getUser_id());
	}
	
	/**
	 * 得到某个用户已选中的所有购物车
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "findallcheckedcarts.do")
	@ResponseBody
	public SverResponse<ActionCartVo> findAllCheckedCarts(HttpSession httpSession){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后，再查看购物车！");
		}
		return actionCartService.findAllCheckedCarts(user.getUser_id());
	}
	
	/**
	 * 清空购物车
	 * @param httpSession
	 * @return
	 */
	@RequestMapping("/clearcarts.do")
	@ResponseBody
	public SverResponse<String> clearCarts(HttpSession httpSession){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后，再查看购物车！");
		}
		//清空购物车
		return actionCartService.clearCarts(user.getUser_id());
	}
	
	/**
	 * 更新购物车
	 * @param httpSession
	 * @param productId
	 * @param count
	 * @param checked
	 * @return
	 */
	@RequestMapping("/updatecarts.do")
	@ResponseBody
	public SverResponse<ActionCartVo> updateCarts(HttpSession httpSession, String productId, Integer count, Integer checked){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后，再查看购物车！");
		}
		return actionCartService.updateCart(user.getUser_id(), productId, count, checked);
	}
	
	/**
	 * 删除用户购物车商品
	 * @param httpSession
	 * @param productId
	 * @return
	 */
	@RequestMapping("/deletecart.do")
	@ResponseBody
	public SverResponse<ActionCartVo> deleteCart(HttpSession httpSession, String productId){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后，再查看购物车！");
		}
		return actionCartService.deleteCart(user.getUser_id(), productId);
	}
	
	/**
	 * 获得购物车数量
	 * @param httpSession
	 * @return
	 */
	@RequestMapping("/getcartscount.do")
	@ResponseBody
	public SverResponse<Integer> getCartsCount(HttpSession httpSession){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后，再查看购物车！");
		}
		return actionCartService.getCartsCount(user.getUser_id());
	}
	
	/**
	 * 将购物车中的所有商品都不选中
	 * @param httpSession
	 * @return
	 */
	@RequestMapping("/clearchecked.do")
	@ResponseBody
	public SverResponse<String> clearCheckedStatus(HttpSession httpSession){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后，再查看购物车！");
		}
		return actionCartService.clearCheckedStatus(user.getUser_id());
	}
}
