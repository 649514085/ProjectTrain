package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import com.sun.org.apache.bcel.internal.generic.NEW;

import cn.techaction.dao.ActionParamsDao;
import cn.techaction.pojo.ActionParam;

@Repository
public class ActionParamsDaoImpl implements ActionParamsDao {
	
	@Resource
	QueryRunner queryRunner;

	@Override
	public List<ActionParam> findParamsByParentId(Integer parentId) {
		String sql = "select type_id, parent_id, type_name from types where parent_id = ?";
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionParam>(ActionParam.class), parentId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ActionParam findParamById(Integer id) {
		String sql = "select * from types where type_id = ?";
		try {
			return queryRunner.query(sql, new BeanHandler<ActionParam>(ActionParam.class), id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
