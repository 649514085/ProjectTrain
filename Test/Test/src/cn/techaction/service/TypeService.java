package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.Order;
import cn.techaction.pojo.Type;
import cn.techaction.vo.TypeVo;

public interface TypeService {

	//当把类型展示在类型管理主界面时，展示的是Type
	public SverResponse<List<Type>> findRoot();
	public SverResponse<List<Type>> findSubType(Integer type_id);
	
	//当为产品选择类型时，展示的是TypeVo，即把编号和名称结合起来。
	public SverResponse<List<TypeVo>> findRootVo();
	public SverResponse<List<TypeVo>> findSubTypeVo(Integer type_id);
	public SverResponse<List<Type>> findTypeByRank(Integer rank);
	//搜索时展示在类型管理主界面，所以应为Type
	public SverResponse<List<TypeVo>> findTypeByTid(Integer type_id);
	public SverResponse<List<TypeVo>> findTypeByName(String type_name);

	public SverResponse<String> delType(Integer type_id);
	public SverResponse<String> addType(TypeVo typeVo);
	public SverResponse<String> updateType(TypeVo typeVo);
}
