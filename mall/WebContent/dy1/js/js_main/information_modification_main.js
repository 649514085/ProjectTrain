require.config({
	paths:{
		"jquery":"/mall/dy1/js/js_main/jquery-1.7.2.min",
	}
});
require(['jquery','common','information_modification'],function(jquery,common,information_modification){
	$(function(){
		//加载登录用户信息
		
		common.getUserInfo();
		//用户登出
		common.loginOut();
		information_modification.ready();
		
	});
});