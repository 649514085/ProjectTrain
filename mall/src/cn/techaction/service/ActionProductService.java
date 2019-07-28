package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionProductFloorVo;
import cn.techaction.vo.ActionProductListVo;

public interface ActionProductService {
	
	/**
	 * �Ż�������������Ʒ
	 * @param num ���ҵ�����
	 * @return
	 */
	public SverResponse<List<ActionProduct>> findHotProducts(Integer num);
	
	/**
	 * �Ż����������¥����Ϣ
	 * @param num
	 * @return
	 */
	public SverResponse<ActionProductFloorVo> findFloorProducts(Integer num);
	
	/**
	 * �Ż���������Ʒ��Ų�����Ʒ��Ϣ
	 * @param productId
	 * @return
	 */
	public SverResponse<ActionProduct> findProductDetailForPortal(String productId);
	
	/**
	 * �Ż������ݲ�Ʒ���ͺ�������Ͳ�����Ʒ��Ϣ��ģ����ѯ��
	 * @param type_id
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public SverResponse<PageBean<ActionProductListVo>> findProductsForPortal(Integer type_id, String name, int pageNum,
			int pageSize);
}
