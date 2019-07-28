package cn.techaction.vo;

import java.math.BigDecimal;

public class ActionCartsListVo {
	
	private String user_id;
	private String product_id;		//��Ʒ���
	private String product_name;	//��Ʒ����
	private Integer quantity;		//��Ʒ����
	private BigDecimal price;		//��Ʒ�۸�
	private Integer status;			//��Ʒ״̬
	private BigDecimal totalPrice;	//��Ʒ�ܼ�
	private Integer stock;			//��Ʒ���
	private Integer checked;		//�Ƿ�ѡ�У�1Ϊѡ�У�0Ϊδѡ�У�
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Integer getChecked() {
		return checked;
	}
	public void setChecked(Integer checked) {
		this.checked = checked;
	}
	
}
