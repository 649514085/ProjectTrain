define(['jquery','common'],function(jquery,common){
	function ready(){
		//显示当前用户信息
		showUser();
		//姓名非空验证
		$("#userName").blur(function(){
			nullValidate("userName","姓名");
		});
		//验证年龄非空，格式
		$("#age").blur(function(){
			if(nullValidate("age","年龄")){
				return;
			}
			var re =/^[0-9]+.?[0-9]*$/;
			if(!re.test($("#age").val())){
				$("#age+div").css("display","block");
				$("#age+div").html("年龄必须为数字");
				return;
			}
			if($("#age").val()<0||$("#age").val()>120){
				$("#age+div").css("display","block");
				$("#age+div").html("年龄在0-120岁之间！");
				return;
			}
			$("#age+div").css("display","none");
		});
		//验证电话非空，格式
		$("#phone_number").blur(function(){
			if (nullValidate("phone_number","手机号")) {
				return;
			}
			var phone=$("#phone_number").val();
			var reg=/^1[0-9]{10}$/;
			if(!reg.test(phone)){
				$("#phone_number+div").css("display","block");
				$("#phone_number+div").html("电话号码格式不正确！");
				return;
			}
			$("#phone_number+div").css("display","none");
		});
				//验证email非空,格式
		$("#email").blur(function(){
			if (nullValidate("email","邮箱")) {
				return;
			}
			var email=$("#email").val();
			var reg=/^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
			if(!reg.test(email)){
				$("#email+div").css("display","block");
				$("#email+div").html("邮箱格式不正确！");
				return;
			}
			$("#email+div").css("display","none");
		});
				//添加保存事件
			$("#btnSave").click(function(){
				if (nullValidate("userName","姓名")) {
					return;
				}
				if (nullValidate("age","年龄")) {
					return;
				}
				if (nullValidate("phone_number","手机号")) {
					return;
				}
				if (nullValidate("email","邮箱")) {
					return;
				}
				var sex="女";
				if($("input:radio[name='sex']").get(0).checked==true){
					sex="男";
				}
				alert($("#userName").val()+" "+$("#age").val());
				var formData={
					name:$("#userName").val(),
					sex:sex,
				    age:$("#age").val(),
					phone:$("#phone_number").val(),
					email:$("#email").val()
				};
				$.ajax({
					url:baseUrl+"user/updateuserinfo.do",
					data:formData,
					xhrFields:{withCredentials:true},
					crossDomain:true,
					type:"post",
					success:function(rs){
						//判断方法是否成功
						alert(rs.msg)
					}
				});
			return false;
			});
	}
	function showUser(){
		$.ajax({
			url:baseUrl+"user/getuserinfo.do",
			xhrFields:{withCredentials:true},
			crossDomain:true,
			success:function(rs){
				//判断方法是否成功
				if(rs.status==0){
					//显示用户信息
					$("#account").val(rs.data.user_id);
				 	$("#userName").val(rs.data.name);
					$("#age").val(rs.data.age);
					$("#phone_number").val(rs.data.phone);
					$("#email").val(rs.data.email);
					if (rs.data.sex==1) {
						$("input:radio[name='sex']").get(0).checked=true;
					}else{
						$("input:radio[name='sex']").get(1).checked=true;
					}
					
				}else{
					alert(rs.msg);
					 $(window).attr("location","login.html");
				}
			}
		});
	}
	//通用非空验证
	function nullValidate(labelId,msg){
		if($("#"+labelId).val()==null||$("#"+labelId).val()==""){
			$("#"+labelId+"+div").css("display","block");
			$("#"+labelId+"+div").html(msg+"不能为空！");
			return true;
		}
		$("#"+labelId+"+div").css("display","none");
		return false;
	}
	return{
		ready:ready,
	};
});