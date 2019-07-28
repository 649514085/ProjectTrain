package cn.techaction.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionAddrDao;
import cn.techaction.dao.ActionOrderDao;
import cn.techaction.pojo.Addrs;
import cn.techaction.pojo.Order;
import cn.techaction.pojo.OrderItem;
import cn.techaction.service.OrderService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.vo.OrderVo;
@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private ActionOrderDao orderDao;
	@Autowired
	private ActionAddrDao addrDao;
	
	@Override
	public SverResponse<List<OrderVo>> findOrders(){
		List<Order> orders = orderDao.findOrders();
		
		List<OrderVo> vo = Lists.newArrayList(); 
		if (orders!=null) {
			for (Order order : orders) {
				vo.add(orederToOrderVo(order));
			}
		}
		return SverResponse.createRespBySuccess(vo);
	}
	
	@Override
	public SverResponse<List<OrderVo>> findOrderByOid(String order_id){
		List<Order> orders = orderDao.findOrdersByOid(order_id);
		
		List<OrderVo> vo = Lists.newArrayList(); 
		if (orders!=null) {
			for (Order order : orders) {
				vo.add(orederToOrderVo(order));
			}
			return SverResponse.createRespBySuccess(vo);
		}
		
		return SverResponse.createRespByError();
	}
	
	@Override
	public SverResponse<List<OrderVo>> findOrderByUid(String user_id){
		List<Order> orders = orderDao.findOrderByUid(user_id);
		
		List<OrderVo> vo = Lists.newArrayList(); 
		if (orders!=null) {
			for (Order order : orders) {
				vo.add(orederToOrderVo(order));
			}
			return SverResponse.createRespBySuccess(vo);
		}
		return SverResponse.createRespByError();
	}
	
	@Override
	public SverResponse<List<OrderItem>> findOrderItem(String order_id){
		List<OrderItem> orderItems = orderDao.searchOrderItems(order_id);
		
		if (orderItems!=null) {
			return SverResponse.createRespBySuccess(orderItems);
		}
		return null;
	}
	
	public OrderVo orederToOrderVo(Order order) {
		//order.setOrder_id("2");
		OrderVo orderVo = new OrderVo();
		orderVo.setOrder_id(order.getOrder_id());
		orderVo.setUser_id(order.getUser_id());
		
		if (order.getStatus()==ConstUtil.OrderStatus.ORDER_NO_PAY) {
			orderVo.setStatus("δ����");
		}
		if (order.getStatus()==ConstUtil.OrderStatus.ORDER_PAID) {
			orderVo.setStatus("�Ѹ���");
		}
		if (order.getStatus()==ConstUtil.OrderStatus.ORDER_SHIPPED) {
			orderVo.setStatus("�ѷ���");
		}
		if (order.getStatus()==ConstUtil.OrderStatus.ORDER_SUCCESS) {
			orderVo.setStatus("���׳ɹ�");
		}
		if (order.getStatus()==ConstUtil.OrderStatus.ORDER_CANCELED) {
			orderVo.setStatus("����ȡ��");
		}
		
		orderVo.setAmount(order.getAmount());
		orderVo.setPay_time(order.getPay_time());
		orderVo.setDelivery_time(order.getDelivery_time());
		orderVo.setFinish_time(order.getFinish_time());
		
		//���õ�ַ�ջ��ˡ���ַ��
		Addrs addrs = addrDao.findAddrs(order.getAddr_id());
		orderVo.setReceiver(addrs.getName());
		orderVo.setPhone(addrs.getPhone());
		
		String addrString = "";
		//��ʡ�е��ֶ���ϳɾ����ַ
		addrString = addrString+addrs.getProvince()+addrs.getCity()+addrs.getDistrict()+addrs.getAddr();
		orderVo.setAddr(addrString);
		
		return orderVo;
	}
}
