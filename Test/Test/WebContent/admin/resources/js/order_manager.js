define(function(){
	function showOrders(){
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
				"url":baseUrl+"mgr/order/findorders.do"
			},
			"columns":[
				{"data":"order_id"},
				{"data":"user_id"},
				{"data":"status"},
				{"data":"amount"},
				{"data":"receiver"},
				{"data":"phone"},
				{"data":"addr"},
				{"data":null}
			],
			columnDefs:[
				{
					targets:7,
					render:function(data,type,row,meta){
						return query(row);
					}
				},
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
	
	function orderSearch(){
		$("#search").click(function(){
			var order_kw = $("#order-kw").val();
			var select = document.getElementById("select");
			var index = select.selectedIndex; 
			var query = select.options[index].value;
			//alert(query);
			//按照编号查找商品
			if (query==0){
				$.ajax({
					"xhrFields":{withCredentials:true},
					"crossDomain":true,
					"url":baseUrl+"mgr/order/findorderbyoid.do",
					data:{
						"order_id":order_kw
					},
					type:"post",
					dataType:"json",
					success:function(rs){
						if(rs.status==0){
							var table = $("#table").dataTable();
							table.fnClearTable();
							table.fnReloadAjax(baseUrl+"mgr/order/findorderbyoid.do?order_id="+order_kw);
						
						}
						else if(rs.status==1&&rs.msg=="ERROR"){
							alert("没有该订单！");
						}
					}
				});
			}
			//按照用户名查找商品
			if (query==1){
				$.ajax({
					"xhrFields":{withCredentials:true},
					"crossDomain":true,
					"url":baseUrl+"mgr/order/findorderbyuid.do",
					data:{
						"user_id":order_kw
					},
					type:"post",
					dataType:"json",
					success:function(rs){
						if(rs.status==0){
							var table = $("#table").dataTable();
							table.fnClearTable();
							table.fnReloadAjax(baseUrl+"mgr/order/findorderbyuid.do?user_id="+order_kw);
						
						}
						else if(rs.status==1&&rs.msg=="ERROR"){
							alert("没有该订单！");
						}
					}
				});
			}
			
		});
	}
	
	return {
		showOrders:showOrders,
		orderSearch:orderSearch
	};
});

function query(row){
	var str = '<a class="button" href="javascript:void(0);" onclick="queryDetail(\''+row.order_id +'\');">查看详情</a>';
	return str;
}

function queryDetail(order_id){
	$(window).attr("location","order_item.html?order_id="+order_id);
}


