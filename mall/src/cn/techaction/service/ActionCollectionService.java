package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;

public interface ActionCollectionService {

	/**
	 * 添加商品到用户的收藏
	 * @param userId
	 * @param productId
	 * @return
	 */
	SverResponse<String> addCollection(String userId, String productId);

	/**
	 * 查找一个用户的所有收藏
	 * @param user_id
	 * @return
	 */
	SverResponse<List<ActionProduct>> findCollectionsByUserId(String user_id);

	/**
	 * 删除用户的一个收藏
	 * @param user_id
	 * @param productId
	 * @return
	 */
	SverResponse<List<ActionProduct>> deleteCollection(String user_id, String productId);

	/**
	 * 检查用户的某个商品是否已经收藏
	 * @param user_id
	 * @param productId
	 * @return
	 */
	SverResponse<String> checkCollected(String user_id, String productId);

}
