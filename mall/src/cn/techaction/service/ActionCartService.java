package cn.techaction.service;

import cn.techaction.common.SverResponse;
import cn.techaction.vo.ActionCartVo;

public interface ActionCartService {

	/**
	 * ������Ʒ��Ϣ�����ﳵ
	 * @param userId
	 * @param productId
	 * @param count
	 * @return
	 */
	public SverResponse<String> saveOrUpdate(String useId, String productId, Integer count);

	/**
	 * ��ѯ�û����ﳵ����Ʒ��Ϣ
	 * @param user_id
	 * @return
	 */
	public SverResponse<ActionCartVo> findAllCarts(String user_id);
	
	/**
	 * ��չ��ﳵ
	 * @param user_id
	 * @return
	 */
	public SverResponse<String> clearCarts(String user_id);

	/**
	 * ���¹��ﳵ��Ʒ�Ĵ���
	 * @param user_id
	 * @param productId
	 * @param count
	 * @param checked
	 * @return
	 */
	public SverResponse<ActionCartVo> updateCart(String user_id, String productId, Integer count, Integer checked);

	/**
	 * ɾ�����ﳵ���е���Ʒ��Ϣ
	 * @param user_id
	 * @param productId
	 * @return
	 */
	public SverResponse<ActionCartVo> deleteCart(String user_id, String productId);

	/**
	 * ��ȡ��¼�û����ﳵ����Ʒ������
	 * @param user_id
	 * @return
	 */
	public SverResponse<Integer> getCartsCount(String user_id);

	/**
	 * �õ�ĳ���û�ѡ�е����й��ﳵ
	 * @param user_id
	 * @return
	 */
	public SverResponse<ActionCartVo> findAllCheckedCarts(String user_id);

	/**
	 * ��ĳ���û����ﳵ��������Ʒ������Ϊ��ѡ��
	 * @param user_id
	 * @return
	 */
	public SverResponse<String> clearCheckedStatus(String user_id);


}
