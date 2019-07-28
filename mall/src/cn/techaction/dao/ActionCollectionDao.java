package cn.techaction.dao;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionCollection;
import cn.techaction.pojo.ActionProduct;

public interface ActionCollectionDao {

	/**
	 * 添加一条用户的收藏记录
	 * @param userId
	 * @param productId
	 * @return
	 */
	public int insertOneCollection(String userId, String productId);

	/**
	 * 查找该用户是否已经收藏该商品
	 * @param userId
	 * @param productId
	 * @return
	 */
	public int findCollections(String userId, String productId);

	/**
	 * 根据用户id找到所有收藏
	 * @param user_id
	 * @return
	 */
	public List<ActionCollection> findCollectionsByUserId(String user_id);

	/**
	 * 删除用户的一个收藏
	 * @param user_id
	 * @param productId
	 * @return
	 */
	public int deleteOneCollection(String user_id, String productId);

}
