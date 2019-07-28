package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionAddress;

public interface ActionAddressService {
	
	/**
	 * �����ռ��˵�ַ��Ϣ
	 * @param address
	 * @return
	 */
	SverResponse<String> addAddress(ActionAddress address);
	
	/**
	 * �����ռ��˵�ַ��Ϣ
	 * @param address
	 * @return
	 */
	SverResponse<String> updateAddress(ActionAddress address);

	/**
	 * ����ĳ���û��������ջ���ַ
	 * @param user_id
	 * @return
	 */
	SverResponse<List<ActionAddress>> findAddrsByUserId(String user_id);
	
	/**
	 * ����addr_idɾ���ռ��˵�ַ��Ϣ
	 * @param user_id
	 * @param addr_id
	 * @return
	 */
	SverResponse<String> delAddress(String user_id, Integer addr_id);
	
	/**
	 * ����Ĭ�ϵ�ַ
	 * @param user_id
	 * @param addr_id
	 * @return
	 */
	SverResponse<String> updateAddrDefaultStatus(String user_id, Integer addr_id);

	/**
	 * �����û�id�͵�ַid��ȡ��ַ����
	 * @param user_id
	 * @param addr_id
	 * @return
	 */
	SverResponse<ActionAddress> findAddrByUserAndAddrId(String user_id, Integer addr_id);

}
