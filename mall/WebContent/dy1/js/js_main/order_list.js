define(['jquery','common','handlebar'],function(jquery,common,Handlebars){
	var status=-1;//订单状态
	var pageNum=1;//当前页
	var pageSize=2;//一页数量
	var orderNo=common.getParam("out_trade_no");
	function ready(){
		if(orderNo!=null&&orderNo!=""){
			 $.ajax({
	               url:baseUrl+"order/payorder.do",
	               data:{'orderId':orderNo},
	               xhrFields:{withCredentials:true},
	               crossDomain:true,
	               success:function(rs){
	            	 if(rs.status==0){
	            		//方法成功时
	            		 alert(rs.msg);
	            		 getOrders(-1,1,pageSize,null);
	            	 }else{
	            		 alert(rs.msg);
	            	 }
	               }
	           });
		}
		//点击全部订单待付款，待收货事件
		$(".nav_item").live("click",function(){
			//获得标签位置拿到状态
			 status=$(this).attr("data-status");
			//清除选中样式
			$(".nav_item").removeClass("active");
			//添加点击标签的选中样式
			$(this).addClass("active");
			//获取订单信息
			pageNum=1;
			getOrders(status,pageNum,pageSize,null);
		});
		
		//绑定分页点击事件
		$(".btn_prev").click(function(){
			//获取上一页页码
			pageNum=$(".btn_prev").attr("data-page");
			getOrders(status,pageNum,pageSize);
		});
		$(".btn_next").click(function(){
			//获取下一页页码
			pageNum=$(".btn_next").attr("data-page");
			getOrders(status,pageNum,pageSize);
		});
		$(".confirm_receipt").live("click",function(){
			//获取订单号
			var orderNo=$(this).attr("data-orderno");
			//向服务器发送请求
			 $.ajax({
	               url:baseUrl+"order/confirmorder.do",
	               data:{'orderId':orderNo},
	               xhrFields:{withCredentials:true},
	               crossDomain:true,
	               success:function(rs){
	            	 if(rs.status==0){
	            		//方法成功时
	            		 alert(rs.msg);
	            		 $(window).attr("location","order_list.html");
	            	 }else{
	            		 alert(rs.msg);
	            	 }
	               }
	           });
		});
		
		$(".del_receipt").live("click",function(){
			//获取订单号
			var orderNo=$(this).attr("data-orderno");
			//向服务器发送请求
			 $.ajax({
	               url:baseUrl+"order/cancelorder.do",
	               data:{'orderId':orderNo},
	               xhrFields:{withCredentials:true},
	               crossDomain:true,
	               success:function(rs){
	            	 if(rs.status==0){
	            		//方法成功时
	            		 alert(rs.msg);
	            		 $(window).attr("location","order_list.html");
	            	 }else{
	            		 alert(rs.msg);
	            	 }
	               }
	           });
		});
		//获取订单列表
		getOrders(-1,1,pageSize,null);
	}
	//获得订单列表
	function getOrders(status,pageNum,pageSize,callBack){
		   $.ajax({
               url:baseUrl+"order/getorderlist.do",
               data:{'status':status,'pageNum':pageNum,'pageSize':pageSize},
               xhrFields:{withCredentials:true},
               crossDomain:true,
               success:function(rs){
            	  //方法成功时
            	   if(rs.status==0){
            		  //内容嵌入
                	  $("#order-list-container") .html();
                	  var tpl=$("#order-item-tpl").html();
                	  var func=Handlebars.compile(tpl);
                	  var result=func(rs.data.data);
                	  $("#order-list-container").html(result);
                	 // var json = JSON.stringify(rs.data.data);
                	  //alert(json);
                   	  //处理分页显示
                	  $(".btn_prev").attr("data-page",rs.data.prePage);
                	  $(".btn_next").attr("data-page",rs.data.nextPage);
                	  $(".page_num").attr("data-page",rs.data.pageNum);
                	  $(".page_num").html(rs.data.pageNum);
                	  $(".page_count").html("共"+rs.data.totalPage+"页")
                   	   
                   	  //各种状态的订单数量显示
                	  //全部订单
                   	  if(status==-1){
                   		  $("#all_num").html("("+rs.data.totalRecord+")");
                   		  $(".pay_order"+3).html("查看订单");
                   		  $(".pay_order"+4).html("查看订单");
                   		  $(".pay_order"+6).html("查看订单");
                   	  }
                	  //待付款
                	  if(status==0){
                		  $("#no_pay_num").html("("+rs.data.totalRecord+")");
                	  }
                	  //待收货
                	  if(status==1){
                		  $("#unshipped_num").html("("+rs.data.totalRecord+")");
                		  $("pay_order"+3).remove();
                		  $(".confirm_receipt").attr("style","display:block");
                	  }
                   	  //回调函数调用
            	   }else{
            		   alert(rs.msg);
  					 $(window).attr("location","login.html");
            	   }
            	   if(callBack)callBack();
               	
               }
           });
	}
	return{
		ready:ready,
	};
});