define(['common'],function(common){
	//绑定登陆点击事件
	function clickLogin(){
		$("#login").click(function(){
			//获取用户名，以此来判断用户名是否填写
			var input_account = $('#account').val();
			if (input_account == ""){
				$("#errMsg").html("请输入用户名！");
				$("#account").focus;
				return false;
			}
			//获取密码，以此来判断密码是否填写
			var input_password = $('#password').val();
			if (input_password == ""){
				$("#errMsg").html("请输入密码！");
				$("#password").focus;
				return false;
			}
			
			
			$.ajax({

				url:baseUrl+"mgr/admin/login.do",
				xhrFields:{withCredentials:true},
				crossDomain:true,
				type:"post",
				data:{
					"account":input_account,
					"password":input_password
				},
				success:function(rs){
					//如果成功，直接跳转到后台首页页面
					if (rs.status==0){
						$(window).attr("location","starter.html");
					}
					
					//如果失败，返回错误信息
					if (rs.status==1){
						alert("用户不存在或密码错误！");
					}
				}
			});
		})
	};
	
	return {
		clickLogin:clickLogin,
		//hh:hh
	};
});