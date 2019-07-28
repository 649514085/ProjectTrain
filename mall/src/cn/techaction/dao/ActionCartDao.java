package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionCart;

public interface ActionCartDao {

	/**
	 * �����û�id�Ͳ�Ʒid���ҹ��ﳵ
	 * @param userId
	 * @param productId
	 * @return
	 */
	public ActionCart findCartByUserAndProductId(String userId, String productId);

	/**
	 * ���湺�ﳵ
	 * @param cart
	 * @return
	 */
	public int insertCart(ActionCart cart);
	
	/**
	 * ���¹��ﳵ���е���Ʒ����
	 * @param actionCart
	 * @return
	 */
	public int updateCart(ActionCart actionCart);

	/**
	 * ����ĳ���û����ﳵ�е�������Ʒ
	 * @param user_id
	 * @return
	 */
	public List<ActionCart> findCartByUserId(String user_id);
	
	/**
	 * ɾ��ĳ���û����ﳵ���е�������Ʒ
	 * @param user_id
	 * @return
	 */
	public int deleteCartByUserId(String user_id);

	/**
	 * ���¹��ﳵ�е���Ʒ����
	 * @param actionCart
	 * @return
	 */
	public int updateCartByUserAndProductId(ActionCart actionCart);

	/**
	 * ɾ�����ﳵ�е���Ʒ��Ϣ
	 * @param user_id
	 * @param productId
	 * @return
	 */
	public int deleteCart(String user_id, String productId);

	/**
	 * ��ȡ��ǰ�û����ﳵ�е���Ʒ����
	 * @param user_id
	 * @return
	 */
	public int getCartsCountByUserId(String user_id);

	/**
	 * ���ĳ���û�ѡ�е����й��ﳵ
	 * @param user_id
	 * @return
	 */
	public List<ActionCart> findCheckedCartsByUserId(String user_id);

	/**
	 * ɾ��ĳ���û�ѡ�е����й��ﳵ
	 * @param user_id
	 */
	public int deleteCheckedCartsByUserId(String user_id);

	/**
	 * ��ĳ���û����ﳵ�е�������Ʒ��Ϊ��ѡ��
	 * @param user_id
	 * @return
	 */
	public int clearCheckedStatus(String user_id);

}
