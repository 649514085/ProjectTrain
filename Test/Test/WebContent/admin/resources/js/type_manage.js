define(['common'],function(common){
	function showTypes(){
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
				"url":baseUrl+"mgr/type/findroot.do"
			},
			"columns":[
				{"data":"type_id"},
				{"data":"type_name"},
				{"data":null}
			],
			columnDefs:[
				{
					targets:2,
					render:function(data,type,row,meta){
						return action(row);
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
	function typeSearch(){
		$("#search").click(function(){
			var type_kw = $("#type-kw").val();
			var select = document.getElementById("select");
			var index = select.selectedIndex; 
			var query = select.options[index].value;
			//alert(query);
			//按照编号查找商品
			if (query==0){
				$.ajax({
					"xhrFields":{withCredentials:true},
					"crossDomain":true,
					"url":baseUrl+"mgr/type/findtypebytid.do",
					data:{
						"type_id":type_kw
					},
					type:"post",
					dataType:"json",
					success:function(rs){
						if(rs.status==0){
							var table = $("#table").dataTable();
							table.fnClearTable();
							table.fnReloadAjax(baseUrl+"mgr/type/findtypebytid.do?type_id="+type_kw);
							alert("hh");
						}
						else if(rs.status==1&&rs.msg=="ERROR"){
							alert("没有该类型！");
						}
					}
				});
			}
			//按照名称查找商品
			if (query==1){
				$.ajax({
					"xhrFields":{withCredentials:true},
					"crossDomain":true,
					"url":baseUrl+"mgr/type/findtypebyname.do",
					data:{
						"type_name":type_kw
					},
					type:"post",
					dataType:"json",
					success:function(rs){
						if(rs.status==0){
							var table = $("#table").dataTable();
							table.fnClearTable();
							table.fnReloadAjax(baseUrl+"mgr/type/findtypebyname.do?type_name="+type_kw);
						}
						else if(rs.status==1&&rs.msg=="ERROR"){
							alert("没有该类型！");
						}
					}
				});
			}
			
		});
	}
	return {
		showTypes:showTypes,
		typeSearch:typeSearch
	};
});

//渲染按钮
function action(row){
	var str = '<a class="button" href="javascript:void(0);" onclick="findSubType(\''+row.type_id +'\');">查看子类型</a>';
	str += '<a class="button" href="javascript:void(0);" onclick="updateType(\''+row.type_id +'\');" style="margin-left:10px;">编辑</a>';
	str += '<a class="button" href="javascript:void(0);" onclick="deleteType(\''+row.type_id +'\');" style="margin-left:10px;">删除</a>';
	return str;
}

function findSubType(type_id){
	//alert(type_id);
	var table = $("#table").dataTable();
	table.fnClearTable();
	table.fnReloadAjax(baseUrl+"mgr/type/findsubtypevo.do?type_id="+type_id)
}

function deleteType(type_id){
	if (confirm("您确定删除该类型吗？")){
		$.ajax({
			url:baseUrl+"mgr/type/deletetype.do",
			xhrFields:{withCredentials:true},
			crossDomain:true,
			type:"post",
			data:{
				"type_id":type_id
			},
			dataType:"json",
			success:function(rs){
				if (rs.status==0){
					var table = $("#table").dataTable();
					table.fnReloadAjax(baseUrl+"mgr/type/findroot.do");
				}
				
				//如果失败，返回错误信息
				if (rs.status==1){
					alert(rs.msg);
				}
			}
		});0
		return true;
	}
	return false;
}

function updateType(type_id){
	$(window).attr("location","type-update.html?type_id="+type_id);
}

function addType(type_id){
	$(window).attr("location","type-add.html?type_id="+type_id);
}