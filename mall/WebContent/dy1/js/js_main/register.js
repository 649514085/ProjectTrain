define(['jquery-1.7.2.min','common'],function(jquery,common){
    //用户名是否有效
    var isUserNameValidate=false;
    
    //密码是否为空
    var isUserPwdValidate=false;
    
    //用户确认密码是否为空
    var isUserRePwdValidate=false;
    
    //用户输入密码是否有效
    var isPwdValidate=false;
    
    //用户手机号是否有效
    var isPhoneValidate=false;
    
    //用户电子邮箱是否有效
    var isEmailValidate=false;
    
    //密码提示问题是否有效
    var isQuestionValidate=false;
    
    //密码提示回答是否有效
    var isAnswerValidate=false;
    
    //1.用户名验证
    $("#username").blur(function(){
    	
        isUserNameValidate=checkUserName("username");
    });
    
    function checkUserName(objId){
        
        var userName=$("#"+objId).val();
        
        $("#usernameError").css({display:"none"});
        
        if(userName==""){
            $("#usernameError").html("请输入用户名！");
            $("#usernameError").css({display:"block"});
            return false;
        }
        
        if(userName.length<3 || userName.length>16){
            $("#usernameError").html("用户名长度错误！");
            $("#usernameError").css({display:"block"});
            return false;
        }
        
        var reg = /^[0-9a-zA-Z]+$/;
        var str = document.getElementById("username").value;
        
        if(!reg.test(str)){
            $("#usernameError").html("用户名只能为数字和英文！");
            $("#usernameError").css({display:"block"});
            return false;
        }
        
        //请求服务器 验证用户名是否已经存在 同步
        
//        var flag = false;
//        $.ajax({
//            url:baseUrl+"user/do_check_info.do?number="+Math.random(),
//            type:"post",
//            data:{info:userName,type:"account"},
//            
//            async:false,//同步请求服务器
//            
//            success:function(rs){
//                
//                if (rs.status==1) {
//                    $("#usernameError").css({display:"block"});
//                    $("#usernameError").html(data.msg);
//                }else{
//                    $("#usernameError").css({display:"none"});
//                    flag=true;
//                }
//            }
//        });
//        
//        return flag;
        return true;
    }

    //2.密码是否为空
    $("#password").blur(function(){
        
    	isUserPwdValidate=checkUserPwd("password");
        
        //如果确认密码已经校验，则需要校验两者是否相同
        if(isUserRePwdValidate){
            isPwdValidate = checkPwdAndRePwd("password","rePassword");
        }
    });
    
    function checkUserPwd(objId){
        
        var pwd=$("#"+objId).val();
        $("#userpasswordError").css({display:"none"});
        
        if(pwd==""){
            $("#userpasswordError").html("请输入密码！");
            $("#userpasswordError").css({display:"block"});
            return false;
        }
        
        if(pwd.length<6 || pwd.length>12){
            $("#userpasswordError").html("密码长度为6-12位！");
            $("#userpasswordError").css({display:"block"});
            return false;
        }
        
        var reg = /^[0-9a-zA-Z]+$/;
        var str = document.getElementById("password").value;
        if(!reg.test(str)){
            $("#userpasswordError").html("密码只能为数字和英文！");
            $("#userpasswordError").css({display:"block"});
            return false;
        }
        return true;
    }
    
    //3.确认密码是否为空  验证两次密码是否一致
    $("#rePassword").blur(function(){
        isUserRePwdValidate=checkReUserPwd("rePassword");
        
        if(isUserPwdValidate && isUserRePwdValidate){
            
        	isPwdValidate = checkPwdAndRePwd("password","rePassword");
        }  
    });
    
    //校验 确认密码 是否为空
    function checkReUserPwd(reObjId){
        
        var rePwd=$("#"+reObjId).val();
        $("#userpasswordError").css({display:"none"});
        
        if(rePwd == ""){
            $("#repasswordError").css({display:"block"});
            return false;
        }
        return true;
    }

    //校验 密码 和 确认密码 是否一致
    function checkPwdAndRePwd(objId,reObjId){
        
        var pwd=$("#"+objId).val();
        var rePwd=$("#"+reObjId).val();
        
        $("#repasswordError").css({display:"none"});
        
        if(!(pwd==rePwd)){
            $("#repasswordError").css({display:"block"});
            $("#repasswordError").html("两次密码不一致");
            return false;
        }
        return true;
    }

    //4.手机号
    $("#phone").blur(function(){
        isPhoneValidate=checkPhone("phone");
    });
    
    function checkPhone(objId){
        
        var phone=$("#"+objId).val();
        $("#phoneError").css({display:"none"});
        
        if(phone==""){
            $("#phoneError").css({display:"block"});
            $("#phoneError").html("请输入手机号！");
            return false;
        }
        
        var reg = /^1[0-9]{10}$/;
        var str = document.getElementById("phone").value;
        
        if(!reg.test(str)){
            $("#phoneError").html("请输入正确的手机号码！");
            $("#phoneError").css({display:"block"});
            return false;
        }
        return true;
    }

    //5.邮箱
    $("#email").blur(function(){
        isEmailValidate=checkEmail("email");
    });
    
    function checkEmail(objId){
        
        var email=$("#"+objId).val();
        $("#emailError").css({display:"none"});
        
        if(email==""){
            $("#emailError").css({display:"block"});
            $("#emailError").html("请输入电子邮箱！");
            return false;
        }
        
        var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
        var obj = document.getElementById("email").value;
        
        if(!reg.test(obj)){
            $("#emailError").html("邮箱格式错误");
            $("#emailError").css({display:"block"});
            return false;
        }
        return true;
    }

    //6.密码提示问题
    $("#question").blur(function(){
        isQuestionValidate=checkQuestion("question");
    });
    
    function checkQuestion(objId){
    	
        var question=$("#"+objId).val();
        $("#questionError").css({display:"none"});
        
        if(question==""){
            $("#questionError").css({display:"block"});
            $("#questionError").html("请输入问题！");
            return false;
        }
        return true;
    }

    //7.答案
    $("#answer").blur(function(){
        isAnswerValidate=checkAnswer("answer");
    });
    
    function checkAnswer(objId){
        
        var answer=$("#"+objId).val();
        $("#answerError").css({display:"none"});
        
        if(answer==""){
            $("#answerError").css({display:"block"});
            $("#answerError").html("请输入答案！");
            return false;
        }
        return true;
    }

    //8.点击注册
    function registBtn(){
        
    	$(".register_btn").click(function(){
            
    		//提交注册时检查校验结果
            if(!isUserNameValidate){
                return checkUserName("username");
            }
            if(!isUserPwdValidate){
                return checkUserPwd("password");
            }
            if(!isUserRePwdValidate){
                return checkReUserPwd("rePassword");
            }
            if(!isPwdValidate){
                return checkPwdAndRePwd("password","rePassword");
            }
            if(!isPhoneValidate){
                return checkPhone("phone");
            }
            if(!isEmailValidate){
                return checkEmail("email");
            }
            if(!isQuestionValidate){
                return checkQuestion("question");
            }
            if(!isAnswerValidate){
                return checkAnswer("answer");
            }

            //提交表单
            var formData={
                user_id:$("#username").val(),
                password:$("#password").val(),
                phone:$("#phone").val(),
                email:$("#email").val(),
                question:$("#question").val(),
                asw:$("#answer").val(),//?????????
            }
            
            //请求服务器
            $.ajax({
                url:baseUrl+"user/do_register.do",
                type:"post",
                data:formData,
                
                success:function(rs){
                	
                    if(rs.status==0){
                        
                        alert("注册成功");
                        
                        $(window).attr("location","login.html");
                    }else{
                        
                        alert(rs.msg);
                    }
                }
            });
        });
    }

    return{
        registBtn:registBtn

    };
});