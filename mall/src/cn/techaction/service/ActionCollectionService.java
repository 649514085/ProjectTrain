package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;

public interface ActionCollectionService {

	/**
	 * �����Ʒ���û����ղ�
	 * @param userId
	 * @param productId
	 * @return
	 */
	SverResponse<String> addCollection(String userId, String productId);

	/**
	 * ����һ���û��������ղ�
	 * @param user_id
	 * @return
	 */
	SverResponse<List<ActionProduct>> findCollectionsByUserId(String user_id);

	/**
	 * ɾ���û���һ���ղ�
	 * @param user_id
	 * @param productId
	 * @return
	 */
	SverResponse<List<ActionProduct>> deleteCollection(String user_id, String productId);

	/**
	 * ����û���ĳ����Ʒ�Ƿ��Ѿ��ղ�
	 * @param user_id
	 * @param productId
	 * @return
	 */
	SverResponse<String> checkCollected(String user_id, String productId);

}
