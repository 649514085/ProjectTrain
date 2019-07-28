define(['jquery'], function(jquery) {
    
    var flag=true;//"下一步"按钮标志，true-输入用户名，false-输入问题
    var userID;//用户ID
    var asw;//答案
    var account;//用户名
    
    //控制显示和隐藏
    
    //用户名检查
    $("#userName").blur(function(){
        if( $("#userName").val()==null || $("#userName").val()==""){
            alert("请输入用户名");
            return;
        }
    });

    //答案检查
    $("answer").blur(function(){
        if( $("#answer").val()==null || $("#answer").val()==""){
            alert("请输入答案");
            return;
        }
    });

    //新密码验证
    $("newPwd").blur(function(){
        newPwdValidate();
    });

    //确认密码验证
    $("surePwd").blur(function(){
        surePwdValidate();
    });

    //密码检查
    function validatePwd(labelId,showName){
        var pwd=$("#"+labelId).val();
        if(pwd==null || pwd==""){
            return "请输入"+showName+"!";
        }
        if(pwd.length<6 || pwd.length>12){
            return "密码长度为6-12位";
        }
        
        var reg = /^[0-9a-zA-Z]+$/;
        if(!reg.test(pwd)){
            return "密码只能为数字或英文";
        }
        return null;
    }

    function newPwdValidate(){
        var ms = validatePwd("newPwd","新密码");
        if(ms==null){
            //成功将错误信息隐藏
            $("#newPwd+div").css("display","none");
            return true;
        }
        //失败提示
        $("#newPwd+div").css("display","block");
        $("newPwd+div").html(ms);
        return false;
    }
    
    function surePwdValidate(){
        //验证密码是否一致
        var newPwd=$("#newPwd").val();
        var surePwd=$("#surePwd").val();
        
        if(newPwd!=surePwd){
            $("#userPwd+div").css("display","block");
            $("#userPwd+div").html("两次密码不一致");
            return false;
        }
        
        //验证是否空
        var ms = validatePwd("surePwd","确认密码");
        //清空
        $("#surePwd+div").css("display","none");
        
        if(ms!=null){
            $("#surePwd+div").css("display","block");
            $("#surePwd+div").html(ms);
            return false;
        }
        $("#surePwd+div").css("display","none");
        return true;

    }

    //下一步按钮
    $(".nextOpt").click(function(){
        if(flag){
            if($("#userName").val()==null || $("#userName").val()==""){
                alert("请输入用户名！");
                return;
            }
            //验证用户名是否正确，展示输入问题部分
            $.ajax({
                url:baseUrl+"user/getuserbyuserid.do",
                xhrFields:{withCredentials:true},
                crossDomain:true,
                type:"post",
                data:{user_id:$("#userName").val()},
                
                success:function(data){
                    if(data.status==0){
                        
                        //当方法成功，数据加入页面，将参数附上数据
                        $("#question").html(data.data.question);
                        userID=data.data.user_id;
                        asw=data.data.asw;
                        
                        //显示问题
                        $("#validateUser").css({display:"none"});
                        $("#validateUser2").css({display:"block"});
                        
                        //设置标志
                        flag=false;
                    }else{
                        //失败返回错误信息
                        alert(data.msg);
                    }
                }
            });
        }else{
            //答案提交
            $.ajax({
                url:baseUrl+"user/checkuserasw.do",
                xhrFields:{withCredentials:true},
                crossDomain:true,
                type:"post",
                data:{user_id:$("#userName").val(),question:$("#question").text(),asw:$("#answer").val()},
                
                success:function(data){
                    if(data.status==0){
                        //显示重置密码，隐藏验证身份
                        $("#validateUser2").css({display:"none"});
                        $("#changePwd").css({display:"block"});
                        $("#changePwd").find("div").first().css("background-image","url()");
                        $("#changePwd p:eq(1)").css("color","#fff")
                        
                        //清空用户名及答案
                        $("#userName").val("");
                        $("#answer").val("");
                    }else{
                        alert(data.msg);
                    }
                }
            });
        }
    return false;
    });

    //重置密码
    $("#submitBtn").click(function(){
        //验证
        if(!newPwdValidate()){
            return;
        }
        if(!surePwdValidate()){
            return;
        }
        //向后台提交重置密码
        $.ajax({
            url:baseUrl+"user/resetpassword.do",
            xhrFields:{withCredentials:true},
            crossDomain:true,
            type:"post",
            data:{"user_id":userID,"newpwd":$("#newPwd").val()},
            
            success:function(data){
                if(data.status==0){
                    //提示成功，跳转登录页面
                    alert(data.msg);
                    $(window).attr("location","/mall/dy1/html/login.html");
                }
            }
        });
   return false;
    });
});