package cn.techaction.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionOrderService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionOrderVo;

@Controller
@RequestMapping("/order")
public class ActionOrderController {

	@Autowired
	private ActionOrderService actionOrderService;
	
	/**
	 * 获取订单列表
	 * @param httpSession
	 * @param status -1-所有订单，0-未支付订单，1-已支付订单，2-已发货订单，3-确认收货已完成订单，4-取消的订单
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/getorderlist.do")
	@ResponseBody
	public SverResponse<PageBean<ActionOrderVo>> list(HttpSession httpSession, Integer status,
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
		//判断用户是否已经登录
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后再进行操作！");
		}
		return actionOrderService.findOrders(user.getUser_id(), status, pageNum, pageSize);
	}
	
	/**
	 * 创建订单
	 * @param httpSession
	 * @param addrId
	 * @return
	 */
	@RequestMapping(value = "createorder.do", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<ActionOrderVo> createOrder(HttpSession httpSession, Integer addrId){
		//判断用户是否已经登录
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后再进行操作！");
		}
		return actionOrderService.generateOrder(user.getUser_id(), addrId);
	}
	
	/**
	 * 取消订单
	 * @param httpSession
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/cancelorder.do")
	@ResponseBody
	public SverResponse<String> cancelOrder(HttpSession httpSession, String orderId){
		//判断用户是否已经登录
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后再进行操作！");
		}
		return actionOrderService.cancelOrder(user.getUser_id(), orderId);
	}
	
	/**
	 * 确认收货
	 * @param httpSession
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/confirmorder.do")
	@ResponseBody
	public SverResponse<String> confirmOrder(HttpSession httpSession, String orderId){
		//判断用户是否已经登录
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后再进行操作！");
		}
		return actionOrderService.confirmOrder(user.getUser_id(), orderId);
	}
	
	/**
	 * 支付订单
	 * @param httpSession
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/payorder.do")
	@ResponseBody
	public SverResponse<String> payOrder(HttpSession httpSession, String orderId){
		//判断用户是否已经登录
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后再进行操作！");
		}
		return actionOrderService.payOrder(user.getUser_id(), orderId);
	}
	
	/**
	 * 获取订单详细信息
	 * @param httpSession
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/getorderdetail.do")
	@ResponseBody
	public SverResponse<ActionOrderVo> getOrderDetail(HttpSession httpSession, String orderId){
		//判断用户是否已经登录
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("请登录后再进行操作！");
		}
		return actionOrderService.findOrderDetail(user.getUser_id(), orderId);
	}
}
