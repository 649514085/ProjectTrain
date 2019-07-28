package cn.techaction.service.impl;

import java.util.List;

import javax.print.attribute.ResolutionSyntax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionCollectionDao;
import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.ActionCollection;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionCollectionService;

@Service
public class ActionCollectionServiceImpl implements ActionCollectionService {

	@Autowired
	ActionCollectionDao actionCollectionDao; 
	
	@Autowired
	ActionProductDao actionProductDao;
	
	@Override
	public SverResponse<String> addCollection(String userId, String productId) {
		//�������
		if(userId==null||productId==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		//�ж��Ƿ��Ѿ��ղظ���Ʒ
		int count = actionCollectionDao.findCollections(userId, productId);
		if(count>0) {
			return SverResponse.createByErrorMessage("�����ղظ���Ʒ�������ٴ��ղأ�");
		}
		//�����Ʒ���û����ղ�
		int rs = actionCollectionDao.insertOneCollection(userId, productId);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("����ղسɹ���");
		}
		return SverResponse.createByErrorMessage("����ղ�ʧ�ܣ�");
	}

	@Override
	public SverResponse<List<ActionProduct>> findCollectionsByUserId(String user_id) {
		//�������
		if(user_id==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		List<ActionCollection> collections = actionCollectionDao.findCollectionsByUserId(user_id);
		List<ActionProduct> lists = Lists.newArrayList();
		for(ActionCollection collection : collections) {
			ActionProduct product = actionProductDao.findProductById(collection.getProduct_id());
			lists.add(product);
		}
		return SverResponse.createRespBySuccess(lists);
	}

	@Override
	public SverResponse<List<ActionProduct>> deleteCollection(String user_id, String productId) {
		//�������
		if(user_id==null||productId==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		int rs = actionCollectionDao.deleteOneCollection(user_id, productId);
		if(rs>0) {
			return findCollectionsByUserId(user_id);
		}
		return SverResponse.createByErrorMessage("ɾ���ղ�ʧ�ܣ�");
	}

	@Override
	public SverResponse<String> checkCollected(String user_id, String productId) {
		//�������
		if(user_id==null||productId==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		//�ж��Ƿ��Ѿ��ղظ���Ʒ
		int count = actionCollectionDao.findCollections(user_id, productId);
		if(count>0) {
			return SverResponse.createRespBySuccessMessage("���ղ�");
		}
		return SverResponse.createRespBySuccessMessage("δ�ղ�");
	}
}
