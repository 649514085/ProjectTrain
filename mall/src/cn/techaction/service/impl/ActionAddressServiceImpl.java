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
		//�жϲ���
		if(address==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		//�ж����е�ַ���Ƿ���Ĭ�ϵ�ַ�����û��������ַΪĬ�ϵ�ַ������Ϊһ���ַ
		int rs = actionAddressDao.findDefaultAddrByUserId(address.getUser_id());
		if(rs==0) {
			address.setDft(1);
		}else {
			address.setDft(0);
		}
		rs = actionAddressDao.insertAddress(address);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("��ַ�����ɹ���");
		}
		return SverResponse.createByErrorMessage("��ַ����ʧ�ܣ�");
	}

	@Override
	public SverResponse<String> updateAddress(ActionAddress address) {
		//�жϲ���
		if(address==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		//�޸ĵ�ַ
		int rs = actionAddressDao.updateAddress(address);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("��ַ���³ɹ���");
		}
		return SverResponse.createByErrorMessage("��ַ����ʧ�ܣ�");
	}

	@Override
	public SverResponse<List<ActionAddress>> findAddrsByUserId(String user_id) {
		//�жϲ���
		if(user_id==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		List<ActionAddress> list = actionAddressDao.findAddrsByUserId(user_id);
		return SverResponse.createRespBySuccess(list);
	}

	@Override
	public SverResponse<String> delAddress(String user_id, Integer addr_id) {
		//�жϲ���
		if(addr_id==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		//ɾ����ַ
		int rs = actionAddressDao.delAddress(user_id, addr_id);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("��ַɾ���ɹ���");
		}
		return SverResponse.createByErrorMessage("��ַɾ��ʧ�ܣ�");
	}

	@Override
	public SverResponse<String> updateAddrDefaultStatus(String user_id, Integer addr_id) {
		//�жϲ���
		if(addr_id==null||user_id == null) {
			return SverResponse.createByErrorMessage("��������");
		}
		//��ȡԭ��Ĭ�ϵ�ַ
		ActionAddress oldAddr = actionAddressDao.findDefaultAddr(user_id);
		if(oldAddr!=null) {
			//ȡ��Ĭ�ϵ�ַ
			oldAddr.setDft(0);
			if(actionAddressDao.updateAddress(oldAddr)<=0) {
				return SverResponse.createByErrorMessage("Ĭ�ϵ�ַ����ʧ�ܣ�");
			}
		}
		//�����µ�Ĭ�ϵ�ַ
		ActionAddress newAddr = new ActionAddress();
		newAddr.setDft(1);
		newAddr.setAddr_id(addr_id);
		newAddr.setUser_id(user_id);
		if(actionAddressDao.updateAddress(newAddr)<=0) {
			return SverResponse.createByErrorMessage("Ĭ�ϵ�ַ����ʧ�ܣ�");
		}
		return SverResponse.createRespBySuccessMessage("Ĭ�ϵ�ַ���óɹ���");
	}

	@Override
	public SverResponse<ActionAddress> findAddrByUserAndAddrId(String user_id, Integer addr_id) {
		//�жϲ���
		if(addr_id==null||user_id == null) {
			return SverResponse.createByErrorMessage("��������");
		}
		ActionAddress actionAddress = actionAddressDao.findAddrByUserAndAddrId(user_id, addr_id);
		if(actionAddress==null) {
			return SverResponse.createByErrorMessage("��ַ�����ڣ�");
		}
		return SverResponse.createRespBySuccess(actionAddress);
	}
}
