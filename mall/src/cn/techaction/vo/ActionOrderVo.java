package cn.techaction.vo;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class ActionOrderVo {
	private Integer id;
	private String orderId;			//�������
	private BigDecimal amount;		//�����۸�
	private Integer status;			//����״̬
	private String statusDes;		//����״̬����
	private String create_time;		//�µ�ʱ��
	private String payTime;			//֧��ʱ��
	private String deliveryTime;	//�ʼ�ʱ��
	private String finishTime;		//�������ʱ��
	
	private List<ActionOrderItemVo> orderItems;//��������
	private Integer addrId;//�ջ���ַID
	private String deliveryName;//�ջ�������
	private ActionAddressVo address;//�ջ�������
	
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
