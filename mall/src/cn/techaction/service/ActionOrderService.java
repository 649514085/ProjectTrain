package cn.techaction.service;

import cn.techaction.common.SverResponse;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionOrderVo;

public interface ActionOrderService {

	/**
	 * ������ҳ�б�
	 * @param user_id
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public SverResponse<PageBean<ActionOrderVo>> findOrders(String user_id, Integer status, int pageNum, int pageSize);

	/**
	 * ���ɶ���
	 * @param user_id
	 * @param addrId
	 * @return
	 */
	public SverResponse<ActionOrderVo> generateOrder(String user_id, Integer addrId);

	/**
	 * �޸Ķ���״̬��δ֧��->��֧����
	 * @param user_id
	 * @param orderId
	 * @return
	 */
	public SverResponse<String> payOrder(String user_id, String orderId);

	/**
	 * ���ݶ�����Ż�ȡ��������
	 * @param user_id
	 * @param orderId
	 * @return
	 */
	public SverResponse<ActionOrderVo> findOrderDetail(String user_id, String orderId);

	/**
	 * ȡ������
	 * @param user_id
	 * @param orderId
	 * @return
	 */
	public SverResponse<String> cancelOrder(String user_id, String orderId);

	/**
	 * ȷ�϶���
	 * @param user_id
	 * @param orderId
	 * @return
	 */
	public SverResponse<String> confirmOrder(String user_id, String orderId);

}
