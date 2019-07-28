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
		//�ж�user_id�Ƿ�Ϊ��
		if(user_id==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		//���ҷ����������ܼ�¼��
		int totalRecord = acionOrderDao.getTotalRecord(user_id, status);
		//������ҳ��װ����
		PageBean<ActionOrderVo> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
		//��ȡ����
		List<ActionOrder> orders = acionOrderDao.findOrders(user_id, status, pageBean.getStartIndex(), pageSize); 
		//��װvo
		List<ActionOrderVo> voList = Lists.newArrayList();
		for(ActionOrder order : orders) {
			voList.add(createOrderVo1(order, false));
		}
		
		pageBean.setData(voList);
		return SverResponse.createRespBySuccess(pageBean);
	}
	
	@Override
	public SverResponse<ActionOrderVo> generateOrder(String user_id, Integer addrId) {
		//��ȡ���ﳵ����Ʒ��Ϣ
		List<ActionCart> carts = actionCartDao.findCheckedCartsByUserId(user_id); 
		//���㹺�ﳵ��ÿ����Ʒ�۸����ɶ�����
		SverResponse response = this.cart2OrderItem(user_id, carts);
		if(!response.isSuccess()) {
			return response;
		}
		//ȡ���������еļ۸񣬼��㶩���ܼ۸�
		List<ActionOrderItem> orderItems = (List<ActionOrderItem>) response.getData();
		BigDecimal totalPrice = this.calcOrderTotalPrice(orderItems);
		//���ɶ�������������
		ActionOrder order = saveOrder(user_id, addrId, totalPrice);
		int rs = acionOrderDao.insertOrder(order);
		if(rs<=0) {
			return SverResponse.createByErrorMessage("����������������������ύ��");
		}
		if(CollectionUtils.isEmpty(orderItems)) {
			return SverResponse.createByErrorMessage("������Ϊ�գ���ѡ��Ҫ�������Ʒ��");
		}
		//�������붩����
		for(ActionOrderItem orderItem : orderItems) {
			//Ϊ���������ö���id
			orderItem.setOrder_id(order.getOrder_id());
		}
		actionOrderItemDao.batchInsert(orderItems);
		//������Ʒ���п��
		for(ActionOrderItem orderItem : orderItems) {
			ActionProduct product = actionProductDao.findProductById(orderItem.getProduct_id());
			//���ٿ��
			product.setStock(product.getStock()-orderItem.getQuantity());
			//���¿��
			actionProductDao.updateProduct(product);
		}
		//��չ��ﳵ
		actionCartDao.deleteCheckedCartsByUserId(user_id); 
		//��װ����ǰ��
		ActionOrderVo orderVo = createOrderVo(order, orderItems);
		return SverResponse.createRespBySuccess(orderVo);
	}

	/**
	 * ��װvo
	 * @param order
	 * @param hasAddress
	 * @return
	 */
	private ActionOrderVo createOrderVo1(ActionOrder order, boolean hasAddress) {
		ActionOrderVo orderVo = new ActionOrderVo();
		setNormalProperty(order, orderVo);
		setAddressProperty(order, orderVo, hasAddress);
		//���ö�����
		List<ActionOrderItem> orderItems = actionOrderItemDao.getItemsByOrderId(order.getOrder_id());
		setOrderItemProperty(orderItems, orderVo);
		return orderVo;
	}
	
	/**
	 * ��װ����vo
	 * @param order
	 * @param orderItems
	 * @return
	 */
	private ActionOrderVo createOrderVo(ActionOrder order, List<ActionOrderItem> orderItems) {
		ActionOrderVo orderVo = new ActionOrderVo();
		setNormalProperty(order, orderVo);
		setAddressProperty(order, orderVo, true);
		//���ö�����
		setOrderItemProperty(orderItems, orderVo);
		return orderVo;
	}
	
	/**
	 * ��װ����������
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
	 * ��װ������vo
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
	 * ��װ��ַ����
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
	 * ��װ��ַvo
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
	 * ��װ��������ͨvo����
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
	 * ���涩��
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
	 * ���㶩���ܼ۸�
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
	 * �����ﳵ����Ʒ��װΪ������
	 * @param user_id
	 * @param carts
	 * @return
	 */
	private SverResponse cart2OrderItem(String user_id, List<ActionCart> carts) {
		List<ActionOrderItem> items = Lists.newArrayList();
		//�жϹ��ﳵ�Ƿ�Ϊ��
		if(CollectionUtils.isEmpty(carts)) {
			return SverResponse.createByErrorMessage("���ﳵΪ�գ���ѡ��Ҫ�������Ʒ��");
		}
		for(ActionCart cart : carts) {
			//�鿴���ﳵ��Ʒ״̬
			ActionProduct product = actionProductDao.findProductById(cart.getProduct_id());
			//�ж���Ʒ�Ƿ��ϼ�����
			if(ConstUtil.ProductStatus.STATUS_ON_SALE!=product.getStatus()) {
				return SverResponse.createByErrorMessage("��Ʒ"+product.getProduct_name()+"�Ѿ��¼ܣ����ܹ���");
			}
			//�ж���Ʒ����Ƿ����
			if(cart.getQuantity()>product.getStock()) {
				return SverResponse.createByErrorMessage("��Ʒ"+product.getProduct_name()+"��治�㣡");
			}
			//��װ������
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
		//��ѯ����
		ActionOrder order = acionOrderDao.findOrderByUserAndOrderId(user_id, orderId);
		//�ж϶����Ƿ����
		if(order==null) {
			return SverResponse.createByErrorMessage("���û����������ڻ��Ѿ�ɾ����");
		}
		//�ж϶����Ƿ��Ѹ���
		if(order.getStatus()==ConstUtil.OrderStatus.ORDER_PAID) {
			return SverResponse.createByErrorMessage("�ö����Ѿ�����޷�ȡ����");
		}
		//�ж�״̬�޸Ķ�����Ϣ
		ActionOrder updateOrder = new ActionOrder();
		updateOrder.setOrder_id(order.getOrder_id());
		
		if(order.getStatus() == ConstUtil.OrderStatus.ORDER_NO_PAY) {
			updateOrder.setStatus(ConstUtil.OrderStatus.ORDER_CANCELED);
			int rs = acionOrderDao.updateOrder(updateOrder);
			if(rs>0) {
				return SverResponse.createRespBySuccessMessage("�����Ѿ�ȡ����");
			}
		}
		return SverResponse.createByErrorMessage("����ȡ��ʧ�ܣ�");
	}
	
	

	@Override
	public SverResponse<String> payOrder(String user_id, String orderId) {
		//��ѯ����
		ActionOrder order = acionOrderDao.findOrderByUserAndOrderId(user_id, orderId);
		//�ж϶����Ƿ����
		if(order==null) {
			return SverResponse.createByErrorMessage("���û����������ڻ��Ѿ�ɾ����");
		}
		//�ж϶����Ƿ��Ѹ���
		if(order.getStatus()==ConstUtil.OrderStatus.ORDER_PAID) {
			return SverResponse.createByErrorMessage("�ö����Ѿ��������֧����");
		}
		//�ж�״̬�޸Ķ�����Ϣ
		ActionOrder updateOrder = new ActionOrder();
		updateOrder.setOrder_id(order.getOrder_id());
		
		if(order.getStatus() == ConstUtil.OrderStatus.ORDER_NO_PAY) {
			updateOrder.setStatus(ConstUtil.OrderStatus.ORDER_PAID);
			updateOrder.setPay_time(new Date());
			int rs = acionOrderDao.updateOrder(updateOrder);
			if(rs>0) {
				return SverResponse.createRespBySuccessMessage("�����Ѿ�֧����");
			}
		}
		return SverResponse.createByErrorMessage("֧��ʧ�ܣ�");
	}

	@Override
	public SverResponse<ActionOrderVo> findOrderDetail(String user_id, String orderId) {
		//�жϲ����Ƿ���ȷ
		if(user_id==null || orderId == null) {
			return SverResponse.createByErrorMessage("��������");
		}
		//���Ҷ��������з�װ
		ActionOrder order = acionOrderDao.findOrderByUserAndOrderId(user_id, orderId);
		if(order==null) {
			return SverResponse.createByErrorMessage("���û����������ڻ���ɾ����");
		}
		ActionOrderVo orderVo = createOrderVo1(order, true);
		return SverResponse.createRespBySuccess(orderVo);
	}

	@Override
	public SverResponse<String> confirmOrder(String user_id, String orderId) {
		//��ѯ����
		ActionOrder order = acionOrderDao.findOrderByUserAndOrderId(user_id, orderId);
		//�ж϶����Ƿ����
		if(order==null) {
			return SverResponse.createByErrorMessage("���û����������ڻ��Ѿ�ɾ����");
		}
		
		//�ж϶����Ƿ��Ѹ���
		if(order.getStatus()<ConstUtil.OrderStatus.ORDER_SHIPPED) {
			return SverResponse.createByErrorMessage("�ö�����δ�����򸶿�޷�ȷ���ջ���");
		}
		
		ActionOrder updateOrder = new ActionOrder();
		updateOrder.setOrder_id(order.getOrder_id());
		
		if(order.getStatus()==ConstUtil.OrderStatus.ORDER_SHIPPED) {
			updateOrder.setStatus(ConstUtil.OrderStatus.ORDER_SUCCESS);
			updateOrder.setFinish_time(new Date());
			int rs = acionOrderDao.updateOrder(updateOrder);
			if(rs>0) {
				return SverResponse.createRespBySuccessMessage("�����Ѿ�ȷ���ջ���");
			}
		}
		return SverResponse.createByErrorMessage("ȷ���ջ�ʧ�ܣ�");
	}
}
