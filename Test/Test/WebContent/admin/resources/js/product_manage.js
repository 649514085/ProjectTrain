define(['common'],function(common){
	function showProducts(){
		
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
				"url":baseUrl+"mgr/product/findproductlist.do"
			},
			"columns":[
				{"data":"product_id"},
				{"data":"product_name"},
				{"data":"price"},
				{"data":"stock"},
				{"data":"status"},
				{"data":"hot"},
				{"data":null}
			],
			columnDefs:[
				{
					targets:4,
					render:function(data,type,row,meta){
						return statusRender(row);
					}
				},
				{
					targets:5,
					render:function(data,type,row,meta){
						return hotRender(row);
					}
				},
				{
					targets:6,
					render:function(data,type,row,meta){
						return render(row);
					}
				}
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
	
	function productSearch(){
		$("#search").click(function(){
			var product_kw = $("#product-kw").val();
			var select = document.getElementById("select");
			var index = select.selectedIndex; 
			var query = select.options[index].value;
			//alert(query);
			//按照商品编号查找商品
			if (query==0){
				$.ajax({
					"xhrFields":{withCredentials:true},
					"crossDomain":true,
					"url":baseUrl+"mgr/product/findproductbyid.do",
					data:{
						"product_id":product_kw
					},
					type:"post",
					dataType:"json",
					success:function(rs){
						if(rs.status==0){
							var table = $("#table").dataTable();
							table.fnClearTable();
							table.fnReloadAjax(baseUrl+"mgr/product/findproductbyid.do?product_id="+product_kw);
						
						}
						else if(rs.status==1&&rs.msg=="ERROR"){
							alert("没有该商品！");
						}
					}
				});
			}
			//按照商品名称查找商品
			if (query==1){
				$.ajax({
					"xhrFields":{withCredentials:true},
					"crossDomain":true,
					"url":baseUrl+"mgr/product/findproductbyname.do",
					data:{
						"product_name":product_kw
					},
					type:"post",
					dataType:"json",
					success:function(rs){
						if(rs.status==0){
							var table = $("#table").dataTable();
							table.fnClearTable();
							table.fnReloadAjax(baseUrl+"mgr/product/findproductbyname.do?product_name="+product_kw);
						
						}
						else if(rs.status==1&&rs.msg=="ERROR"){
							alert("没有该商品！");
						}
					}
				});
			}
			
		});
	}
	return {
		showProducts:showProducts,
		productSearch:productSearch
	};
});

//渲染状态列
function statusRender(row){
	var content = row.status;
	var product_id = row.product_id;
 	//alert("  "+product_id);
	if (row.status == "上架"){
		content = row.status + '&nbsp;&nbsp; <a class="btn btn-primary" href="javascript:void(0);" onclick=\"updateStatus('+product_id+',0)\">下架</a>'; 
	}
	if (row.status == "下架"){
		content = row.status + '&nbsp;&nbsp; <a class="btn btn-primary" href="javascript:void(0);" onclick=\"updateStatus('+product_id+',1)\">上架</a>'; 
	}
	
	return content;
}

//渲染热销列
function hotRender(row){
	var content = row.hot;
	var status = row.status;
	var status_int=0;
	if (row.status == "上架"){
		 status_int=1;
	}
	if (row.status == "下架"){
		 status_int=0;
	}
	if (row.hot == "热销"){
		content = row.hot+ '&nbsp;&nbsp; <a class="btn btn-primary" href="javascript:void(0);" onclick=\"updateHot('+row.product_id+','+status_int+',0)\">非热销</a>'; 
	}
	if (row.hot == "非热销"){
		content = row.hot+ '&nbsp;&nbsp; <a class="btn btn-primary" href="javascript:void(0);" onclick=\"updateHot('+row.product_id+','+status_int+',1)\">热销</a>'; 
	}
	return content;
}

//渲染操作列
function render(row){
	var str = '<a class="button" href="javascript:void(0);" onclick="updateProduct(\''+row.product_id +'\');">编辑</a>';
	return str;
}

//更改商品状态
function updateStatus(product_id,status){
	//alert("status:"+status);
	//alert("product_id:"+product_id);
	$.ajax({
		"xhrFields":{withCredentials:true},
		"crossDomain":true,
		"url":baseUrl+"mgr/product/modifystatus.do",
		data:{
			"product_id":product_id,
			"status":status
		},
		dataType:"json",
		success:function(rs){
			//alert("rs"+rs);
			if(rs.status==0){
				var table = $("#table").dataTable();
				table.fnReloadAjax(baseUrl+"mgr/product/findproductlist.do");
				//table.ajax.reload();
			}
			if(rs.status==1){
				alert(rs.msg);
			}
		}
	});
}

//更改热销商品
function updateHot(product_id,status,hot){
	//alert(status);
	//alert("hot:"+hot);
	if(status==0){
		alert("只有上架商品才可以设置为热销商品！请先更改商品状态！");
		return;
	}
	$.ajax({
		"xhrFields":{withCredentials:true},
		"crossDomain":true,
		"url":baseUrl+"mgr/product/modifyhot.do",
		"data":{
			"product_id":product_id,
			"hot":hot
		},
		success:function(rs){
			if(rs.status==0){
				var table = $("#table").dataTable();
				table.fnReloadAjax(baseUrl+"mgr/product/findproductlist.do");
			}else{
				alert(rs.msg);
			}
		}
	});
}

function updateProduct(product_id){
	$(window).attr("location","product-update.html?product_id="+product_id);
}

function addProduct(){
	$(window).attr("location","product-add.html");
}