package cn.techaction.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionParamsDao;
import cn.techaction.pojo.ActionParam;
import cn.techaction.service.ActionParamsService;

@Service
public class ActionParamsServiceImlp implements ActionParamsService {
	
	@Autowired
	private ActionParamsDao actionParamsDao;

	@Override
	public SverResponse<List<ActionParam>> findAllParams() {
		//����һ���ӽڵ�
		List<ActionParam> paramList = actionParamsDao.findParamsByParentId(0);
		//�ݹ��ѯ�����ӽڵ�
		for(ActionParam actionParam : paramList) {
			findDirectChildren(actionParam);
		}
		return SverResponse.createRespBySuccess(paramList);
	}

	//�ݹ����
	private void findDirectChildren(ActionParam parentParam) {
		//�����ӽڵ�
		List<ActionParam> paramList = actionParamsDao.findParamsByParentId(parentParam.getType_id());
		parentParam.setChildren(paramList);
		for(ActionParam actionParam : paramList) {
			findDirectChildren(actionParam);
		}
	}
}
