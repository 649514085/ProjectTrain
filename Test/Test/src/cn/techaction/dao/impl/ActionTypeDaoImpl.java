package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

import cn.techaction.dao.ActionTypeDao;
import cn.techaction.pojo.Type;

@Repository
public class ActionTypeDaoImpl implements ActionTypeDao{
	@Resource
	QueryRunner queryRunner;
	
	@Override
	public List<Type> findTypeById(Integer type_id) {
		String sql = "select * from types where type_id=?";
		try {
			return queryRunner.query(sql, new BeanListHandler<Type>(Type.class), type_id);
		} catch (SQLException e) {
			return null;
		}
	}
	
	@Override
	public Type findTypeByIdOne(Integer type_id) {
		String sql = "select * from types where type_id=?";
		try {
			return queryRunner.query(sql, new BeanHandler<Type>(Type.class), type_id);
		} catch (SQLException e) {
			return null;
		}
	}
	
	@Override
	public List<Type> findRootType(){
		String sql = "select * from types where parent_id=0";
		try {
			return queryRunner.query(sql, new BeanListHandler<Type>(Type.class));
		} catch (SQLException e) {
			return null;
		}
	}
	
	@Override
	public List<Type> findSubTypeOfType(Integer type_id){
		String sql = "select * from types where parent_id=?";
		try {
			return queryRunner.query(sql, new BeanListHandler<Type>(Type.class), type_id);
		} catch (SQLException e) {
			return null;
		}
	}
	
	@Override
	public List<Type> findTypeByRank(Integer rank){
		String sql = "select * from types where rank=?";
		try {
			return queryRunner.query(sql, new BeanListHandler<Type>(Type.class),rank);
		} catch (SQLException e) {
			return null;
		}
	}
	
	@Override
	public List<Type> findTypeByName(String type_name){
		String sql = "select * from types where type_name like ?";
		try {
			return queryRunner.query(sql, new BeanListHandler<Type>(Type.class), type_name);
		} catch (SQLException e) {
			return null;
		}
	}
	
	@Override
	public int delType(Integer type_id) {
		String sql = "delete from types where type_id = ?";
		try {
			return queryRunner.update(sql, type_id);
		} catch (SQLException e) {
			return 0;
		}
	}
	
	@Override
	public Type findTypeByParentAndName(Integer parent_id,String type_name) {
		String sql = "select * from types where parent_id=? and type_name = ?";
		try {
			return queryRunner.query(sql, new BeanHandler<Type>(Type.class), parent_id,type_name);
		} catch (SQLException e) {
			return null;
		}
	}
	
	@Override
	public int addType(Type type) {
		String sql = "insert into types(gparent_id,parent_id,type_name,rank) values (?,?,?,?)";
		List<Object> param = Lists.newArrayList();
		param.add(type.getGparent_id());
		param.add(type.getParent_id());
		param.add(type.getType_name());
		param.add(type.getRank());
		try {
			return queryRunner.update(sql, param.toArray());
		} catch (SQLException e) {
			return 0;
		}
	}
	
	public int updateType(Type type) {
		String sql = "update types set gparent_id=?, parent_id=?, type_name=?,rank=? where type_id = ?";
		List<Object> param = Lists.newArrayList();
		param.add(type.getGparent_id());
		param.add(type.getParent_id());
		param.add(type.getType_name());
		param.add(type.getRank());
		param.add(type.getType_id());
		
		try {
			return queryRunner.update(sql, param.toArray());
		} catch (SQLException e) {
			return 0;
		}
	}
}
