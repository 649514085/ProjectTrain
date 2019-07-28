package cn.techaction.dao;

import java.util.List;

import com.sun.beans.editors.IntegerEditor;

import cn.techaction.pojo.ActionParam;

public interface ActionParamsDao {
	
	/**
	 * 根据父节点id查找子节点参数
	 * @param i
	 * @return
	 */
	public List<ActionParam> findParamsByParentId(Integer parentId);
	
	/**
	 * 根据类型id查找类型
	 * @param id
	 * @return
	 */
	public ActionParam findParamById(Integer id);
}
