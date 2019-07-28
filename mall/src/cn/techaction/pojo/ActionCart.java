package cn.techaction.pojo;

public class ActionCart {
	private String user_id;
	private String product_id;
	private String product_name;
	private Integer quantity;
	private Integer checked;

	public ActionCart(String user_id, String product_id, String product_name, Integer quantity) {
		super();
		this.user_id = user_id;
		this.product_id = product_id;
		this.product_name = product_name;
		this.quantity = quantity;
	}
	
	public ActionCart() {
		super();
	}
	
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
	public Integer getChecked() {
		return checked;
	}
	public void setChecked(Integer checked) {
		this.checked = checked;
	}
	
}
