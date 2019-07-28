define(function(){
	var type_id = getParam("type_id");
	
	//获得类型信息
	function getTypeInfo(){
		$.ajax({
			"xhrFields":{withCredentials:true},
			"crossDomain":true,
			"url":baseUrl+"mgr/type/findtypebytid.do",
			data:{
				"type_id":type_id
			},
			dataType:"json",
			success:function(rs){
				//alert("rs"+rs);
				if(rs.status==0){
					alert(rs.data[0].type_id);
					$("#type_id").val(rs.data[0].type_id);
					$("#type_name").val(rs.data[0].type_name);
					$("#gparent").val(rs.data[0].gparent_id);
					$("#parent").val(rs.data[0].parent_id);
					
					var gp = rs.data[0].gparent_id;
					var p = rs.data[0].parent_id;
					loadFirstTypes(gp);
					//loadSecondTypes(gp);
				}
				if(rs.status==1){
					alert(rs.msg);
				}
			}
		});
	}
	
	function updateTypeInfo(){
		$("#save").click(function(){
			var type_id = $("#type_id").val();
			var gparent = $("#gparent").val();
			var parent = $("#parent").val();
			var type_name = $("#type_name").val();
			
			var rank = 0;
			var gparent_real = 0;
			var parent_real = 0;
			
			if(gparent==-1){
				//如果没有选择祖父类型，则祖父类型的真实值是null
				gparent_real = null;
				rank = 1;
			}else{
				//如果选择了祖父类型，但是没有选择父类型
				//则该类型为第二层类型，其父亲类型即为选择的祖父类型
				if(parent==-1){
					parent_real = gparent;
					rank = 2;
				}
				//如果选择了祖父类型且选择了父类型
				//则该类型为第三层类型
				else{
					parent_real = parent;
					rank = 3;
				}
			}
		
			$.ajax({
				"xhrFields":{withCredentials:true},
				"crossDomain":true,
				"url":baseUrl+"mgr/type/updatetype.do",
				type:"post",
				data:{
					"type_id":type_id,
					"gparent_id":gparent_real,
					"parent_id":parent_real,
					"type_name":type_name,
					"rank":rank
				},
				dataType:"json",
				success:function(rs){
					//alert("rs"+rs);
					if(rs.status==0){
						$(window).attr("location","type-manager.html");
					}
					if(rs.status==1){
						alert(rs.msg);
					}
				}
			});
		});
	}
	
	function dropDownEvent(){
		$("#gparent").change(function(){
			//alert("11");
			var val = $("#gparent").val();
			if (val!=-1){
				//显示父类型
				loadSecondTypes(val);
			}
//			else{
//				//隐藏父类型
//				$("#parent").css({"display":"none"});
//				$("#parent_label").css({"display":"none"});
//			}
		});
	}
	
	return {
		getTypeInfo:getTypeInfo,
		dropDownEvent:dropDownEvent,
		updateTypeInfo:updateTypeInfo
		
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

function loadFirstTypes(gp){
	$.ajax({
		"xhrFields":{withCredentials:true},
		"crossDomain":true,
		"url":baseUrl+"mgr/type/findtypebyrank.do",
		data:{
			"rank":1
		},
		type:"get",
		dataType:"json",
		success:function(rs){
			//alert(rs.data);
			if(rs.status==0){
				$("#gparent").html("");
				var tpl = $("#param_item_tpl").html();
				var func = Handlebars.compile(tpl);
				var data = rs.data;
				var result = func(data);
				$("#gparent").html(result);
				$("#gparent").prepend("<option selected value='-1'>请选择祖父类型</option>");
				//alert("jjj");
			}
			if(rs.status==1){
				alert(rs.msg);
			}
		}
	});
}



function loadSecondTypes(gp){
	$.ajax({
		"xhrFields":{withCredentials:true},
		"crossDomain":true,
		"url":baseUrl+"mgr/type/findsubtype.do",
		data:{
			"type_id":gp
		},
		type:"get",
		dataType:"json",
		success:function(rs){
			//alert(rs.data);
			if(rs.status==0){
				$("#parent").html("");
				var tpl = $("#param_item_tpl").html();
				var func = Handlebars.compile(tpl);
				var data = rs.data;
				var result = func(data);
				$("#parent").html(result);
				$("#parent").prepend("<option selected value='-1'>请选择父类型</option>");
			}
			if(rs.status==1){
				alert(rs.msg);
			}
		}
	});
}

