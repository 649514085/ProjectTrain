require.config({
	paths:{
		"jquery":"/mall/dy1/js/js_main/jquery-1.7.2.min",
		"handlebar":"/mall/dy1/js/js_main/handlerbars-v4.1.2",
	}
});
require(['jquery','handlebar','common','collect'],function(jquery,handlebar,common,collect){
	$(function(){
		
		common.getUserInfo();
		common.loginOut();
		collect.ready();
		
	});
});