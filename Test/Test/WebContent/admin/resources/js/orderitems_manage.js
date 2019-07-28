define(['common'],function(common){
	var order_id = getParam("order_id");
	
	function showOrderItems(){

		$("#table").dataTable({
			"autoWidth":false,
			"paging":true,
			"ordering":false,
			"info":false,
			"searching":false,
			"dom":'<"#tool-contain"><"top" t><"button" lp>',
			"sPaginationType":"full_numbers",
			"ajax":{
				"xhrFields":{withCredentials:true},
				"crossDomain":true,
				"url":baseUrl+"mgr/order/findorderitems.do?order_id="+order_id,
				"type":"get"
			},
			"columns":[
				{"data":"order_id"},
				{"data":"product_id"},
				{"data":"product_name"},
				{"data":"price"},
				{"data":"quantity"}
			],
			"oLanguage":{
				"oProcessing": "努力加载数据中.",
	            "sLengthMenu": "每页显示 _MENU_ 条记录",
	            "sZeroRecords": "抱歉， 没有找到",
	            "sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
	            "sInfoEmpty": "没有数据",
	            "sInfoFiltered": "(从 _MAX_ 条数据中检索)",
	            "sZeroRecords": "没有检索到数据",
	            "sSearch": "模糊查询:  ",
	            "oPaginate": {
	                "sFirst": "首页",
	                "sPrevious": "前一页",
	                "sNext": "后一页",
	                "sLast": "尾页"
	            }
			}
		});
	}
	return {
		showOrderItems:showOrderItems
	};
});


//获取url中的参数
function getParam(name){
	var reg = new RegExp("(^|&)"+name+"=([^&]*)(&|$)");//构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg);
	if (r != null){
		return decodeURI(r[2]);
	}	
	else
		return null;
}


