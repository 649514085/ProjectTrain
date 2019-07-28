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
		//查找一级子节点
		List<ActionParam> paramList = actionParamsDao.findParamsByParentId(0);
		//递归查询所有子节点
		for(ActionParam actionParam : paramList) {
			findDirectChildren(actionParam);
		}
		return SverResponse.createRespBySuccess(paramList);
	}

	//递归查找
	private void findDirectChildren(ActionParam parentParam) {
		//查找子节点
		List<ActionParam> paramList = actionParamsDao.findParamsByParentId(parentParam.getType_id());
		parentParam.setChildren(paramList);
		for(ActionParam actionParam : paramList) {
			findDirectChildren(actionParam);
		}
	}
}
