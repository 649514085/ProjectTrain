package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.Type;

public interface ActionTypeDao {

	public List<Type> findTypeById(Integer type_id);
	public Type findTypeByIdOne(Integer type_id);
	
	public List<Type> findRootType();
	
	public List<Type> findSubTypeOfType(Integer type_id);
	public List<Type> findTypeByRank(Integer rank);
	public List<Type> findTypeByName(String type_name);
	
	public int delType(Integer type_id);
	
	public Type findTypeByParentAndName(Integer parent_id,String type_name);
	
	public int addType(Type type);
	
	public int updateType(Type type);
}
