package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionProductFloorVo;
import cn.techaction.vo.ActionProductListVo;

public interface ActionProductService {
	
	/**
	 * 门户：查找热门商品
	 * @param num 查找的数量
	 * @return
	 */
	public SverResponse<List<ActionProduct>> findHotProducts(Integer num);
	
	/**
	 * 门户：获得所有楼层信息
	 * @param num
	 * @return
	 */
	public SverResponse<ActionProductFloorVo> findFloorProducts(Integer num);
	
	/**
	 * 门户：根据商品编号查找商品信息
	 * @param productId
	 * @return
	 */
	public SverResponse<ActionProduct> findProductDetailForPortal(String productId);
	
	/**
	 * 门户：根据产品类型和配件类型查找商品信息（模糊查询）
	 * @param type_id
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public SverResponse<PageBean<ActionProductListVo>> findProductsForPortal(Integer type_id, String name, int pageNum,
			int pageSize);
}
