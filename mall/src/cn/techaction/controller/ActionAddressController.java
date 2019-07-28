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
	 * ������ַ���޸ĵ�ַ
	 * @param httpSession
	 * @param address
	 * @return
	 */
	@RequestMapping(value = "/saveaddr.do", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<List<ActionAddress>> saveAddress(HttpSession httpSession, ActionAddress address){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼���ٽ��в�����");
		}
		address.setUser_id(user.getUser_id());
		//�ж����޸Ļ������
		SverResponse<String> result = null;
		if(address.getAddr_id()==null) {
			result = actionAddressService.addAddress(address);
		}else {
			result = actionAddressService.updateAddress(address);
		}
		//��ӳɹ������ص�ǰ�û������е�ַ
		if(result.isSuccess()) {
			return actionAddressService.findAddrsByUserId(user.getUser_id());
		}
		return SverResponse.createByErrorMessage(result.getMsg());
	}
	
	/**
	 * ɾ����ַ
	 * @param httpSession
	 * @param addr_id
	 * @return
	 */
	@RequestMapping(value="/deladdr.do", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<List<ActionAddress>> deleteAddress(HttpSession httpSession, Integer addr_id){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼���ٽ��в�����");
		}
		//ɾ����ַ
		SverResponse<String> result = actionAddressService.delAddress(user.getUser_id(), addr_id);
		//ɾ���ɹ��󣬷��ص�ǰ�û����е�ַ
		if(result.isSuccess()) {
			return actionAddressService.findAddrsByUserId(user.getUser_id());
		}
		return SverResponse.createByErrorMessage(result.getMsg());
	}
	
	/**
	 * ����Ĭ�ϵ�ַ
	 * @param httpSession
	 * @param addr_id
	 * @return
	 */
	@RequestMapping(value = "/setdefault.do", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<List<ActionAddress>> setDefault(HttpSession httpSession, Integer addr_id){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼���ٽ��в�����");
		}
		SverResponse<String> result = actionAddressService.updateAddrDefaultStatus(user.getUser_id(), addr_id);
		if(result.isSuccess()) {
			return actionAddressService.findAddrsByUserId(user.getUser_id());
		}
		return SverResponse.createByErrorMessage(result.getMsg());
	}
	
	/**
	 * ���ҵ�¼�û������е�ַ��Ϣ
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/findaddrs.do", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<List<ActionAddress>> findAddress(HttpSession httpSession){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼���ٽ��в�����");
		}
		return actionAddressService.findAddrsByUserId(user.getUser_id());
	}
	
	/**
	 * ���ݵ�ַid���һ����ַ����Ϣ
	 * @param httpSession
	 * @param addr_id
	 * @return
	 */
	@RequestMapping(value="/findaddrbyaddrid", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<ActionAddress> findAddrByUserAndAddrId(HttpSession httpSession, Integer addr_id){
		User user = (User) httpSession.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼���ٽ��в�����");
		}
		return actionAddressService.findAddrByUserAndAddrId(user.getUser_id(), addr_id);
	}
}
