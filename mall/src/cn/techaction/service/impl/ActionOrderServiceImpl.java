package cn.techaction.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.Date;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionAddressDao;
import cn.techaction.dao.ActionCartDao;
import cn.techaction.dao.ActionOrderDao;
import cn.techaction.dao.ActionOrderItemDao;
import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.ActionAddress;
import cn.techaction.pojo.ActionCart;
import cn.techaction.pojo.ActionOrder;
import cn.techaction.pojo.ActionOrderItem;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionOrderService;
import cn.techaction.utils.CalcUtil;
import cn.techaction.utils.ConstUtil;
import cn.techaction.utils.DateUtils;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionAddressVo;
import cn.techaction.vo.ActionOrderItemVo;
import cn.techaction.vo.ActionOrderVo;

@Service
public class ActionOrderServiceImpl implements ActionOrderService {

	@Autowired
	private ActionOrderDao acionOrderDao;
	
	@Autowired
	private ActionAddressDao actionAddressDao;
	
	@Autowired
	private ActionOrderItemDao actionOrderItemDao;
	
	@Autowired
	private ActionCartDao actionCartDao;
	
	@Autowired
	private ActionProductDao actionProductDao;

	@Override
	public SverResponse<PageBean<ActionOrderVo>> findOrders(String user_id, Integer status, int pageNum, int pageSize) {
		//判断user_id是否为空
		if(user_id==null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		//查找符合条件的总记录数
		int totalRecord = acionOrderDao.getTotalRecord(user_id, status);
		//创建分页封装对象
		PageBean<ActionOrderVo> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
		//读取数据
		List<ActionOrder> orders = acionOrderDao.findOrders(user_id, status, pageBean.getStartIndex(), pageSize); 
		//封装vo
		List<ActionOrderVo> voList = Lists.newArrayList();
		for(ActionOrder order : orders) {
			voList.add(createOrderVo1(order, false));
		}
		
		pageBean.setData(voList);
		return SverResponse.createRespBySuccess(pageBean);
	}
	
	@Override
	public SverResponse<ActionOrderVo> generateOrder(String user_id, Integer addrId) {
		//提取购物车中商品信息
		List<ActionCart> carts = actionCartDao.findCheckedCartsByUserId(user_id); 
		//计算购物车中每件商品价格并生成订单项
		SverResponse response = this.cart2OrderItem(user_id, carts);
		if(!response.isSuccess()) {
			return response;
		}
		//取出订单项中的价格，计算订单总价格
		List<ActionOrderItem> orderItems = (List<ActionOrderItem>) response.getData();
		BigDecimal totalPrice = this.calcOrderTotalPrice(orderItems);
		//生成订单，插入数据
		ActionOrder order = saveOrder(user_id, addrId, totalPrice);
		int rs = acionOrderDao.insertOrder(order);
		if(rs<=0) {
			return SverResponse.createByErrorMessage("订单产生错误，请检查后重新提交！");
		}
		if(CollectionUtils.isEmpty(orderItems)) {
			return SverResponse.createByErrorMessage("订单项为空，请选择要购买的商品！");
		}
		//批量插入订单项
		for(ActionOrderItem orderItem : orderItems) {
			//为订单项设置订单id
			orderItem.setOrder_id(order.getOrder_id());
		}
		actionOrderItemDao.batchInsert(orderItems);
		//减少商品表中库存
		for(ActionOrderItem orderItem : orderItems) {
			ActionProduct product = actionProductDao.findProductById(orderItem.getProduct_id());
			//减少库存
			product.setStock(product.getStock()-orderItem.getQuantity());
			//更新库存
			actionProductDao.updateProduct(product);
		}
		//清空购物车
		actionCartDao.deleteCheckedCartsByUserId(user_id); 
		//封装返回前端
		ActionOrderVo orderVo = createOrderVo(order, orderItems);
		return SverResponse.createRespBySuccess(orderVo);
	}

	/**
	 * 封装vo
	 * @param order
	 * @param hasAddress
	 * @return
	 */
	private ActionOrderVo createOrderVo1(ActionOrder order, boolean hasAddress) {
		ActionOrderVo orderVo = new ActionOrderVo();
		setNormalProperty(order, orderVo);
		setAddressProperty(order, orderVo, hasAddress);
		//设置订单项
		List<ActionOrderItem> orderItems = actionOrderItemDao.getItemsByOrderId(order.getOrder_id());
		setOrderItemProperty(orderItems, orderVo);
		return orderVo;
	}
	
	/**
	 * 封装订单vo
	 * @param order
	 * @param orderItems
	 * @return
	 */
	private ActionOrderVo createOrderVo(ActionOrder order, List<ActionOrderItem> orderItems) {
		ActionOrderVo orderVo = new ActionOrderVo();
		setNormalProperty(order, orderVo);
		setAddressProperty(order, orderVo, true);
		//设置订单项
		setOrderItemProperty(orderItems, orderVo);
		return orderVo;
	}
	
	/**
	 * 封装订单项属性
	 * @param orderItems
	 * @param orderVo
	 */
	private void setOrderItemProperty(List<ActionOrderItem> orderItems, ActionOrderVo orderVo) {
		List<ActionOrderItemVo> items = Lists.newArrayList();
		for(ActionOrderItem orderItem : orderItems) {
			items.add(createOrderItemVo(orderItem));
		}
		orderVo.setOrderItems(items);
	}

	/**
	 * 封装订单项vo
	 * @param orderItem
	 * @return
	 */
	private ActionOrderItemVo createOrderItemVo(ActionOrderItem orderItem) {
		ActionOrderItemVo itemVo = new ActionOrderItemVo();
		itemVo.setOrderId(orderItem.getOrder_id());
		itemVo.setProductId(orderItem.getProduct_id());
		itemVo.setProductName(orderItem.getProduct_name());
		itemVo.setCurPrice(orderItem.getPrice());
		itemVo.setIconUrl(orderItem.getIconUrl());
		itemVo.setQuantity(orderItem.getQuantity());
		itemVo.setTotalPrice(CalcUtil.mul(orderItem.getPrice().doubleValue(), orderItem.getQuantity().doubleValue()));
		return itemVo;
	}

	/**
	 * 封装地址属性
	 * @param order
	 * @param orderVo
	 * @param hasAddress
	 */
	private void setAddressProperty(ActionOrder order, ActionOrderVo orderVo, boolean hasAddress) {
		ActionAddress address = actionAddressDao.findAddrById(order.getAddr_id());
		if(address!=null) {
			orderVo.setDeliveryName(address.getName());
			if(hasAddress) {
				orderVo.setAddress(createAddressVo(address));
			}else {
				orderVo.setAddress(null);
			}
		}
	}
	/**
	 * 封装地址vo
	 * @param address
	 * @return
	 */
	private ActionAddressVo createAddressVo(ActionAddress address) {
		ActionAddressVo addressVo = new ActionAddressVo();
		addressVo.setName(address.getName());
		addressVo.setPhone(address.getPhone());
		addressVo.setCity(address.getCity());
		addressVo.setProvince(address.getProvince());
		addressVo.setDistrict(address.getDistrict());
		addressVo.setAddr(address.getAddr());
		return addressVo;
	}

	/**
	 * 封装订单的普通vo属性
	 * @param order
	 * @param orderVo
	 */
	private void setNormalProperty(ActionOrder order, ActionOrderVo orderVo) {
		orderVo.setId(order.getId());
		orderVo.setOrderId(order.getOrder_id());
		orderVo.setAmount(order.getAmount());
		orderVo.setStatus(order.getStatus());
		orderVo.setStatusDes(ConstUtil.OrderStatus.getStatusDesc(order.getStatus()));
		orderVo.setAddrId(order.getAddr_id());
		orderVo.setCreate_time(DateUtils.date2Str(order.getCreate_time()));
		orderVo.setPayTime(DateUtils.date2Str(order.getPay_time()));
		orderVo.setDeliveryTime(DateUtils.date2Str(order.getDelivery_time()));
		orderVo.setFinishTime(DateUtils.date2Str(order.getFinish_time()));
	}
	

	/**
	 * 保存订单
	 * @param user_id
	 * @param addrId
	 * @param totalPrice
	 * @return
	 */
	private ActionOrder saveOrder(String user_id, Integer addrId, BigDecimal totalPrice) {
		ActionOrder order = new ActionOrder();
		order.setUser_id(user_id);
		order.setAddr_id(addrId);
		order.setAmount(totalPrice);
		order.setCreate_time(new Date());
		order.setStatus(ConstUtil.OrderStatus.ORDER_NO_PAY);
		long currentTime = System.currentTimeMillis();
		Long orderId = currentTime + new Random().nextInt(100);
		order.setOrder_id(""+orderId);
		return order;
	}

	/**
	 * 计算订单总价格
	 * @param orderItems
	 * @return
	 */
	private BigDecimal calcOrderTotalPrice(List<ActionOrderItem> orderItems) {
		BigDecimal totalPrice = new BigDecimal("0");
		for(ActionOrderItem orderItem : orderItems) {
			BigDecimal cur = CalcUtil.mul(orderItem.getPrice().doubleValue(), orderItem.getQuantity().doubleValue());
			totalPrice = CalcUtil.add(totalPrice.doubleValue(), cur.doubleValue());
		}
		return totalPrice;
	}

	/**
	 * 将购物车中商品封装为订单项
	 * @param user_id
	 * @param carts
	 * @return
	 */
	private SverResponse cart2OrderItem(String user_id, List<ActionCart> carts) {
		List<ActionOrderItem> items = Lists.newArrayList();
		//判断购物车是否为空
		if(CollectionUtils.isEmpty(carts)) {
			return SverResponse.createByErrorMessage("购物车为空，请选择要购买的商品！");
		}
		for(ActionCart cart : carts) {
			//查看购物车商品状态
			ActionProduct product = actionProductDao.findProductById(cart.getProduct_id());
			//判断商品是否上架在售
			if(ConstUtil.ProductStatus.STATUS_ON_SALE!=product.getStatus()) {
				return SverResponse.createByErrorMessage("商品"+product.getProduct_name()+"已经下架，不能购买！");
			}
			//判断商品库存是否充足
			if(cart.getQuantity()>product.getStock()) {
				return SverResponse.createByErrorMessage("商品"+product.getProduct_name()+"库存不足！");
			}
			//封装订单项
			ActionOrderItem orderItem = new ActionOrderItem();
			orderItem.setProduct_id(product.getProduct_id());
			orderItem.setProduct_name(product.getProduct_name());
			orderItem.setPrice(product.getPrice());
			orderItem.setQuantity(cart.getQuantity());
			items.add(orderItem);
		}
		return SverResponse.createRespBySuccess(items);
	}

	@Override
	public SverResponse<String> cancelOrder(String user_id, String orderId) {
		//查询订单
		ActionOrder order = acionOrderDao.findOrderByUserAndOrderId(user_id, orderId);
		//判断订单是否存在
		if(order==null) {
			return SverResponse.createByErrorMessage("该用户订单不存在或已经删除！");
		}
		//判断订单是否已付款
		if(order.getStatus()==ConstUtil.OrderStatus.ORDER_PAID) {
			return SverResponse.createByErrorMessage("该订单已经付款，无法取消！");
		}
		//判断状态修改订单信息
		ActionOrder updateOrder = new ActionOrder();
		updateOrder.setOrder_id(order.getOrder_id());
		
		if(order.getStatus() == ConstUtil.OrderStatus.ORDER_NO_PAY) {
			updateOrder.setStatus(ConstUtil.OrderStatus.ORDER_CANCELED);
			int rs = acionOrderDao.updateOrder(updateOrder);
			if(rs>0) {
				return SverResponse.createRespBySuccessMessage("订单已经取消！");
			}
		}
		return SverResponse.createByErrorMessage("订单取消失败！");
	}
	
	

	@Override
	public SverResponse<String> payOrder(String user_id, String orderId) {
		//查询订单
		ActionOrder order = acionOrderDao.findOrderByUserAndOrderId(user_id, orderId);
		//判断订单是否存在
		if(order==null) {
			return SverResponse.createByErrorMessage("该用户订单不存在或已经删除！");
		}
		//判断订单是否已付款
		if(order.getStatus()==ConstUtil.OrderStatus.ORDER_PAID) {
			return SverResponse.createByErrorMessage("该订单已经付款，无需支付！");
		}
		//判断状态修改订单信息
		ActionOrder updateOrder = new ActionOrder();
		updateOrder.setOrder_id(order.getOrder_id());
		
		if(order.getStatus() == ConstUtil.OrderStatus.ORDER_NO_PAY) {
			updateOrder.setStatus(ConstUtil.OrderStatus.ORDER_PAID);
			updateOrder.setPay_time(new Date());
			int rs = acionOrderDao.updateOrder(updateOrder);
			if(rs>0) {
				return SverResponse.createRespBySuccessMessage("订单已经支付！");
			}
		}
		return SverResponse.createByErrorMessage("支付失败！");
	}

	@Override
	public SverResponse<ActionOrderVo> findOrderDetail(String user_id, String orderId) {
		//判断参数是否正确
		if(user_id==null || orderId == null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		//查找订单并进行封装
		ActionOrder order = acionOrderDao.findOrderByUserAndOrderId(user_id, orderId);
		if(order==null) {
			return SverResponse.createByErrorMessage("该用户订单不存在或已删除！");
		}
		ActionOrderVo orderVo = createOrderVo1(order, true);
		return SverResponse.createRespBySuccess(orderVo);
	}

	@Override
	public SverResponse<String> confirmOrder(String user_id, String orderId) {
		//查询订单
		ActionOrder order = acionOrderDao.findOrderByUserAndOrderId(user_id, orderId);
		//判断订单是否存在
		if(order==null) {
			return SverResponse.createByErrorMessage("该用户订单不存在或已经删除！");
		}
		
		//判断订单是否已付款
		if(order.getStatus()<ConstUtil.OrderStatus.ORDER_SHIPPED) {
			return SverResponse.createByErrorMessage("该订单还未发货或付款，无法确认收货！");
		}
		
		ActionOrder updateOrder = new ActionOrder();
		updateOrder.setOrder_id(order.getOrder_id());
		
		if(order.getStatus()==ConstUtil.OrderStatus.ORDER_SHIPPED) {
			updateOrder.setStatus(ConstUtil.OrderStatus.ORDER_SUCCESS);
			updateOrder.setFinish_time(new Date());
			int rs = acionOrderDao.updateOrder(updateOrder);
			if(rs>0) {
				return SverResponse.createRespBySuccessMessage("订单已经确认收货！");
			}
		}
		return SverResponse.createByErrorMessage("确认收货失败！");
	}
}
