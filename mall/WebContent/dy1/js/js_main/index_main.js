require.config({
	paths:{
		"jquery": "/mall/dy1/js/js_main/jquery-1.7.2.min",
		"jquery_SuperSlide":"/mall/dy1/js/jquery.SuperSlide.2.1.1",
		"handlebar":"/mall/dy1/js/js_main/handlerbars-v4.1.2",
		"jquery_url":"/mall/dy1/js/js_main/jquery.url",
		},

	shim:{
			'jquery_SuperSlide':['jquery'],
			'jquery_url':['jquery'],
		}

});


require(['jquery','jquery_SuperSlide','handlebar','jquery_url','common','index'],
		function (jquery,jquery_SuperSlide,handlebar,jquery_url,common,index){
	$(function(){
		//加载用户登录信息
		common.getUserInfo();
		//用户登出
	 	common.loginOut();
		//搜索
		//product_search.ready();
		index.getParam();
	    index.getHotProduct();
	    index.getFloors();
	});
});