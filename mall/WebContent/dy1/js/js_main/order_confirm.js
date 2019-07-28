define(['jquery_SuperSlide','handlebar'],function(jquery,Handlebars){
	//读取用户生成的订单购物车信息
	function getCartInfo(){
		$.ajax({
            url:baseUrl+"cart/findallcheckedcarts.do",
            xhrFields:{withCredentials:true},
            crossDomain:true,
            type:"post",
            success:function(rs){
            	//更新页面
            	updatePageInfo(rs);
            	
            }	
        });
	}
	//根据返回数据更新页面信息
	function updatePageInfo(rs){
		if(rs.status==0){
			//信息更新		
			$("#order_confirm_item_container").html();
			var tpl=$("#product-item-tpl").html();
			var func=Handlebars.compile(tpl);
			var result=func(rs.data.lists);
			
			$("#order_confirm_item_container").html(result);
			//更新购物车总价格
			$("#amount").html("￥"+rs.data.totalPrice);
		} else {
			$(window).attr("location","login.html");
		}
	}
	//提交确认生成订单
	function submitBtn(){
		$("#submit").click(function(){
			//收货人地址id
			var id=$(this).attr("data-id");
			//提交订单
			$.ajax({
	            url:baseUrl+"order/createorder.do",
	            xhrFields:{withCredentials:true},
	            crossDomain:true,
	            data:{addrId:id},
	            type:"post",
	            success:function(rs){
	            	if(rs.status==0){
	            		//跳转到订单详情页面
	            		$(window).attr("location","order_detail.html?orderNo="+rs.data.orderId);
	            	}else{
	            		alert(rs.msg);
	            	}
	            }	
	        });
		});
	}
	return{
		getCartInfo:getCartInfo,
		submitBtn:submitBtn
	};
});