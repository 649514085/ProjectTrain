package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.bcel.internal.generic.Select;
import com.sun.org.apache.regexp.internal.recompile;

import cn.techaction.dao.ActionAddressDao;
import cn.techaction.pojo.ActionAddress;

@Repository
public class ActionAddressDaoImpl implements ActionAddressDao{
	
	@Resource
	private QueryRunner queryRunner;

	@Override
	public int findDefaultAddrByUserId(String user_id) {
		String sql = "select count(addr_id) as num from addrs where user_id = ? and dft = 1";
		try {
			List<Long> rs = queryRunner.query(sql, new ColumnListHandler<Long>("num"), user_id);
			return rs.size()>0?rs.get(0).intValue():0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}	
	}

	@Override
	public int insertAddress(ActionAddress address) {
		String sql = "insert into addrs(user_id, name, phone, province, city, district, addr, dft)"
				+ " values(?,?,?,?,?,?,?,?)";
		Object[] params= {
				address.getUser_id(), address.getName(),address.getPhone(),address.getProvince(),
				address.getCity(),address.getDistrict(), address.getAddr(),address.getDft()
		};
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateAddress(ActionAddress address) {
		String sql = "update addrs set user_id = ?";
		List<Object> params = new ArrayList<>();
		params.add(address.getUser_id());
		if(!StringUtils.isEmpty(address.getName())) {
			sql+=", name = ?";
			params.add(address.getName());
		}
		if(!StringUtils.isEmpty(address.getPhone())) {
			sql+=", phone = ?";
			params.add(address.getPhone());
		}
		if(!StringUtils.isEmpty(address.getProvince())) {
			sql+=", province = ?";
			params.add(address.getProvince());
		}
		if(!StringUtils.isEmpty(address.getCity())) {
			sql+=", city = ?";
			params.add(address.getCity());
		}
		if(!StringUtils.isEmpty(address.getDistrict())) {
			sql+=", district = ?";
			params.add(address.getDistrict());
		}
		if(!StringUtils.isEmpty(address.getAddr())) {
			sql+=", addr = ?";
			params.add(address.getAddr());
		}
		if(address.getDft()!=null) {
			sql+=", dft = ?";
			params.add(address.getDft());
		}
		sql+=" where addr_id = ?";
		params.add(address.getAddr_id());
		try {
			return queryRunner.update(sql, params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<ActionAddress> findAddrsByUserId(String user_id) {
		String sql = "select * from addrs where user_id = ?";
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionAddress>(ActionAddress.class), user_id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int delAddress(String user_id, Integer addr_id) {
		String sql = "delete from addrs where user_id = ? and addr_id = ?";
		try {
			return queryRunner.update(sql, user_id, addr_id);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public ActionAddress findDefaultAddr(String user_id) {
		String sql = "select * from addrs where user_id = ? and dft = 1";
		try {
			return queryRunner.query(sql, new BeanHandler<ActionAddress>(ActionAddress.class), user_id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ActionAddress findAddrById(Integer addr_id) {
		String sql = "select * from addrs where addr_id = ?";
		try {
			return queryRunner.query(sql, new BeanHandler<ActionAddress>(ActionAddress.class), addr_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ActionAddress findAddrByUserAndAddrId(String user_id,Integer addr_id) {
		String sql = "select * from addrs where user_id = ? and addr_id = ?";
		try {
			return queryRunner.query(sql, new BeanHandler<ActionAddress>(ActionAddress.class), user_id, addr_id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
