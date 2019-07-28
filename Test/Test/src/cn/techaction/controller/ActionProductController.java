package cn.techaction.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.ResponseCode;
import cn.techaction.common.SverResponse;
import cn.techaction.pojo.Admin;
import cn.techaction.pojo.Product;
import cn.techaction.service.ProductService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.vo.ProductVo;

@Controller
@RequestMapping("/mgr/product")
public class ActionProductController {
	@Autowired
	private ProductService productService;
	
	@ResponseBody
	@RequestMapping("/findproductlist.do")
	public SverResponse<List<ProductVo>> findProductList(HttpSession session) {
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return productService.findProductList();
		}
	}
	
	@ResponseBody
	@RequestMapping("/findproductbyid.do")
	public SverResponse<List<ProductVo>> findProductById(HttpSession session,String product_id) {
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return productService.findProductById(product_id);
		}
	}
	
	@ResponseBody
	@RequestMapping("/findproductbyname.do")
	public SverResponse<List<ProductVo>> findProductByName(HttpSession session,String product_name) {
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return productService.findProductByName(product_name);
		}
	}
	
	@ResponseBody
	@RequestMapping("/modifystatus.do")
	public SverResponse<String> modifyStatus(HttpSession session,String product_id,Integer status){
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return productService.updateStatus(product_id, status);
		}
	}
	
	@ResponseBody
	@RequestMapping("/modifyhot.do")
	public SverResponse<String> modifyHot(HttpSession session,String product_id,Integer hot){
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return productService.updateHot(product_id, hot);
		}
	}

	@ResponseBody
	@RequestMapping("/addproduct.do")
	public SverResponse<String> addProduct(HttpSession session,ProductVo productVo){
		System.out.println("+++++++");
		Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return productService.addProduct(productVo);
		}
	}
	
	@ResponseBody
	@RequestMapping("/updateproduct.do")
	public SverResponse<String> updateProduct(HttpSession session,ProductVo productVo){
	Admin admin = (Admin)session.getAttribute(ConstUtil.CUR_USER);
		//未登录
		if (admin==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作");
		}
		//已登录
		else {
			return productService.updateProduct(productVo);
		}
	}
}
