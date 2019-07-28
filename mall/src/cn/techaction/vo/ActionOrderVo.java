package cn.techaction.vo;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class ActionOrderVo {
	private Integer id;
	private String orderId;			//订单编号
	private BigDecimal amount;		//订单价格
	private Integer status;			//订单状态
	private String statusDes;		//订单状态描述
	private String create_time;		//下单时间
	private String payTime;			//支付时间
	private String deliveryTime;	//邮寄时间
	private String finishTime;		//交易完成时间
	
	private List<ActionOrderItemVo> orderItems;//订单详情
	private Integer addrId;//收货地址ID
	private String deliveryName;//收货人姓名
	private ActionAddressVo address;//收货人详情
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatusDes() {
		return statusDes;
	}
	public void setStatusDes(String statusDes) {
		this.statusDes = statusDes;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getDeliveryTime() {
		return deliveryTime; 
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	public List<ActionOrderItemVo> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<ActionOrderItemVo> orderItems) {
		this.orderItems = orderItems;
	}
	public Integer getAddrId() {
		return addrId;
	}
	public void setAddrId(Integer addrId) {
		this.addrId = addrId;
	}
	public String getDeliveryName() {
		return deliveryName;
	}
	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}
	public ActionAddressVo getAddress() {
		return address;
	}
	public void setAddress(ActionAddressVo address) {
		this.address = address;
	}
	
}
