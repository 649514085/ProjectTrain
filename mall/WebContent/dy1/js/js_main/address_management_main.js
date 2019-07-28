require.config({
	paths:{
		"jquery":"/mall/dy1/js/js_main/jquery-1.7.2.min",
		"handlebar":"/mall/dy1/js/js_main/handlerbars-v4.1.2",
		"ChineseDistricts":"/mall/dy1/js/jqueryDistpicker/distpicker.data",
		"distpicker":"/mall/dy1/js/jqueryDistpicker/distpicker",
	}
});
require(['jquery','handlebar','common','address_management'],function(jquery,handlebar,common,address_management){
	$(function(){
		//加载登录用户信息
		
		common.getUserInfo();
		//用户登出
		common.loginOut();
		address_management.ready();
		
	});
});