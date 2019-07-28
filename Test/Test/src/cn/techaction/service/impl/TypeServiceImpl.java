package cn.techaction.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionProductDao;
import cn.techaction.dao.ActionTypeDao;
import cn.techaction.pojo.Order;
import cn.techaction.pojo.Product;
import cn.techaction.pojo.Type;
import cn.techaction.service.TypeService;
import cn.techaction.vo.TypeVo;
import sun.font.CreatedFontTracker;

@Service
public class TypeServiceImpl implements TypeService{
	@Autowired
	private ActionTypeDao typeDao;
	@Autowired
	private ActionProductDao productDao;
	
	@Override
	public SverResponse<List<Type>> findRoot(){
		List<Type> types =  typeDao.findRootType();
		return SverResponse.createRespBySuccess(types);
	}
	

	@Override
	public SverResponse<List<TypeVo>> findRootVo() {
		List<Type> types =  typeDao.findRootType();
		
		List<TypeVo> typeVos = Lists.newArrayList();
		for (Type type : types) {
			typeVos.add(typeToTypeVo(type));
		}
		return SverResponse.createRespBySuccess(typeVos);
	}
	
	@Override
	public SverResponse<List<TypeVo>> findTypeByTid(Integer type_id) {
		List<Type> types = typeDao.findTypeById(type_id);
		List<TypeVo> typeVos = Lists.newArrayList();
		for (Type type : types) {
			typeVos.add(typeToTypeVo(type));
		}
		return SverResponse.createRespBySuccess(typeVos);
	}
	
	@Override
	public SverResponse<List<TypeVo>> findSubTypeVo(Integer type_id) {
		List<Type> types =  typeDao.findSubTypeOfType(type_id);
		
		List<TypeVo> typeVos = Lists.newArrayList();
		for (Type type : types) {
			typeVos.add(typeToTypeVo(type));
		}
		return SverResponse.createRespBySuccess(typeVos);
	}
	
	@Override
	public SverResponse<List<TypeVo>> findTypeByName(String type_name) {
		String sql = "%"+type_name+"%";
		List<Type> types = typeDao.findTypeByName(sql);
		List<TypeVo> typeVos = Lists.newArrayList();
		for (Type type : types) {
			typeVos.add(typeToTypeVo(type));
		}
		return SverResponse.createRespBySuccess(typeVos);
	}
	
	@Override
	public SverResponse<List<Type>> findTypeByRank(Integer rank) {
		List<Type> types = typeDao.findTypeByRank(rank);
		return SverResponse.createRespBySuccess(types);
	}
	
	
	@Override
	public SverResponse<String> delType(Integer type_id){
		//若类型包含子类型或者该类型目录下已有产品，则不能删除
		
		//判断有无子类型
		List<Type> types = typeDao.findSubTypeOfType(type_id);
		if (types.size()>0) {
			return SverResponse.createByErrorMessage("该类型包含子类型，无法删除！");
		}
		//判断类型下是否已包含产品
		List<Product> products = productDao.findProductOfType(type_id);
		if (products.size()>0) {
			return SverResponse.createByErrorMessage("该类型已包含商品，无法删除！");
		}
		
		int rs = typeDao.delType(type_id);
		if (rs>0) {
			return SverResponse.createRespBySuccess("类型删除成功！");
		}else {
			return SverResponse.createByErrorMessage("类型删除失败！");
		}
	}
	
	@Override
	public SverResponse<String> addType(TypeVo typeVo){
		if (typeVo.getType_name()==null) {
			return SverResponse.createByErrorMessage("参数非法！");
		}
		
		Type temp = typeVoToType(typeVo);
		//判断在同一父类型中是否重名
		Type ty = typeDao.findTypeByParentAndName(temp.getParent_id(), temp.getType_name());
		if (ty!=null) {
			return SverResponse.createByErrorMessage("该类型与已有类型重名！");
		}
		
		int rs = typeDao.addType(temp);
		if (rs>0) {
			return SverResponse.createRespBySuccess("类型添加成功！");
		}else {
			return SverResponse.createByErrorMessage("类型添加失败！");
		}
	}
	
	@Override
	public SverResponse<String> updateType(TypeVo typeVo){
		if (typeVo==null||typeVo.getType_id()==null) {
			return SverResponse.createByErrorMessage("参数非法！");
		}
		Type temp = typeVoToType(typeVo);
		//判断在同一父类型中是否重名
		Type ty = typeDao.findTypeByParentAndName(temp.getParent_id(), temp.getType_name());
		if (ty!=null) {
			return SverResponse.createByErrorMessage("该类型与已有类型重名！");
		}
		
		int rs = typeDao.updateType(temp);
		if (rs>0) {
			return SverResponse.createRespBySuccess("类型修改成功！");
		}else {
			return SverResponse.createByErrorMessage("类型修改失败！");
		}
	}
	
	public TypeVo typeToTypeVo(Type type) {
		TypeVo typeVo = new TypeVo();
		typeVo.setType_id(type.getType_id());
		typeVo.setType_name(type.getType_name());
		typeVo.setParent_id(type.getParent_id());
		typeVo.setGparent_id(type.getGparent_id());
		typeVo.setRank(type.getRank());
		return typeVo;
	}
	
	public Type typeVoToType(TypeVo typeVo) {
		Type type = new Type();
		type.setType_id(typeVo.getType_id());
		type.setType_name(typeVo.getType_name());
		type.setParent_id(typeVo.getParent_id());
		type.setGparent_id(typeVo.getGparent_id());
		type.setRank(typeVo.getRank());
		return type;
	}



	@Override
	public SverResponse<List<Type>> findSubType(Integer type_id) {
		List<Type> types = typeDao.findSubTypeOfType(type_id);
		return SverResponse.createRespBySuccess(types);
	}

	


}
