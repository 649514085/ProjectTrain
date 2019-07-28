package cn.techaction.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionCartDao;
import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.ActionCart;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionCartService;
import cn.techaction.utils.CalcUtil;
import cn.techaction.vo.ActionCartVo;
import cn.techaction.vo.ActionCartsListVo;

@Service
public class ActionCartServiceImpl implements ActionCartService {
	
	@Autowired
	private ActionCartDao actionCartDao;
	
	@Autowired
	private ActionProductDao actionProductDao;

	@Override
	public SverResponse<String> saveOrUpdate(String userId, String productId, Integer count) {
		//验证参数是否正确
		if(userId==null||productId==null||count==null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		//查看用户的购物车中是否存在商品
		ActionCart actionCart = actionCartDao.findCartByUserAndProductId(userId, productId);
		if(actionCart==null) {
			//不存在则新增
			ActionCart cart = new ActionCart();
			cart.setUser_id(userId);
			cart.setProduct_id(productId);
			cart.setQuantity(count);
			cart.setProduct_name(actionProductDao.findProductById(productId).getProduct_name());
			actionCartDao.insertCart(cart);
		}else {
			//存在则数量增加
			int num = actionCart.getQuantity()+count;
			actionCart.setQuantity(num);
			actionCartDao.updateCart(actionCart);
		}
		return SverResponse.createRespBySuccessMessage("商品已成功添加到购物车！");
	}

	@Override
	public SverResponse<ActionCartVo> findAllCarts(String user_id) {
		if(user_id==null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		//查找用户购物车中的商品
		List<ActionCart> list = actionCartDao.findCartByUserId(user_id);
		//封装actionCartVo对象
		ActionCartVo actionCartVo = createCartVo(list);
		return SverResponse.createRespBySuccess(actionCartVo);
	}
	
	/**
	 * 封装购物车vo对象
	 * @param list
	 * @return
	 */
	private ActionCartVo createCartVo(List<ActionCart> carts) {
		ActionCartVo actionCartVo = new ActionCartVo();
		List<ActionCartsListVo> list = Lists.newArrayList();
		//购物车的商品总价格
		BigDecimal cartTotalPrice = new BigDecimal("0");
		if(CollectionUtils.isNotEmpty(carts)) {
			for(ActionCart cart : carts) {
				//转换对象
				ActionCartsListVo listVo = new ActionCartsListVo();
				listVo.setUser_id(cart.getUser_id());
				listVo.setProduct_id(cart.getProduct_id());
				listVo.setChecked(cart.getChecked());
				//封装商品信息
				ActionProduct product = actionProductDao.findProductById(listVo.getProduct_id());
				if(product!=null) {
					listVo.setProduct_name(product.getProduct_name());
					listVo.setStatus(product.getStatus());
					listVo.setPrice(product.getPrice());
					listVo.setStock(product.getStock());
					//判断库存
					int buyCount = 0;
					if(product.getStock()>=cart.getQuantity()) {
						buyCount = cart.getQuantity();
					}
					else {
						buyCount = product.getStock();
						//更新购物车中商品数量
						ActionCart updateCart = new ActionCart();
						updateCart.setUser_id(cart.getUser_id());
						updateCart.setProduct_id(cart.getProduct_id());
						updateCart.setQuantity(buyCount);
						//更新选中状态
						updateCart.setChecked(cart.getChecked());
						actionCartDao.updateCart(updateCart);
					}
					listVo.setQuantity(buyCount);
					//计算购物车中某个商品的总价格
					BigDecimal totalPrice = CalcUtil.mul(listVo.getPrice().doubleValue(), listVo.getQuantity().doubleValue());
					listVo.setTotalPrice(totalPrice);
					if(cart.getChecked()==1) {
						//计算购物车选中状态商品的总价格
						cartTotalPrice = CalcUtil.add(cartTotalPrice.doubleValue(), listVo.getTotalPrice().doubleValue());
					}
				}
				list.add(listVo);
			}
		}
		actionCartVo.setLists(list);
		actionCartVo.setTotalPrice(cartTotalPrice);
		return actionCartVo;
	}

	@Override
	public SverResponse<String> clearCarts(String user_id) {
		//判断参数正确
		if(user_id==null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		//清空购物车
		int rs = actionCartDao.deleteCartByUserId(user_id);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("成功清空购物车！");
		}
		return SverResponse.createByErrorMessage("清空购物车失败！");
	}

	@Override
	public SverResponse<ActionCartVo> updateCart(String user_id, String productId, Integer count, Integer checked) {
		//判断参数
		if(user_id==null || productId==null || count==null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		//更新购物车信息
		ActionCart actionCart = new ActionCart();
		actionCart.setUser_id(user_id);
		actionCart.setProduct_id(productId);
		actionCart.setQuantity(count);
		actionCart.setChecked(checked);
		actionCartDao.updateCartByUserAndProductId(actionCart);
		//返回所有购物车信息
		return findAllCarts(user_id);
	}

	@Override
	public SverResponse<ActionCartVo> deleteCart(String user_id, String productId) {
		//判断参数
		if(user_id==null || productId == null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		//删除商品
		int rs = actionCartDao.deleteCart(user_id, productId);
		if(rs>0) {
			//返回所有购物车信息
			return this.findAllCarts(user_id);
		}
		return SverResponse.createByErrorMessage("删除商品失败！");
	}

	@Override
	public SverResponse<Integer> getCartsCount(String user_id) {
		//判断参数
		if(user_id==null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		int count = actionCartDao.getCartsCountByUserId(user_id);
		return SverResponse.createRespBySuccess(Integer.valueOf(count));
	}

	@Override
	public SverResponse<ActionCartVo> findAllCheckedCarts(String user_id) {
		if(user_id==null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		//查找用户购物车中的商品
		List<ActionCart> list = actionCartDao.findCheckedCartsByUserId(user_id);
		//封装actionCartVo对象
		ActionCartVo actionCartVo = createCartVo(list);
		return SverResponse.createRespBySuccess(actionCartVo);
	}

	@Override
	public SverResponse<String> clearCheckedStatus(String user_id) {
		//判断参数
		if(user_id==null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		actionCartDao.clearCheckedStatus(user_id);
		return SverResponse.createRespBySuccessMessage("购物车全部不选中设置成功！");
	}

}
