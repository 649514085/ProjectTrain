define(['jquery','common'],function(jquery,common){
    
    function ready(){
        $("#now_password").blur(function(){
            var pwd=$("#now_password").val();
            if(pwd==null || pwd==""){
                $("#now_password_msg").css("display","block");
                $("#now_password_msg").html("当前密码不能为空");
            }else{
                $("#now_password_msg").css("display","none");
            }
        });

        $("#new_password").blur(function(){
            var result=validatePwd("new_password");

            if(result!=null){
                $("#new_password_msg").css("display","block");
                $("#new_password_msg").html(result);
                return;
            }
            
            $("#new_password_msg").css("display","none");   
        });

        $("#password_confirmation").blur(function(){
            var result=validatePwd("password_confirmation");

            if(result!=null){
                $("#password_con_msg").css("display","block");
                $("#password_con_msg").html(result);
                return;
            }

            if($("#new_password").val()!=$("#password_confirmation").val()){
                $("#password_con_msg").css("display","block");
                $("#password_con_msg").html("两次密码不一致");
                return;
            }

            $("#password_con_msg").css("display","none");
        });

        $("#btnSave").click(function(){
            $.ajax({
                url:baseUrl+"user/updatepassword.do",
                xhrFields:{withCredentials:true},
                crossDomain:true,
                type:"post",
                data:{"oldpwd":$("#now_password").val(),"newpwd":$("#password_confirmation").val()},
                
                success:function(data){
                    alert(data.msg);
                    if(data.status==0){
                        $(window).attr("location","login.html");
                    }   
                }
            });
         return false;
        });
    }

    function validatePwd(labelId){ 
        var pwd=$("#"+labelId).val();

        if(pwd==null || pwd==""){
            return "密码不能为空";
        }

        if(pwd.length<6 || pwd.length>12){
            return "密码长度为6-12位";
        }

        var reg=/^[0-9a-zA-Z]+$/;
        if(!reg.test(pwd)){
            return "密码只能为数字和英文";
        }

         return null;
    }

    return{
        ready:ready
    };
});