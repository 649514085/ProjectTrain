package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionOrder;

public interface ActionOrderDao {

	/**
	 * ��ȡ�û���������������״̬��
	 * @param user_id
	 * @param status
	 * @return
	 */
	public int getTotalRecord(String user_id, Integer status);

	/**
	 * ����û�������ҳ�б�
	 * @param user_id
	 * @param status
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<ActionOrder> findOrders(String user_id, Integer status, int startIndex, int pageSize);

	/**
	 * ���붩��
	 * @param order
	 * @return
	 */
	public int insertOrder(ActionOrder order);

	/**
	 * �����û��Ͷ�����Ų�ѯ������Ϣ
	 * @param user_id
	 * @param orderId
	 * @return
	 */
	public ActionOrder findOrderByUserAndOrderId(String user_id, String orderId);

	/**
	 * ���¶�����Ϣ
	 * @param updateOrder
	 * @return
	 */
	public int updateOrder(ActionOrder updateOrder);

}
