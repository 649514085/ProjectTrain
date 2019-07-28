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

require(['jquery','bootstrap','datatables.net','bsdataTables','adminlte','type_add','common','fnReloadAjax'],
	function(jquery,bootstrap,datatables_net,bsdataTables,adminlte,type_add,common,fnReloadAjax){

	$(function(){
		type_add.getRootTypeInfo();
		type_add.dropDownEvent();
		type_add.saveBtn();
	});
});