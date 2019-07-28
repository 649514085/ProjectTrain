require.config({       
    paths:{
    	"jquery":"/mall/dy1/js/js_main/jquery-1.7.2.min",
		
    }
});


require(['jquery','common','login'],function(jquery,common,login){
    $(function () {
        //失去光标时验证用户名
        login.accountCheck();
        //失去光标时验证密码
        login.pwdCheck();
        //登录
        login.loginBtn();
    })
});