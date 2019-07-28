package cn.techaction.service;

import cn.techaction.common.SverResponse;
import cn.techaction.vo.ActionCartVo;

public interface ActionCartService {

	/**
	 * 保存商品信息到购物车
	 * @param userId
	 * @param productId
	 * @param count
	 * @return
	 */
	public SverResponse<String> saveOrUpdate(String useId, String productId, Integer count);

	/**
	 * 查询用户购物车中商品信息
	 * @param user_id
	 * @return
	 */
	public SverResponse<ActionCartVo> findAllCarts(String user_id);
	
	/**
	 * 清空购物车
	 * @param user_id
	 * @return
	 */
	public SverResponse<String> clearCarts(String user_id);

	/**
	 * 更新购物车商品的处理
	 * @param user_id
	 * @param productId
	 * @param count
	 * @param checked
	 * @return
	 */
	public SverResponse<ActionCartVo> updateCart(String user_id, String productId, Integer count, Integer checked);

	/**
	 * 删除购物车当中的商品信息
	 * @param user_id
	 * @param productId
	 * @return
	 */
	public SverResponse<ActionCartVo> deleteCart(String user_id, String productId);

	/**
	 * 获取登录用户购物车中商品的数量
	 * @param user_id
	 * @return
	 */
	public SverResponse<Integer> getCartsCount(String user_id);

	/**
	 * 得到某个用户选中的所有购物车
	 * @param user_id
	 * @return
	 */
	public SverResponse<ActionCartVo> findAllCheckedCarts(String user_id);

	/**
	 * 将某个用户购物车的所有商品均设置为不选中
	 * @param user_id
	 * @return
	 */
	public SverResponse<String> clearCheckedStatus(String user_id);


}
