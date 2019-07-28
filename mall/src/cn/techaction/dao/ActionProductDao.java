package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionProduct;

public interface ActionProductDao {
	
	/**
	 * 查找热门商品
	 * @param num
	 * @return
	 */
	public List<ActionProduct> findHotProducts(Integer num);
	
	/**
	 * 根据产品类型查询商品信息
	 * @param categoryId
	 * @return
	 */
	public List<ActionProduct> findProductsByProductCategory(Integer categoryId);
	
	/**
	 * 根据商品编号查询商品信息
	 * @param productId
	 * @return
	 */
	public ActionProduct findProductById(String productId);
	
	/**
	 * 根据条件查询总记录数
	 * @param product
	 * @return
	 */
	public int getTotalCount(ActionProduct product);
	
	/**
	 * 根据条件分页查询
	 * @param product
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<ActionProduct> findProducts(ActionProduct product, int startIndex, int pageSize);

	/**
	 * 更新商品信息
	 * @param product
	 */
	public int updateProduct(ActionProduct product);

	/**
	 * 获得商品楼层信息
	 * @param typeHntjx 楼层
	 * @param num 每层数量
	 * @return
	 */
	public List<ActionProduct> findProductsByGparent(Integer gCategoryId, Integer num);

}
