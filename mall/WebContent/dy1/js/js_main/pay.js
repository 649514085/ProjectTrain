define(['jquery','common'],function(jquery,common){
	//1.获取订单编号
	var name=common.getParam("Pdname");
	var price=common.getParam("Price");
	
	//2.获取订单详情
	function ready(){
		$(".price").html(price);
		$("#paybtn").click(function(){
			if($("input:radio[name='pay']").get(0).checked==true){
				window.location.href="../jsp/index.jsp?name="+name+"&price="+price;
			}else if($("input:radio[name='pay']").get(1).checked==true){
				alert("暂不支持微信支付");
			}else{
				alert("暂不支持银联支付");
			}
	         
		});
	}

	return{
		ready:ready,
	};
});