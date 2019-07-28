package cn.techaction.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionParamsDao;
import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.ActionProduct;

import cn.techaction.service.ActionProductService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionProductFloorVo;
import cn.techaction.vo.ActionProductListVo;

@Service
public class ActionProductServiceImpl implements ActionProductService {
	
	@Autowired
	private ActionProductDao actionProductDao;
	
	@Autowired
	private ActionParamsDao actionParamsDao;
	
	@Override
	public SverResponse<List<ActionProduct>> findHotProducts(Integer num) {
		//��ѯ�������ݼ���
		List<ActionProduct> products = actionProductDao.findHotProducts(num);
		return SverResponse.createRespBySuccess(products);
	}

	@Override
	public SverResponse<ActionProductFloorVo> findFloorProducts(Integer num) {
		//����vo����
		ActionProductFloorVo vo = new ActionProductFloorVo();
		//һ¥����
		List<ActionProduct> product1 = actionProductDao.findProductsByGparent(ConstUtil.ProductType.TYPE_HNTJX, num);
		vo.setOneFloor(product1);
		//��¥����
		List<ActionProduct> product2 = actionProductDao.findProductsByGparent(ConstUtil.ProductType.TYPE_JZQZJJX, num);
		vo.setTwoFloor(product2);
		//��¥����
		List<ActionProduct> product3 = actionProductDao.findProductsByGparent(ConstUtil.ProductType.TYPE_GCQZJJX, num);
		vo.setThreeFloor(product3);
		//��¥����
		List<ActionProduct> product4 = actionProductDao.findProductsByGparent(ConstUtil.ProductType.TYPE_LMJX, num);
		vo.setFourFloor(product4);
		return SverResponse.createRespBySuccess(vo);
	}

	@Override
	public SverResponse<ActionProduct> findProductDetailForPortal(String productId) {
		//�жϲ�Ʒ����Ƿ�Ϊ��
		if(productId == null) {
			return SverResponse.createByErrorMessage("��Ʒ��Ų���Ϊ�գ�");
		}
		//��ѯ��Ʒ����
		ActionProduct product = actionProductDao.findProductById(productId);
		//�жϲ�Ʒ�Ƿ��¼�
		if(product==null||product.getStatus()==ConstUtil.ProductStatus.STATUS_OFF_SALE) {
			return SverResponse.createByErrorMessage("��Ʒ�Ѿ��¼ܣ�");
		}
		return SverResponse.createRespBySuccess(product);
	}

	@Override
	public SverResponse<PageBean<ActionProductListVo>> findProductsForPortal(Integer type_id, String name, int pageNum,
			int pageSize) {
		//��������
		ActionProduct product = new ActionProduct();
		int totalRecord = 0;
		//�ж�name�Ƿ�Ϊ��
		if(name!=null && !name.equals("")) {
			product.setProduct_name(name);
		}
		if(type_id!=null&&type_id!=0) {
			product.setType_id(type_id);
		}
		//ǰ����ʾ��Ʒ��Ϊ����
		product.setStatus(1);
		//���ҷ����������ܼ�¼��
		totalRecord = actionProductDao.getTotalCount(product);
		//������ҳ����
		PageBean<ActionProductListVo> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
		//��ȡ����
		List<ActionProduct> products = actionProductDao.findProducts(product, pageBean.getStartIndex(),pageSize);
		//��װ
		List<ActionProductListVo> voList = Lists.newArrayList();
		for(ActionProduct p : products) {
			voList.add(createProductListVo(p));
		}
		pageBean.setData(voList);
		return SverResponse.createRespBySuccess(pageBean);
	}
	
	//��װvo����
	private ActionProductListVo createProductListVo(ActionProduct product) {
		ActionProductListVo vo = new ActionProductListVo();
		vo.setProduct_id(product.getProduct_id());
		vo.setProduct_name(product.getProduct_name());
		vo.setType(actionParamsDao.findParamById(product.getType_id()).getType_name());
		vo.setPrice(product.getPrice());
		vo.setSpec_param(product.getSpec_param());
		vo.setStatus(ConstUtil.ProductStatus.getStatusDesc(product.getStatus()));
		vo.setStock(product.getStock());
		vo.setHot(ConstUtil.HotStatus.getHotDesc(product.getHot()));
		vo.setDetail(product.getDetail());
		vo.setMain_image(product.getMain_image());
		vo.setSub_images(product.getSub_images());
		return vo;
	}
}
