package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionAddress;

public interface ActionAddressService {
	
	/**
	 * 新增收件人地址信息
	 * @param address
	 * @return
	 */
	SverResponse<String> addAddress(ActionAddress address);
	
	/**
	 * 更新收件人地址信息
	 * @param address
	 * @return
	 */
	SverResponse<String> updateAddress(ActionAddress address);

	/**
	 * 查找某个用户的所有收货地址
	 * @param user_id
	 * @return
	 */
	SverResponse<List<ActionAddress>> findAddrsByUserId(String user_id);
	
	/**
	 * 根据addr_id删除收件人地址信息
	 * @param user_id
	 * @param addr_id
	 * @return
	 */
	SverResponse<String> delAddress(String user_id, Integer addr_id);
	
	/**
	 * 更新默认地址
	 * @param user_id
	 * @param addr_id
	 * @return
	 */
	SverResponse<String> updateAddrDefaultStatus(String user_id, Integer addr_id);

	/**
	 * 根据用户id和地址id获取地址详情
	 * @param user_id
	 * @param addr_id
	 * @return
	 */
	SverResponse<ActionAddress> findAddrByUserAndAddrId(String user_id, Integer addr_id);

}
