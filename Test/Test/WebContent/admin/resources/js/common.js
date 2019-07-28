baseUrl =  'http://localhost:8080/Test/';
define(function(){
	//获取url中的参数
	function getParam(name){
		var reg = new RegExp("(^|&)"+name+"=([^&*])(&|$)");//构造一个含有目标参数的正则表达式对象
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return decodeURI(r[2]);
		else
			return null;
	}
	
	//用户验证
//	function(){
//		var = window.location.href;
//		//判断是否是登录页
//		if (url.indexOf("login.html")>=0){
//			return;
//		}
//		//若不是登录页，则加载登录页
//		$.ajax({
//			url:baseUrl+""
//		});
//	}
});