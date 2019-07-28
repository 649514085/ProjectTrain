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
	 * ������Ʒ�����ﳵ
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
			return SverResponse.createByErrorMessage("���¼���ٲ鿴���ﳵ��");
		}
		return actionCartService.saveOrUpdate(user.getUser_id(), productId, count);
	}
	
	/**
	 * �鿴���ﳵ
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/findallcarts.do")
	@ResponseBody
	public SverResponse<ActionCartVo> findAllCarts(HttpSession httpSession){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼���ٲ鿴���ﳵ��");
		}
		return actionCartService.findAllCarts(user.getUser_id());
	}
	
	/**
	 * �õ�ĳ���û���ѡ�е����й��ﳵ
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "findallcheckedcarts.do")
	@ResponseBody
	public SverResponse<ActionCartVo> findAllCheckedCarts(HttpSession httpSession){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼���ٲ鿴���ﳵ��");
		}
		return actionCartService.findAllCheckedCarts(user.getUser_id());
	}
	
	/**
	 * ��չ��ﳵ
	 * @param httpSession
	 * @return
	 */
	@RequestMapping("/clearcarts.do")
	@ResponseBody
	public SverResponse<String> clearCarts(HttpSession httpSession){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼���ٲ鿴���ﳵ��");
		}
		//��չ��ﳵ
		return actionCartService.clearCarts(user.getUser_id());
	}
	
	/**
	 * ���¹��ﳵ
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
			return SverResponse.createByErrorMessage("���¼���ٲ鿴���ﳵ��");
		}
		return actionCartService.updateCart(user.getUser_id(), productId, count, checked);
	}
	
	/**
	 * ɾ���û����ﳵ��Ʒ
	 * @param httpSession
	 * @param productId
	 * @return
	 */
	@RequestMapping("/deletecart.do")
	@ResponseBody
	public SverResponse<ActionCartVo> deleteCart(HttpSession httpSession, String productId){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼���ٲ鿴���ﳵ��");
		}
		return actionCartService.deleteCart(user.getUser_id(), productId);
	}
	
	/**
	 * ��ù��ﳵ����
	 * @param httpSession
	 * @return
	 */
	@RequestMapping("/getcartscount.do")
	@ResponseBody
	public SverResponse<Integer> getCartsCount(HttpSession httpSession){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼���ٲ鿴���ﳵ��");
		}
		return actionCartService.getCartsCount(user.getUser_id());
	}
	
	/**
	 * �����ﳵ�е�������Ʒ����ѡ��
	 * @param httpSession
	 * @return
	 */
	@RequestMapping("/clearchecked.do")
	@ResponseBody
	public SverResponse<String> clearCheckedStatus(HttpSession httpSession){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼���ٲ鿴���ﳵ��");
		}
		return actionCartService.clearCheckedStatus(user.getUser_id());
	}
}
