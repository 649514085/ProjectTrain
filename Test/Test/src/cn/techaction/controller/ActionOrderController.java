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
import cn.techaction.pojo.OrderItem;
import cn.techaction.service.OrderService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.vo.OrderVo;

@Controller
@RequestMapping("/mgr/order")
public class ActionOrderController {
	@Autowired
	private OrderService orderService;
	
	@ResponseBody
	@RequestMapping("/findorders.do")
	public SverResponse<List<OrderVo>> findOrders(HttpSession session) {
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return orderService.findOrders();
		}
	}
	
	/**
	 * 根据订单编号查询订单
	 * @param session
	 * @param order_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findorderbyoid.do")
	public SverResponse<List<OrderVo>> findOrderByOid(HttpSession session,String order_id) {
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return orderService.findOrderByOid(order_id);
		}
	}
	
	@ResponseBody
	@RequestMapping("/findorderbyuid.do")
	public SverResponse<List<OrderVo>> findOrderByUid(HttpSession session,String user_id) {
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return orderService.findOrderByUid(user_id);
		}
	}
	
	/**
	 * 查找某订单的订单项
	 * @param session
	 * @param order_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findorderitems.do")
	public SverResponse<List<OrderItem>> findOrderItems(HttpSession session,String order_id){
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return orderService.findOrderItem(order_id);
		}
	}
}
