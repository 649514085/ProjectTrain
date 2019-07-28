define(['jquery','handlebar','common'],function(jquery,Handlebars,common){
	var aId=null;
	
	function ready(){
		showCollectInfo();
		//取消收藏绑定
		$("#collect_container").on("click",".collect_del",function(){
			var result=confirm("您确定要取消收藏吗？");
			if(!result){
				return;
			}
			var collectId=$(this).attr("collect_id");
			$.ajax({
			url:baseUrl+"collect/deletecollect.do",
			xhrFields:{withCredentials:true},
			crossDomain:true,
			data:{"productId":collectId},
			type:"post",
			success:function(rs){
				//数据成功返回时，将后台信息添加到前端
				updatePageInfo(rs);
			}
			});
		return false;
		});
	}
	function showCollectInfo(){
		$.ajax({
			url:baseUrl+"collect/findcollects.do",
			xhrFields:{withCredentials:true},
			crossDomain:true,
			type:'post',
			success:function(rs){
					//数据成功返回时，将后台信息添加到前端
				
					updatePageInfo(rs);
					
				}
	});
    }	
	function updatePageInfo(rs){
		if(rs.status==0){
			
			var tpl=$("#collect_tpl").html();
		
			var func=Handlebars.compile(tpl);
			var result=func(rs.data);
//			var json = JSON.stringify(rs.data);
//        	   alert(json);
			$("#collect_container").html(result);
			
		}else{
			$(window).attr("location","login.html");
		}
	}
	return{
		ready:ready
	};
});