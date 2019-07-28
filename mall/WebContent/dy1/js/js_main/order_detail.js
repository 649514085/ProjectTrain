define(['jquery','handlebar','common'],function(jquery,Handlebars,common){
	//1.获取订单编号
	var orderNo=common.getParam("orderNo");
	var price;
	var name;
	
	//2.获取订单详情
	function getDetail(){
		$.ajax({
            url:baseUrl+"order/getorderdetail.do",
            //传输方式
            type:"post",
            data:{"orderId":orderNo},
            xhrFields:{withCredentials:true},
            crossDomain:true,
            success:function(rs){
            	if(rs.status==0){
            		//订单信息
            		//var json = JSON.stringify(rs.data);
               	   // alert(json);
            		name=orderNo;
            		price=rs.data.amount;
            		$("#ordernum-container").html(rs.data.orderId);
            		$("#order-created-container").html(rs.data.create_time);
            		$("#order-price-container").html(rs.data.amount);
            		$("#order-status-container").html(rs.data.statusDes);
            		$("#order-paytime-container").html(rs.data.payTime);
            		$("#order-fahuo-container").html(rs.data.deliveryTime);
            		$("#order-finish-container").html(rs.data.finishTime);
            		var address=rs.data.address.province+" "+rs.data.address.city+
            		rs.data.address.district+" "+rs.data.address.addr+" "+rs.data.address.name+" "
            		+rs.data.address.phone+" ";
            		$("#address-container").html(address);
            		//商品订单列表
            		$("#item-container").html();
            		var tpl=$("#product-item-tpl").html();
            		var func=Handlebars.compile(tpl);
            		var result=func(rs.data.orderItems);
            		$("#item-container").html(result);
            		//支付取消
            		if(rs.data.status!=0){
            			$("#order_pay").remove();
            			$("#order_cancel").remove();
            		}
            	}else{
            		alert(rs.msg);
            	}
            }
        });
	}
	//点击取消订单按钮
	function orderCancel(){
		$("#order_cancel").click(function(){
			$.ajax({
	            url:baseUrl+"order/cancelorder.do",
	            //传输方式
	            data:{"orderId":orderNo},
	            xhrFields:{withCredentials:true},
	            crossDomain:true,
	            type:"post",
	            success:function(rs){
	            	if(rs.status==0){
	            		alert(rs.msg);
	            		$(window).attr("location","order_list.html");
	            	}else{
	            		alert(rs.msg);
	            	}
	            	
	            }
	        });
		});
		$("#order_pay").click(function(){
	         window.location.href="pay.html?Pdname="+name+"&Price="+price;
		});
	}
	
	return{
		getDetail:getDetail,
		orderCancel:orderCancel,
	};
});