require.config({
	paths: {
		"jquery":"../../AdminLTE-2.4.5/bower_components/jquery/dist/jquery.min",
		"bootstrap":"../../AdminLTE-2.4.5/bower_components/bootstrap/dist/js/bootstrap.min",
		"datatables.net":"../../AdminLTE-2.4.5/bower_components/datatables.net/js/jquery.dataTables.min",
		"bsdataTables":"../../AdminLTE-2.4.5/bower_components/datatables.net-bs/js/dataTables.bootstrap.min",
		"adminlte":"../../AdminLTE-2.4.5/dist/js/adminlte.min"
	},
	shim:{//依赖
		'bootstrap':['jquery'],
		'bsdataTables':['bootstrap'],
		'fnReloadAjax':['jquery','datatables.net'],
		'adminlte':['bootstrap']
	}
});

//登录页
require(['adminlte','common','login'],function(adminlte,common,login){
	//登录方法
	$(function(){
		
		//绑定登录按钮点击事件
		login.clickLogin();
		//login.hh();
	});
});