package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.Order;
import cn.techaction.pojo.Type;
import cn.techaction.vo.TypeVo;

public interface TypeService {

	//��������չʾ�����͹���������ʱ��չʾ����Type
	public SverResponse<List<Type>> findRoot();
	public SverResponse<List<Type>> findSubType(Integer type_id);
	
	//��Ϊ��Ʒѡ������ʱ��չʾ����TypeVo�����ѱ�ź����ƽ��������
	public SverResponse<List<TypeVo>> findRootVo();
	public SverResponse<List<TypeVo>> findSubTypeVo(Integer type_id);
	public SverResponse<List<Type>> findTypeByRank(Integer rank);
	//����ʱչʾ�����͹��������棬����ӦΪType
	public SverResponse<List<TypeVo>> findTypeByTid(Integer type_id);
	public SverResponse<List<TypeVo>> findTypeByName(String type_name);

	public SverResponse<String> delType(Integer type_id);
	public SverResponse<String> addType(TypeVo typeVo);
	public SverResponse<String> updateType(TypeVo typeVo);
}
