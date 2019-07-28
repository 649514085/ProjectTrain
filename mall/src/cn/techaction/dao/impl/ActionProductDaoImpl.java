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
import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.ActionProduct;

@Repository
public class ActionProductDaoImpl implements ActionProductDao{
	
	@Resource
	private QueryRunner queryRunner;
	
	@Override
	public List<ActionProduct> findHotProducts(Integer num) {
		String sql = "select * from products where hot = 1 and status = 1";
		if(num!=null) {
			sql += " limit 0, ?";
		}
		try {
			if(num!=null) {
				return queryRunner.query(sql, new BeanListHandler<ActionProduct>(ActionProduct.class), num);
			}else {
				return queryRunner.query(sql, new BeanListHandler<ActionProduct>(ActionProduct.class));
			}
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<ActionProduct> findProductsByProductCategory(Integer categoryId) {
		String sql = "select * from products where type_id = ? and status = 1";
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionProduct>(ActionProduct.class), categoryId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public ActionProduct findProductById(String productId) {
		String sql = "select * from products where product_id = ?";
		try {
			return queryRunner.query(sql, new BeanHandler<ActionProduct>(ActionProduct.class), productId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int getTotalCount(ActionProduct product) {
		String sql = "select count(product_id) as num from products where 1 = 1";
		List<Object> params = new ArrayList<>();
		if(product.getProduct_id()!=null) {
			sql += " and product_id = ?";
			params.add(product.getProduct_id());
		}
		if(product.getProduct_name()!=null) {
			sql += " and product_name like ?";
			params.add("%"+product.getProduct_name()+"%");
		}
		if(product.getType_id()!=null) {
			sql += " and type_id = ?";
			params.add(product.getType_id());
		}
		if(product.getStatus()!=null) {
			sql += " and status = ?";
			params.add(product.getStatus());
		}
		System.out.println(sql);
		try {
			List<Long> rs = queryRunner.query(sql, new ColumnListHandler<Long>("num"), params.toArray());
			return rs.size()>0?rs.get(0).intValue():0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<ActionProduct> findProducts(ActionProduct product, int startIndex, int pageSize) {
		String sql = "select * from products where 1 = 1";
		List<Object> params = new ArrayList<>();
		if(product.getProduct_name()!=null) {
			sql += " and product_name like ?";
			params.add("%"+product.getProduct_name()+"%");
		}
		if(product.getType_id()!=null) {
			sql += " and type_id = ?";
			params.add(product.getType_id());
		}
		if(product.getStatus()!=null) {
			sql += " and status = ?";
			params.add(product.getStatus());
		}
		sql += " limit ?,?";
		params.add(startIndex);
		params.add(pageSize);
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionProduct>(ActionProduct.class), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int updateProduct(ActionProduct product) {
		String sql = "update products set product_id = ?";
		List<Object> params = new ArrayList<>();
		params.add(product.getProduct_id());
		if(!StringUtils.isEmpty(product.getProduct_name())) {
			sql+=", product_name = ?";
			params.add(product.getProduct_name());
		}
		if(product.getType_id()!=null) {
			sql += ", type_id = ?";
			params.add(product.getType_id());
		}
		if(product.getPrice()!=null) {
			sql += ", price = ?";
			params.add(product.getPrice());
		}
		if(!StringUtils.isEmpty(product.getSpec_param())) {
			sql+=", spec_param = ?";
			params.add(product.getSpec_param());
		}
		if(!StringUtils.isEmpty(product.getDetail())) {
			sql+=", detail = ?";
			params.add(product.getDetail());
		}
		if(product.getStock()!=null) {
			sql += ", stock = ?";
			params.add(product.getStock());
		}
		if(product.getStatus()!=null) {
			sql += ", status = ?";
			params.add(product.getStatus());
		}
		if(product.getHot()!=null) {
			sql += ", hot = ?";
			params.add(product.getHot());
		}
		sql += " where product_id = ?";
		params.add(product.getProduct_id());
		try {
			return queryRunner.update(sql, params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<ActionProduct> findProductsByGparent(Integer gCategoryId, Integer num) {
		String sql = "select product_id, product_name, products.type_id, price, spec_param, detail, stock, status, hot, main_image, sub_images "
				+ "from products,types where products.type_id = types.type_id and gparent_id = ? and status = 1 limit 0, ?";
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionProduct>(ActionProduct.class), gCategoryId, num);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
