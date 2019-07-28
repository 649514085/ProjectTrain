package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionAddress;

public interface ActionAddressDao {
	
	/**
	 * 查询是否存在默认地址
	 * @param user_id
	 * @return
	 */
	public int findDefaultAddrByUserId(String user_id);

	/**
	 * 新增收货人地址信息
	 * @param address
	 * @return
	 */
	public int insertAddress(ActionAddress address);
	
	/**
	 * 更新收件人地址信息
	 * @param address
	 * @return
	 */
	public int updateAddress(ActionAddress address);
	
	/**
	 * 查询用户的收件人地址信息
	 * @param user_id
	 * @return
	 */
	public List<ActionAddress> findAddrsByUserId(String user_id);
	
	/**
	 * 根据addr_id删除一条地址
	 * @param user_id
	 * @param addr_id
	 * @return
	 */
	public int delAddress(String user_id, Integer addr_id);
	
	/**
	 * 根据user_id获得他的默认地址
	 * @param user_id
	 * @return
	 */
	public ActionAddress findDefaultAddr(String user_id);

	/**
	 * 根据地址addr_id获得地址信息
	 * @param addr_id
	 * @return
	 */
	public ActionAddress findAddrById(Integer addr_id);

	/**
	 * 根据用户id和地址id获得一个地址信息
	 * @param user_id
	 * @param addr_id
	 * @return
	 */
	public ActionAddress findAddrByUserAndAddrId(String user_id, Integer addr_id);

}
