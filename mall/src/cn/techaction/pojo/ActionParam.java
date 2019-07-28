package cn.techaction.pojo;

import java.util.List;

public class ActionParam {
	private Integer type_id;
	private String type_name;
	private Integer parent_id;
	private Integer gparent_id;
	private List<ActionParam> children;
	
	public List<ActionParam> getChildren() {
		return children;
	}
	public void setChildren(List<ActionParam> children) {
		this.children = children;
	}
	public Integer getType_id() {
		return type_id;
	}
	public void setType_id(Integer type_id) {
		this.type_id = type_id;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	public Integer getGparent_id() {
		return gparent_id;
	}
	public void setGparent_id(Integer gparent_id) {
		this.gparent_id = gparent_id;
	}

}
