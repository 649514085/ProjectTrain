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
	 * ��ȡ�����б�
	 * @param httpSession
	 * @param status -1-���ж�����0-δ֧��������1-��֧��������2-�ѷ���������3-ȷ���ջ�����ɶ�����4-ȡ���Ķ���
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/getorderlist.do")
	@ResponseBody
	public SverResponse<PageBean<ActionOrderVo>> list(HttpSession httpSession, Integer status,
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
		//�ж��û��Ƿ��Ѿ���¼
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼���ٽ��в�����");
		}
		return actionOrderService.findOrders(user.getUser_id(), status, pageNum, pageSize);
	}
	
	/**
	 * ��������
	 * @param httpSession
	 * @param addrId
	 * @return
	 */
	@RequestMapping(value = "createorder.do", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<ActionOrderVo> createOrder(HttpSession httpSession, Integer addrId){
		//�ж��û��Ƿ��Ѿ���¼
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼���ٽ��в�����");
		}
		return actionOrderService.generateOrder(user.getUser_id(), addrId);
	}
	
	/**
	 * ȡ������
	 * @param httpSession
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/cancelorder.do")
	@ResponseBody
	public SverResponse<String> cancelOrder(HttpSession httpSession, String orderId){
		//�ж��û��Ƿ��Ѿ���¼
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼���ٽ��в�����");
		}
		return actionOrderService.cancelOrder(user.getUser_id(), orderId);
	}
	
	/**
	 * ȷ���ջ�
	 * @param httpSession
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/confirmorder.do")
	@ResponseBody
	public SverResponse<String> confirmOrder(HttpSession httpSession, String orderId){
		//�ж��û��Ƿ��Ѿ���¼
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼���ٽ��в�����");
		}
		return actionOrderService.confirmOrder(user.getUser_id(), orderId);
	}
	
	/**
	 * ֧������
	 * @param httpSession
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/payorder.do")
	@ResponseBody
	public SverResponse<String> payOrder(HttpSession httpSession, String orderId){
		//�ж��û��Ƿ��Ѿ���¼
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼���ٽ��в�����");
		}
		return actionOrderService.payOrder(user.getUser_id(), orderId);
	}
	
	/**
	 * ��ȡ������ϸ��Ϣ
	 * @param httpSession
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/getorderdetail.do")
	@ResponseBody
	public SverResponse<ActionOrderVo> getOrderDetail(HttpSession httpSession, String orderId){
		//�ж��û��Ƿ��Ѿ���¼
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼���ٽ��в�����");
		}
		return actionOrderService.findOrderDetail(user.getUser_id(), orderId);
	}
}
