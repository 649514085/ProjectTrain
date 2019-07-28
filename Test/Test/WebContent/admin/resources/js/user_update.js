define(function(){
	var user_id = getParam("user_id");

	//获得用户信息
	function getUserInfo(){
		$.ajax({
			"xhrFields":{withCredentials:true},
			"crossDomain":true,
			"url":baseUrl+"mgr/admin/finduserone.do",
			data:{
				"user_id":user_id
			},
			dataType:"json",
			success:function(rs){
				//alert("rs"+rs);
				if(rs.status==0){
					$("#user_id").val(rs.data.user_id);
					$("#phone").val(rs.data.phone);
					$("#email").val(rs.data.email);
					
					if (rs.data.sex==("男")){
						document.getElementById("man").checked = "checked";
					}else{
						document.getElementById("woman").checked = "checked";
					}
				}
				if(rs.status==1){
					alert(rs.msg);
				}
			}
		});
	}
	
	function updateUserInfo(){
		$("#save").click(function(){
			var user_id = $("#user_id").val();
			var sex_int = $('input:radio[name="sex"]:checked').val();
			var sex = "";
			if(sex_int==1){
				sex = "男";
			}
			if(sex_int==0){
				sex = "女";
			}
			var phone = $("#phone").val();
			var email = $("#email").val();
			$.ajax({
				"xhrFields":{withCredentials:true},
				"crossDomain":true,
				"url":baseUrl+"mgr/admin/updateuser.do",
				data:{
					"user_id":user_id,
					"sex":sex,
					"phone":phone,
					"email":email
				},
				type:"post",
				dataType:"json",
				success:function(rs){
					//alert("rs"+rs);
					if(rs.status==0){
						$(window).attr("location","user-manager.html");
					}
					else{
						alert(rs.msg);
					}
				}
			});
		});
	}
	
	return {
		getUserInfo:getUserInfo,
		updateUserInfo:updateUserInfo
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

