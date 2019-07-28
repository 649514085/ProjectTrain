define(['common'],function(common){
    
    var isAccountValidate=false;
    var isPasswordValidate=false;
    
    //失去光标时验证用户名
    function accountCheck() {
        $("#username").blur(function () {
            isAccountValidate=checkAccount();
        })
    }
    
    //失去光标时验证密码
    function pwdCheck() {
        $("#password").blur(function () {
            isPasswordValidate=checkPwd();
        })
    }

    //登录
    function loginBtn() {
        //创建单击事件
        $(".login_btn").click(function () {
            //判断验证信息
            //失败返回信息
            if (!isAccountValidate) {
                return checkAccount();
            }
            if (!isPasswordValidate) {
                return checkPwd();
            }            
            
            //成功进入接口登录
            $.ajax({
                url:baseUrl+"user/do_login.do",
                //传输方式
                type:"post",
                data:{user_id:$("#username").val(),password:$("#password").val()},
                xhrFields:{withCredentials:true},
                crossDomain:true,
                success:function(data){
                	// 
                	//判断是否登录成功
                	if(data.status==0){
                		$(window).attr("location","/mall/dy1/html/index.html");
                	}else{
                		$("#passwordError").css({display:"block"});
                        $("#passwordError").html(data.msg);
                	}
                   
                         
                    //
                }
            });
            return false;
        });
    }
    function hhh(){alert("hhh");}
    

    return{
        accountCheck:accountCheck,
        pwdCheck:pwdCheck,
        loginBtn:loginBtn,
    };

    //检查用户名
    function checkAccount() {
        //获取用户名
        var account=$("#username").val();
        //再一次输入时清空本身的错误信息
        $("#usernameError").css({display:"none"});
        
        if (account=="") {
            $("#usernameError").css({display:"block"});
            $("#usernameError").html("用户名不能为空！");
            
            return false;
        }
        return true;
    }
    
    //检查密码
    function checkPwd() {
        //获取输入密码
        var pwd=$("#password").val();
        
        $("#passwordError").css({display:"none"});
        
        if (pwd=="") {
            $("#passwordError").css({display:"block"});
            $("#passwordError").html("密码不能为空！");
            return false;
        }
        return true;
    }
});