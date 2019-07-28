define(['common'],function(common){
	//商品表格初始化，展示所有用户
	function showUsers(){
		
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
				"url":baseUrl+"mgr/admin/finduserlist.do"
			},
			"columns":[
				{"data":"user_id"},
				{"data":"sex"},
				{"data":"phone"},
				{"data":"email"},
				{"data":null}
			],

			columnDefs:[
				{
					targets:4,
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
	
	function userSearch(){
		$("#search").click(function(){
			var user_id = $("#user-kw").val();
			if(user_id==""){
				alert("请输入查询词！");
			}else{
				$.ajax({
					"xhrFields":{withCredentials:true},
					"crossDomain":true,
					"url":baseUrl+"mgr/admin/finduser.do",
					data:{
						"user_id":user_id
					},
					type:"post",
					dataType:"json",
					success:function(rs){
						//alert("rs"+rs);
						if(rs.status==0){
							//alert("user:"+user_id);
							var table = $("#table").dataTable();
							table.fnClearTable();
							table.fnReloadAjax(baseUrl+"mgr/admin/finduser.do?user_id="+user_id);
						
						}
						else if(rs.status==1&&rs.msg=="ERROR"){
							alert("没有该用户！");
						}
					}
				});
			}
		});
	}
	
	return {
		showUsers:showUsers,
		userSearch:userSearch
	};
});

//渲染按钮
function query(row){
	
	var str = '<a class="button" href="javascript:void(0);" onclick="updateUser(\''+row.user_id +'\');">编辑</a>';
	str += '<a class="button" href="javascript:void(0);" onclick="deleteUser(\''+row.user_id +'\');" style="margin-left:10px;">删除</a>';
	return str;
}

function updateUser(user_id){
	$(window).attr("location","user-update.html?user_id="+user_id);
}

function deleteUser(user_id){
	if (confirm("您确定删除该用户吗？")){

		$.ajax({

			url:baseUrl+"mgr/admin/deleteuser.do",
			xhrFields:{withCredentials:true},
			crossDomain:true,
			type:"post",
			data:{
				"user_id":user_id
			},
			dataType:"json",
			success:function(rs){
				//如果成功，直接跳转到后台首页页面
				if (rs.status==0){
					var table = $("#table").dataTable();
					table.fnClearTable();
					table.fnReloadAjax(baseUrl+"mgr/admin/finduserlist.do");
				}
				
				//如果失败，返回错误信息
				if (rs.status==1){
					alert(rs.msg);
				}
			}
		});
	}
}

