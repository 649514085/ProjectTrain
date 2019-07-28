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
	 * �����Ʒ���û����ղ�
	 * @param httpSession
	 * @param productId
	 * @return
	 */
	@RequestMapping("/addcollect.do")
	@ResponseBody
	public SverResponse<String> addCollection(HttpSession httpSession, String productId){
		//����û���¼
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼֮���ٽ��в�����");
		}
		return actionCollectionService.addCollection(user.getUser_id(), productId);
	}
	
	/**
	 * ����һ���û��������ղ�
	 * @param httpSession
	 * @return
	 */
	@RequestMapping("/findcollects.do")
	@ResponseBody
	public SverResponse<List<ActionProduct>> findCollections(HttpSession httpSession){
		//����û���¼
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼֮���ٽ��в�����");
		}
		return actionCollectionService.findCollectionsByUserId(user.getUser_id());
	}
	
	/**
	 * ɾ���û���һ���ղ�
	 * @param httpSession
	 * @param productId
	 * @return
	 */
	@RequestMapping("deletecollect.do")
	@ResponseBody
	public SverResponse<List<ActionProduct>> deleteCollection(HttpSession httpSession, String productId){
		//����û���¼
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼֮���ٽ��в�����");
		}
		return actionCollectionService.deleteCollection(user.getUser_id(), productId);
	}
	
	/**
	 * ������Ʒ�Ƿ��Ѿ������ղ�
	 * @param httpSession
	 * @param productId
	 * @return
	 */
	@RequestMapping("/checkcollected.do")
	@ResponseBody
	public SverResponse<String> checkCollected(HttpSession httpSession, String productId){
		//����û���¼
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼֮���ٽ��в�����");
		}
		return actionCollectionService.checkCollected(user.getUser_id(), productId);
	}
	
}
