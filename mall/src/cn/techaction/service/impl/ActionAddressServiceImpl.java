package cn.techaction.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionAddressDao;
import cn.techaction.pojo.ActionAddress;
import cn.techaction.service.ActionAddressService;

@Service
public class ActionAddressServiceImpl implements ActionAddressService {
	
	@Autowired
	private ActionAddressDao actionAddressDao;

	@Override
	public SverResponse<String> addAddress(ActionAddress address) {
		//判断参数
		if(address==null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		//判断已有地址中是否含有默认地址，如果没有则本条地址为默认地址，否则为一般地址
		int rs = actionAddressDao.findDefaultAddrByUserId(address.getUser_id());
		if(rs==0) {
			address.setDft(1);
		}else {
			address.setDft(0);
		}
		rs = actionAddressDao.insertAddress(address);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("地址新增成功！");
		}
		return SverResponse.createByErrorMessage("地址新增失败！");
	}

	@Override
	public SverResponse<String> updateAddress(ActionAddress address) {
		//判断参数
		if(address==null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		//修改地址
		int rs = actionAddressDao.updateAddress(address);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("地址更新成功！");
		}
		return SverResponse.createByErrorMessage("地址更新失败！");
	}

	@Override
	public SverResponse<List<ActionAddress>> findAddrsByUserId(String user_id) {
		//判断参数
		if(user_id==null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		List<ActionAddress> list = actionAddressDao.findAddrsByUserId(user_id);
		return SverResponse.createRespBySuccess(list);
	}

	@Override
	public SverResponse<String> delAddress(String user_id, Integer addr_id) {
		//判断参数
		if(addr_id==null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		//删除地址
		int rs = actionAddressDao.delAddress(user_id, addr_id);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("地址删除成功！");
		}
		return SverResponse.createByErrorMessage("地址删除失败！");
	}

	@Override
	public SverResponse<String> updateAddrDefaultStatus(String user_id, Integer addr_id) {
		//判断参数
		if(addr_id==null||user_id == null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		//读取原来默认地址
		ActionAddress oldAddr = actionAddressDao.findDefaultAddr(user_id);
		if(oldAddr!=null) {
			//取消默认地址
			oldAddr.setDft(0);
			if(actionAddressDao.updateAddress(oldAddr)<=0) {
				return SverResponse.createByErrorMessage("默认地址设置失败！");
			}
		}
		//设置新的默认地址
		ActionAddress newAddr = new ActionAddress();
		newAddr.setDft(1);
		newAddr.setAddr_id(addr_id);
		newAddr.setUser_id(user_id);
		if(actionAddressDao.updateAddress(newAddr)<=0) {
			return SverResponse.createByErrorMessage("默认地址设置失败！");
		}
		return SverResponse.createRespBySuccessMessage("默认地址设置成功！");
	}

	@Override
	public SverResponse<ActionAddress> findAddrByUserAndAddrId(String user_id, Integer addr_id) {
		//判断参数
		if(addr_id==null||user_id == null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		ActionAddress actionAddress = actionAddressDao.findAddrByUserAndAddrId(user_id, addr_id);
		if(actionAddress==null) {
			return SverResponse.createByErrorMessage("地址不存在！");
		}
		return SverResponse.createRespBySuccess(actionAddress);
	}
}
