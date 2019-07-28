require.config({
	paths: {
		"jquery":"../../AdminLTE-2.4.5/bower_components/jquery/dist/jquery.min",
		"bootstrap":"../../AdminLTE-2.4.5/bower_components/bootstrap/dist/js/bootstrap.min",
		"datatables.net":"../../AdminLTE-2.4.5/bower_components/datatables.net/js/jquery.dataTables.min",
		"bsdataTables":"../../AdminLTE-2.4.5/bower_components/datatables.net-bs/js/dataTables.bootstrap.min",
		"adminlte":"../../AdminLTE-2.4.5/dist/js/adminlte.min",
		"WebUploader":"../../resources/webuploader/webuploader",
		"Handlebars":"handlebars-v4.1.2"
	},
	shim:{//依赖
		'bootstrap':['jquery'],
		'bsdataTables':['bootstrap'],
		'fnReloadAjax':['jquery','datatables.net'],
		'adminlte':['bootstrap']
	}
});

require(['jquery','bootstrap','datatables.net','bsdataTables','adminlte','product_update','common','fnReloadAjax','WebUploader',"Handlebars"],
	function(jquery,bootstrap,datatables_net,bsdataTables,adminlte,product_update,common,fnReloadAjax,WebUploader,Handlebars){

	$(function(){
		product_update.getType();
		product_update.updateProductInfo();
		
	});
});