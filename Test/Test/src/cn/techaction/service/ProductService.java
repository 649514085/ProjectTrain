package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.Product;
import cn.techaction.vo.ProductVo;

public interface ProductService {
	
	public SverResponse<List<ProductVo>> findProductList();
	/**
	 * 多条件查询商品信息
	 * @param product
	 * @return
	 */
	public SverResponse<List<ProductVo>> findProductById(String product_id);
	public SverResponse<List<ProductVo>> findProductByName(String product_name);
	public SverResponse<String> updateStatus(String product_id,Integer status);
	public SverResponse<String> updateHot(String product_id,Integer hot);
	
	public SverResponse<String> addProduct(ProductVo productVo);
	public SverResponse<String> updateProduct(ProductVo productVo);
}
