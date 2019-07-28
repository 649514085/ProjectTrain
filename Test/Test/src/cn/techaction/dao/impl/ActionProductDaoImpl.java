package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.Icons;
import cn.techaction.pojo.Product;

@Repository
public class ActionProductDaoImpl implements ActionProductDao{
	@Autowired
	private QueryRunner queryRunner;
	
	
	@Override
	public List<Product> findProductList(){
		String sql = "select * from products";
		try {
			return queryRunner.query(sql, new BeanListHandler<Product>(Product.class));
		} catch (SQLException e) {
			return null;
		}
	}
	
	@Override
	public List<Product> findProductByPid(String product_id){
		String sql = "select * from products where product_id=?";
		try {
			return queryRunner.query(sql, new BeanListHandler<Product>(Product.class), product_id);
		} catch (SQLException e) {
			return null;
		}	
	}
	
	@Override
	public List<Product> findProductByPname(String product_name){
		String sql = "select * from products where product_name like ?";
		try {
			return queryRunner.query(sql, new BeanListHandler<Product>(Product.class), product_name);
		} catch (SQLException e) {
			return null;
		}	
	}
	
	@Override
	public int updateStatus(Product product) {
		String sql = "update products set status =? where product_id =? ";
		List<Object> param = Lists.newArrayList();
		param.add(product.getStatus());
		param.add(product.getProduct_id());
		
		try {
			int rs = queryRunner.update(sql, param.toArray());
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int updateHot(Product product) {
		String sql = "update products set hot =? where product_id =? ";
		List<Object> param = Lists.newArrayList();
		param.add(product.getHot());
		param.add(product.getProduct_id());
		
		try {
			int rs = queryRunner.update(sql, param.toArray());
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int addProduct(Product product) {
		String sql = "insert into products (product_id,product_name,type_id,"+
				"price,spec_param,detail,stock,main_image,sub_images,status,hot) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?)";
		List<Object> params = Lists.newArrayList();
		params.add(product.getProduct_id());
		System.out.print(product.getProduct_name());
		params.add(product.getProduct_name());
		params.add(product.getType_id());
		params.add(product.getPrice());
		params.add(product.getSpec_param());
		params.add(product.getDetail());
		params.add(product.getStock());
		params.add(product.getMain_image());
		params.add(product.getSub_images());
		params.add(product.getStatus());
		params.add(product.getHot());
		try {
			//Product pro = queryRunner.insert(sql, new BeanHandler<Product>(Product.class), params.toArray());
			return queryRunner.update(sql, params.toArray());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public int updateProduct(Product product) {
		String sql = "update products set product_name=?,type_id=?,"+
				"price=?,spec_param=?,detail=?,stock=?,main_image=?,sub_images=? where product_id=?";
		List<Object> params = Lists.newArrayList();
		params.add(product.getProduct_name());
		params.add(product.getType_id());
		params.add(product.getPrice());
		params.add(product.getSpec_param());
		params.add(product.getDetail());
		params.add(product.getStock());
		params.add(product.getMain_image());
		params.add(product.getSub_images());
		params.add(product.getProduct_id());
		
		try {
			//Product pro = queryRunner.insert(sql, new BeanHandler<Product>(Product.class), params.toArray());
			return queryRunner.update(sql, params.toArray());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public List<Product> findProductOfType(Integer type_id){
		String sql = "select * from products where type_id =?";
		try {
			return queryRunner.query(sql, new BeanListHandler<Product>(Product.class), type_id);
		} catch (SQLException e) {
			return null;
		}	
 	}
}
