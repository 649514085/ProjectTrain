require.config({
	paths:{
		"jquery":"/mall/dy1/js/js_main/jquery-1.7.2.min",
		"handlebar":"/mall/dy1/js/js_main/handlerbars-v4.1.2",
	}
});
require(['jquery','handlebar','common','cart'],function(jquery,handlebar,common,cart){
	$(function(){
		//加载登录用户信息
		
		common.getUserInfo();
		//用户登出
		common.loginOut();
		cart.ready();
		
	});
});