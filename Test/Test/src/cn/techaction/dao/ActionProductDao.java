package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.Icons;
import cn.techaction.pojo.Product;

public interface ActionProductDao {
	/**
	   *  ��ѯ������Ʒ
	 * @return
	 */
	public List<Product> findProductList();
	/**
	   * ������Ʒ��Ų�ѯ��Ʒ
	 * @param product
	 * @return
	 */
	public List<Product> findProductByPid(String product_id);
	/**
	 * ������Ʒ���Ʋ�ѯ��Ʒ
	 * @param product_name
	 * @return
	 */
	public List<Product> findProductByPname(String product_name);
	public List<Product> findProductOfType(Integer type_id);
	public int updateStatus(Product product);
	public int updateHot(Product product);
	public int addProduct(Product product);
	public int updateProduct(Product product);

}
