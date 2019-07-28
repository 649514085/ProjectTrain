package cn.techaction.dao;

import java.util.List;

import com.sun.beans.editors.IntegerEditor;

import cn.techaction.pojo.ActionParam;

public interface ActionParamsDao {
	
	/**
	 * ���ݸ��ڵ�id�����ӽڵ����
	 * @param i
	 * @return
	 */
	public List<ActionParam> findParamsByParentId(Integer parentId);
	
	/**
	 * ��������id��������
	 * @param id
	 * @return
	 */
	public ActionParam findParamById(Integer id);
}
