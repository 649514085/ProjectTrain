package cn.techaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionProductService;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionProductFloorVo;
import cn.techaction.vo.ActionProductListVo;

@Controller
@RequestMapping("/product")
public class ActionProductController {
	
	@Autowired
	private ActionProductService actionProductService;
	
	/**
	 * 查询热销商品
	 * @param num
	 * @return
	 */
	@RequestMapping("/findhotproducts.do")
	@ResponseBody
	public SverResponse<List<ActionProduct>> findHotProducts(Integer num){
		return actionProductService.findHotProducts(num);
	}
	
	/**
	 * 查找楼层商品
	 * @return
	 */
	@RequestMapping("/findfloor.do")
	@ResponseBody
	public SverResponse<ActionProductFloorVo> findFloorProducts(Integer num){
		return actionProductService.findFloorProducts(num);
	}
	
	/**
	 * 根据商品编号获取商品详情
	 * @param productId
	 * @return
	 */
	@RequestMapping(value="/getdetail.do", method = RequestMethod.POST)
	@ResponseBody
	public SverResponse<ActionProduct> getProductDetail(String productId){
		return actionProductService.findProductDetailForPortal(productId);
	}
	
	/**
	 * 根据条件搜索商品
	 * @param type_id
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/findproducts.do")
	@ResponseBody
	public SverResponse<PageBean<ActionProductListVo>> searchProducts(Integer typeId, String keywords,
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		//调用Service层的方法分页查询
		System.out.println(keywords);
		return actionProductService.findProductsForPortal(typeId, keywords, pageNum, pageSize);
	}
	
}
