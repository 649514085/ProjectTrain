package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionCart;

public interface ActionCartDao {

	/**
	 * 根据用户id和产品id查找购物车
	 * @param userId
	 * @param productId
	 * @return
	 */
	public ActionCart findCartByUserAndProductId(String userId, String productId);

	/**
	 * 保存购物车
	 * @param cart
	 * @return
	 */
	public int insertCart(ActionCart cart);
	
	/**
	 * 更新购物车当中的商品数量
	 * @param actionCart
	 * @return
	 */
	public int updateCart(ActionCart actionCart);

	/**
	 * 查找某个用户购物车中的所有商品
	 * @param user_id
	 * @return
	 */
	public List<ActionCart> findCartByUserId(String user_id);
	
	/**
	 * 删除某个用户购物车当中的所有商品
	 * @param user_id
	 * @return
	 */
	public int deleteCartByUserId(String user_id);

	/**
	 * 更新购物车中的商品数量
	 * @param actionCart
	 * @return
	 */
	public int updateCartByUserAndProductId(ActionCart actionCart);

	/**
	 * 删除购物车中的商品信息
	 * @param user_id
	 * @param productId
	 * @return
	 */
	public int deleteCart(String user_id, String productId);

	/**
	 * 获取当前用户购物车中的商品数量
	 * @param user_id
	 * @return
	 */
	public int getCartsCountByUserId(String user_id);

	/**
	 * 获得某个用户选中的所有购物车
	 * @param user_id
	 * @return
	 */
	public List<ActionCart> findCheckedCartsByUserId(String user_id);

	/**
	 * 删除某个用户选中的所有购物车
	 * @param user_id
	 */
	public int deleteCheckedCartsByUserId(String user_id);

	/**
	 * 将某个用户购物车中的所有商品设为不选中
	 * @param user_id
	 * @return
	 */
	public int clearCheckedStatus(String user_id);

}
