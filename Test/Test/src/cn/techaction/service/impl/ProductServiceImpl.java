package cn.techaction.service.impl;

import java.util.List;

import javax.swing.Icon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionProductDao;
import cn.techaction.dao.ActionTypeDao;
import cn.techaction.pojo.Icons;
import cn.techaction.pojo.Product;
import cn.techaction.service.ProductService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.vo.ProductVo;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ActionProductDao productDao;
	@Autowired
	private ActionTypeDao typeDao;
	
	@Override
	public SverResponse<List<ProductVo>> findProductList(){
		List<Product> products = productDao.findProductList();
		List<ProductVo> voList = Lists.newArrayList();
		for (Product pro : products) {
			voList.add(productToProductVo(pro));
		}
		return SverResponse.createRespBySuccess(voList);
	}
	
	@Override
	public SverResponse<List<ProductVo>> findProductById(String product_id){
		List<Product> products = productDao.findProductByPid(product_id);
		List<ProductVo> voList = Lists.newArrayList();
		if (products.size()>0) {
			for (Product pro : products) {
				voList.add(productToProductVo(pro));
			}
		}
		return SverResponse.createRespBySuccess(voList);
	}
	
	@Override
	public SverResponse<List<ProductVo>> findProductByName(String product_name){
		String sql = "%"+product_name+"%";
		List<Product> products = productDao.findProductByPname(sql);
		List<ProductVo> voList = Lists.newArrayList();
		if (products.size()>0) {
			for (Product pro : products) {
				voList.add(productToProductVo(pro));
			}
		}
		return SverResponse.createRespBySuccess(voList);
	}
	
	@Override
	public SverResponse<String> updateStatus(String product_id,Integer status){
		if (product_id==null) {
			return SverResponse.createByErrorMessage("�����Ƿ���");
		}
		Product product = new Product();
		product.setProduct_id(product_id);
		if (status!=null) {
			product.setStatus(status);
		}
		int rs = productDao.updateStatus(product);
		if (rs>0) {
			return SverResponse.createRespBySuccess("�޸���Ʒ״̬�ɹ���");
		}
		return SverResponse.createByErrorMessage("�޸���Ʒ״̬ʧ�ܣ�");	
	}

	@Override
	public SverResponse<String> updateHot(String product_id,Integer hot){
		if (product_id==null) {
			return SverResponse.createByErrorMessage("�����Ƿ���");
		}
		Product product = new Product();
		product.setProduct_id(product_id);
		if (hot!=null) {
			product.setHot(hot);
		}
		int rs = productDao.updateHot(product);
		if (rs>0) {
			return SverResponse.createRespBySuccess("�޸�������Ʒ�ɹ���");
		}
		return SverResponse.createByErrorMessage("�޸�������Ʒʧ�ܣ�");	
	}
	
	@Override
	public SverResponse<String> addProduct(ProductVo productVo) {
		if (productVo==null) {
			return SverResponse.createByErrorMessage("�����Ƿ���");
		}
		productVo.setStatus("�ϼ�");
		productVo.setHot("������");
		Product pro = productVoToProduct(productVo);
		System.out.print("pro:"+pro.getProduct_name());
		int rs = productDao.addProduct(pro);
		if (rs>0) {
			return SverResponse.createRespBySuccess("��Ʒ��ӳɹ���");
		}
		return SverResponse.createByErrorMessage("��Ʒ���ʧ�ܣ�");
	}
	
	public SverResponse<String> updateProduct(ProductVo productVo) {
		if (productVo==null) {
			return SverResponse.createByErrorMessage("�����Ƿ���");
		}
		Product product = productVoToProduct(productVo);
		int rs = productDao.updateProduct(product);
		if (rs>0) {
			return SverResponse.createRespBySuccess("��Ʒ�޸ĳɹ���");
		}
		return SverResponse.createByErrorMessage("��Ʒ�޸�ʧ�ܣ�");
	}
	
	
	public Product productVoToProduct(ProductVo productVo) {
		Product product = new Product();
		product.setProduct_id(productVo.getProduct_id());
		product.setProduct_name(productVo.getProduct_name());
		product.setType_id(productVo.getType_id());
		product.setPrice(productVo.getPrice());
		product.setSpec_param(productVo.getSpec_param());
		product.setDetail(productVo.getDetail());
		product.setStock(productVo.getStock());
		
		String url = productVo.getUrl();
		if (url!=null) {
			String[] a =url.split(",");
			if (a.length>1) {
				product.setMain_image(a[0]);
				int index = url.indexOf(",");
				product.setSub_images(url.substring(index+1));
			}
			if (a.length==1) {
				product.setMain_image(a[0]);
				product.setSub_images(null);
			}
		}
		
		if (productVo.getStatus()=="�ϼ�") {
			product.setStatus(1);
		}
		else {
			product.setStatus(0);
		}
		if (productVo.getHot()=="������") {
			product.setHot(0);
		}
		else {
			product.setHot(1);
		}
		return product;
	}
	
	public ProductVo productToProductVo(Product product) {
		ProductVo vo = new ProductVo();
		vo.setProduct_id(product.getProduct_id());
		vo.setProduct_name(product.getProduct_name());
		vo.setType_id(product.getType_id());
		vo.setPrice(product.getPrice());
		vo.setDetail(product.getDetail());
		vo.setSpec_param(product.getSpec_param());
		vo.setStock(product.getStock());
		vo.setUrl(product.getMain_image()+","+product.getSub_images());
		if (product.getStatus()==ConstUtil.ProductStatus.STATUS_ON_SALE) {
			vo.setStatus("�ϼ�");
		}
		else if (product.getStatus()==ConstUtil.ProductStatus.STATUS_OFF_SALE) {
			vo.setStatus("�¼�");
		}
		
		if (product.getHot()==ConstUtil.HotStatus.HOT_STATUS) {
			vo.setHot("����");
		}
		else if (product.getHot()==ConstUtil.HotStatus.NORMAL_STATUS) {
			vo.setHot("������");
		}	
		return vo;
	}
}
