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
		//查询所需数据即可
		List<ActionProduct> products = actionProductDao.findHotProducts(num);
		return SverResponse.createRespBySuccess(products);
	}

	@Override
	public SverResponse<ActionProductFloorVo> findFloorProducts(Integer num) {
		//创建vo对象
		ActionProductFloorVo vo = new ActionProductFloorVo();
		//一楼数据
		List<ActionProduct> product1 = actionProductDao.findProductsByGparent(ConstUtil.ProductType.TYPE_HNTJX, num);
		vo.setOneFloor(product1);
		//二楼数据
		List<ActionProduct> product2 = actionProductDao.findProductsByGparent(ConstUtil.ProductType.TYPE_JZQZJJX, num);
		vo.setTwoFloor(product2);
		//三楼数据
		List<ActionProduct> product3 = actionProductDao.findProductsByGparent(ConstUtil.ProductType.TYPE_GCQZJJX, num);
		vo.setThreeFloor(product3);
		//四楼数据
		List<ActionProduct> product4 = actionProductDao.findProductsByGparent(ConstUtil.ProductType.TYPE_LMJX, num);
		vo.setFourFloor(product4);
		return SverResponse.createRespBySuccess(vo);
	}

	@Override
	public SverResponse<ActionProduct> findProductDetailForPortal(String productId) {
		//判断产品编号是否为空
		if(productId == null) {
			return SverResponse.createByErrorMessage("产品编号不能为空！");
		}
		//查询商品详情
		ActionProduct product = actionProductDao.findProductById(productId);
		//判断产品是否下架
		if(product==null||product.getStatus()==ConstUtil.ProductStatus.STATUS_OFF_SALE) {
			return SverResponse.createByErrorMessage("商品已经下架！");
		}
		return SverResponse.createRespBySuccess(product);
	}

	@Override
	public SverResponse<PageBean<ActionProductListVo>> findProductsForPortal(Integer type_id, String name, int pageNum,
			int pageSize) {
		//创建对象
		ActionProduct product = new ActionProduct();
		int totalRecord = 0;
		//判断name是否为空
		if(name!=null && !name.equals("")) {
			product.setProduct_name(name);
		}
		if(type_id!=null&&type_id!=0) {
			product.setType_id(type_id);
		}
		//前端显示商品都为在售
		product.setStatus(1);
		//查找符合条件的总记录数
		totalRecord = actionProductDao.getTotalCount(product);
		//创建分页对象
		PageBean<ActionProductListVo> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
		//读取数据
		List<ActionProduct> products = actionProductDao.findProducts(product, pageBean.getStartIndex(),pageSize);
		//封装
		List<ActionProductListVo> voList = Lists.newArrayList();
		for(ActionProduct p : products) {
			voList.add(createProductListVo(p));
		}
		pageBean.setData(voList);
		return SverResponse.createRespBySuccess(pageBean);
	}
	
	//封装vo对象
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
