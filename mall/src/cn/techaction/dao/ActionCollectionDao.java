package cn.techaction.dao;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionCollection;
import cn.techaction.pojo.ActionProduct;

public interface ActionCollectionDao {

	/**
	 * ���һ���û����ղؼ�¼
	 * @param userId
	 * @param productId
	 * @return
	 */
	public int insertOneCollection(String userId, String productId);

	/**
	 * ���Ҹ��û��Ƿ��Ѿ��ղظ���Ʒ
	 * @param userId
	 * @param productId
	 * @return
	 */
	public int findCollections(String userId, String productId);

	/**
	 * �����û�id�ҵ������ղ�
	 * @param user_id
	 * @return
	 */
	public List<ActionCollection> findCollectionsByUserId(String user_id);

	/**
	 * ɾ���û���һ���ղ�
	 * @param user_id
	 * @param productId
	 * @return
	 */
	public int deleteOneCollection(String user_id, String productId);

}
