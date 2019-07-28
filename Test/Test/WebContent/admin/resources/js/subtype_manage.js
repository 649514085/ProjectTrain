define(['common'],function(common){
	var type_id = getParam("type_id");
	function showSubTypes(){
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
				"url":baseUrl+"mgr/type/findsubtype.do?type_id="+type_id
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
	return {
		showSubTypes:showSubTypes
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
	var table = $("#table").dataTable();
	table.fnClearTable();
	table.fnReloadAjax(baseUrl+"mgr/type/findsubtype.do?type_id="+type_id);
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
		});
		return true;
	}
	return false;
}

function updateType(type_id){
	$(window).attr("location","type-update.html?type_id="+type_id);
}