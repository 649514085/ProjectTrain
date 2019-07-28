define(['jquery','ChineseDistricts','distpicker','handlebar','common'],function(jquery,distpicker_data,distpicker,Handlebars,common){
	var aId=null;
	Handlebars.registerHelper("state",function(default_addr,id){
		if(default_addr==1){
			return new Handlebars.SafeString("<div addr_id="+id+" class='t_default'>默认地址</div>");
		}
		return new Handlebars.SafeString('<button class="btn_default" addr_id='+id+' style="background:#fff;border:none;">设为默认</button>')
	});
	function ready(){
		//设置三级联动不选中
		$("#distpicker1").distpicker("destroy");
		$("#distpicker1").distpicker({
			autoSelect:false
		});
		//显示所有地址信息
		showAddressInfo();
		//设置默认地址事件绑定
		$("#address_container").on("click",'.btn_default',function(){
			var addrId=$(this).attr("addr_id");
			$.ajax({
			url:baseUrl+"addr/setdefault.do",
			xhrFields:{withCredentials:true},
			crossDomain:true,
			data:{"addr_id":addrId},
			type:"post",
			success:function(rs){
					//数据成功返回时，将后台信息添加到前端
					updatePageInfo(rs);
					
				
			}
			});
		});
		//修改事件绑定
		$("#address_container").on("click",".addr_update",function(){
			var addrId=$(this).attr("addr_id");
			$.ajax({
			url:baseUrl+"addr/findaddrbyaddrid.do",
			type:"post",
			xhrFields:{withCredentials:true},
			crossDomain:true,
			data:{"addr_id":addrId},
			success:function(rs){
					//数据成功返回时，将后台信息添加到前端
					fillAddress(rs.data);
					aId=rs.data.addr_id;
			}
			});
		return false;
		});
		//删除事件绑定
		$("#address_container").on("click",".addr_del",function(){
			var result=confirm("要删除该地址吗？");
			if(!result){
				return;
			}
			var addrId=$(this).attr("addr_id");
			$.ajax({
			url:baseUrl+"addr/deladdr.do",
			xhrFields:{withCredentials:true},
			crossDomain:true,
			data:{"addr_id":addrId},
			type:"post",
			success:function(rs){
				//数据成功返回时，将后台信息添加到前端
				updatePageInfo(rs);
			}
			});
		return false;
		});
		//保存
		$("#btnSave").click(function(){
			//验证
			if(!validate()){
				return;
			}
			//提取数据提交后台
			var formData={
				"name":$("#consignee").val(),
				"phone":$("#phone").val(),
				"province":$("#eprovinceName").find("option:selected").text(),
				"city":$("#ecityName").find("option:selected").text(),
				"district":$("#edistrictName").find("option:selected").text(),
				"addr":$("#detailAddr").val()
			};
			//通过aID判断添加还是修改
			if(aId!=null){
				formData["addr_id"]=aId;
				aId=null;
			}
            $.ajax({
			url:baseUrl+"addr/saveaddr.do",
			data:formData,
			type:"post",
			xhrFields:{withCredentials:true},
			crossDomain:true,
			success:function(rs){
					//数据成功返回时，更新信息
					updatePageInfo(rs);
					//清空地址信息
					clearAddress();
					alert("操作成功");

				}
	        });
		});
	}
	function showAddressInfo(){
		$.ajax({
			url:baseUrl+"addr/findaddrs.do",
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
			var tpl=$("#address_tpl").html();
		
			var func=Handlebars.compile(tpl);
			var result=func(rs.data);

			$("#address_container").html(result);
			
		}else{
			alert(rs.msg);
			 $(window).attr("location","login.html");
		}
	}
	//获得地址信息，填充标签显示
	function fillAddress(data){
		//填充省份市区
		$("#distpicker1").distpicker("destroy");//销毁实例
		$("#distpicker1").distpicker({
			province:data.province,
			city:data.city,
			district:data.district
		});
		//填充其他
		$("#detailAddr").val(data.addr);
		$("#consignee").val(data.name);
		$("#phone").val(data.mobile);
		
	}
	//验证
	function validate(){
		var province=$("#eprovinceName").find("option:selected").attr("data-code");
		if(province==null||province==""){
			alert("请选择省份!");
			return false;
		}
		var city=$("#ecityName").find("option:selected").attr("data-code");
		if(city==null||city==""){
			alert("请选择城市!");
			return false;
		}
		var district=$("#edistrictName").find("option:selected").attr("data-code");
		if(district==null||district==""){
			alert("请选择区!");
			return false;
		}
		var detail=$("#detailAddr").val();
		if(detail==null||detail==""){
			alert("请输入详细地址！");
			return false;
		}
		var name=$("#consignee").val();
		if(name==null||name==""){
			alert("请输入收货人姓名！");
			return false;
		}
		var phone=$("#phone").val();
		if(phone==null||phone==""){
			alert("请输入收货人电话！");
			return false;
		}
		var reg=/^1[0-9]{10}$/;
		if(!reg.test(phone)){
			alert("请输入正确格式的手机号！");
			return false;
		}
		return true;
	}
	//清空地址信息
	function clearAddress(){
		$("#distpicker1").distpicker("destroy");
		$("#distpicker1").distpicker({
			autoSelect:false
		});
		$("#consignee").val('');
		$("#phone").val('');
		$("#detailAddr").val('')

	}
	return{
		ready:ready
	};
});