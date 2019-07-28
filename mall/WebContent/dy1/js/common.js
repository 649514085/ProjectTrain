var baseUrl="";//修改端口或项目名

define(function() {
    
    //获取url中的参数
    function getParam(name) {
        //构造一个含有目标参数的正则表达式对象
        var reg = new RegExp("^|&"+name+"=([^&]*)(&|$)");
        //匹配目标参数
        var r = window.location.search.substr(1).match(reg);
        //返回参数值
        if (r!=null) return decodeURI(r[2]);return null;
    }
    
    //加载登陆用户信息
    function getUserInfo(){
        //向服务器请求数据，用到AJAX
        //填写AJAX需要的一些键值对
        /*
        首先是url，需要用到接口文档，
        接口文档是前端与后台交互的参考文档，这里表面标明这许多接口，把数据请求、请求地址、返回信息都罗列在了上面
        通过看接口文档可以知道，我们请求需要那些参数，返回是返回哪些数据
        */
        //获取用户信息接口，请求地址
        //将地址的相同部分提取出来，写成全局变量
        $.ajax({
            url:baseUrl+"",//通过Get请求，并不需要传输数据，所以Ajax中不需要写data
            xhrFields:{withCredentials:true},//允许跨域请求携带cookie属性
            crossDomain:true,//可以进行跨域请求
            //可以使用户数据跟随请求到其他页面，因为会有多个页面用到用户信息
            
            success:function*(uesr){
                //判断是否成功
                if (user.status==0) {
                    //隐藏登陆时span标签
                    $("#register_info").css({display:"none"});
                    //显示登陆后span标签
                    $("#login_info").css({display:"block"});
                    //将用户名添加到对应位置
                    $("#LoginName").html(user.data.account);
                    //获取用户购物车商品数量
                    getCartCount();
                }
            }
        })
    }
    
    //获取用户购物车商品数量
    function getCartCount() {
        $.ajax({
            url:baseUrl+"",
            xhrFields:{withCredentials:true},
            crossDomain:true,
            success:function*(rs){
                //判断是否成功，失败时不需要返回任何东西
                if (user.status==0) {
                    //插入数据，需要span ID
                    $("#ID").html("["+rs.data+"]");
                }
            }
        })
    }

    //用户登出
    function loginout() {
        //给退出按钮挂上单击事件
        //Ajax就是页面不需要全部刷新，只刷新局部
        $("#LoginOut").click(function () {
            //向服务器请求数据
            $.ajax({
                url:baseUrl+"",
                xhrFields:{withCredentials:true},
                crossDomain:true,
                success:function*(rs){
                    //判断是否成功，失败时不需要返回任何东西
                    if (user.status==0) {
                        //显示登陆时span标签
                        $("#register_info").css({display:"block"});
                        //隐藏登陆后span标签
                        $("#login_info").css({display:"none"});
                        //清空购物车数量
                        $("#ID").html("[0]");
                    }
                }
            })
        })
    }

    return{
        getParam:getParam,
        getUserInfo:getUserInfo,
        getCartCount:getCartCount,
        loginout:loginout
    };
});