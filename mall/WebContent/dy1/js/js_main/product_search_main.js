require.config({
	paths:{
		"jquery":"/mall/dy1/js/js_main/jquery-1.7.2.min",
		"handlebar":"/mall/dy1/js/js_main/handlerbars-v4.1.2",
		}

});


require(['jquery','handlebar','common','product_search'],
		function (jquery,handlebar,common,product_search){
	$(function(){
		//加载用户登录信息
		common.getUserInfo();
		//用户登出
		common.loginOut();
		//搜索
		product_search.ready();

	});
});