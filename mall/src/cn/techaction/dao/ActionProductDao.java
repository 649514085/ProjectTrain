package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionProduct;

public interface ActionProductDao {
	
	/**
	 * ����������Ʒ
	 * @param num
	 * @return
	 */
	public List<ActionProduct> findHotProducts(Integer num);
	
	/**
	 * ���ݲ�Ʒ���Ͳ�ѯ��Ʒ��Ϣ
	 * @param categoryId
	 * @return
	 */
	public List<ActionProduct> findProductsByProductCategory(Integer categoryId);
	
	/**
	 * ������Ʒ��Ų�ѯ��Ʒ��Ϣ
	 * @param productId
	 * @return
	 */
	public ActionProduct findProductById(String productId);
	
	/**
	 * ����������ѯ�ܼ�¼��
	 * @param product
	 * @return
	 */
	public int getTotalCount(ActionProduct product);
	
	/**
	 * ����������ҳ��ѯ
	 * @param product
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<ActionProduct> findProducts(ActionProduct product, int startIndex, int pageSize);

	/**
	 * ������Ʒ��Ϣ
	 * @param product
	 */
	public int updateProduct(ActionProduct product);

	/**
	 * �����Ʒ¥����Ϣ
	 * @param typeHntjx ¥��
	 * @param num ÿ������
	 * @return
	 */
	public List<ActionProduct> findProductsByGparent(Integer gCategoryId, Integer num);

}
