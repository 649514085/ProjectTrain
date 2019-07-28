package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionAddress;

public interface ActionAddressDao {
	
	/**
	 * ��ѯ�Ƿ����Ĭ�ϵ�ַ
	 * @param user_id
	 * @return
	 */
	public int findDefaultAddrByUserId(String user_id);

	/**
	 * �����ջ��˵�ַ��Ϣ
	 * @param address
	 * @return
	 */
	public int insertAddress(ActionAddress address);
	
	/**
	 * �����ռ��˵�ַ��Ϣ
	 * @param address
	 * @return
	 */
	public int updateAddress(ActionAddress address);
	
	/**
	 * ��ѯ�û����ռ��˵�ַ��Ϣ
	 * @param user_id
	 * @return
	 */
	public List<ActionAddress> findAddrsByUserId(String user_id);
	
	/**
	 * ����addr_idɾ��һ����ַ
	 * @param user_id
	 * @param addr_id
	 * @return
	 */
	public int delAddress(String user_id, Integer addr_id);
	
	/**
	 * ����user_id�������Ĭ�ϵ�ַ
	 * @param user_id
	 * @return
	 */
	public ActionAddress findDefaultAddr(String user_id);

	/**
	 * ���ݵ�ַaddr_id��õ�ַ��Ϣ
	 * @param addr_id
	 * @return
	 */
	public ActionAddress findAddrById(Integer addr_id);

	/**
	 * �����û�id�͵�ַid���һ����ַ��Ϣ
	 * @param user_id
	 * @param addr_id
	 * @return
	 */
	public ActionAddress findAddrByUserAndAddrId(String user_id, Integer addr_id);

}
