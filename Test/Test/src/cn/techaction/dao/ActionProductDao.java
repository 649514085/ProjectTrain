package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.Icons;
import cn.techaction.pojo.Product;

public interface ActionProductDao {
	/**
	   *  查询所有商品
	 * @return
	 */
	public List<Product> findProductList();
	/**
	   * 按照商品编号查询商品
	 * @param product
	 * @return
	 */
	public List<Product> findProductByPid(String product_id);
	/**
	 * 按照商品名称查询商品
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
