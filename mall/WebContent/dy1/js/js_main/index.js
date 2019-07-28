define(['jquery_SuperSlide','common','handlebar'],function(jquery_SuperSlide,common,Handlebars){
	//1、加载产品分类
	function getParam(){
		    $.ajax({
            url:baseUrl+"param/findallparams.do",//通过Get请求，并不需要传输数据，所以Ajax中不需要写data
            xhrFields:{withCredentials:true},//允许跨域请求携带cookie属性
            crossDomain:true,//可以进行跨域请求
            type:"get",
            success:function(rs){
            	//创建对象。预编译handlebar
            	var tpl=$("#param_tpl").html();
            	var func=Handlebars.compile(tpl);
            	//获取数据
            	var result=func(rs.data);
            	
            	//插入页面
            	$("#paramContainer").html(result);
            	
                }
            });

	}
	//2、加载热销商品
	function getHotProduct(){
		$.ajax({
            url:baseUrl+"product/findhotproducts.do",//通过Get请求，并不需要传输数据，所以Ajax中不需要写data
            xhrFields:{withCredentials:true},//允许跨域请求携带cookie属性
            crossDomain:true,//可以进行跨域请求
            type:"post",
            data:{num:5},
            success:function(rs){
            	//创建对象预编译插件
            	var tpl =$("#hot_tpl").html();
            	var func=Handlebars.compile(tpl);
            	//获取数据 处理数据 （图片：）
            	var data = new Array();
//            	var json = JSON.stringify(rs.data);
//            	alert(json);
            	for(var i=0;i<rs.data.length;i++){
            		rs.data[i].main_image=baseUrl+rs.data[i].main_image;
            		data[i]=rs.data[i];
            		if(i>=4){
            			//前台只展示5条
            			break;
            		}
            	}
            	//添加数据，插入页面和一定JS修改
            	var result=func(data);
            	$("#hotContainer").html(result);
//            	为最后一个li添加样式
//            	$("#hotContainer>li:last-child").add("right_border");
                }
            });

	}
	//3、加载楼层商品
	function getFloors(){
		$.ajax({
            url:baseUrl+"product/findfloor.do",//通过Get请求，并不需要传输数据，所以Ajax中不需要写data
            xhrFields:{withCredentials:true},//允许跨域请求携带cookie属性
            data:{num:6},
            crossDomain:true,//可以进行跨域请求
            type:"post",
            success:function(rs){
            	//判断是否成功
            	if(rs.status==1){
            			return;
        		}
//            	var json = JSON.stringify(rs.data);
//            	alert(json);
        		//1楼数据 获取数据（修改图片路径）预编译插件
            	var data1 = rs.data.oneFloor;
        		//var data1 =new Array();
        		for(var i=0;i<data1.length;i++){
        			data1[i].main_image=baseUrl+data1[i].main_image;
        		}
        		var func =Handlebars.compile($("#floor_odd_top").html());
        		$("#floor_one_m").html();
        		$("#floor_one_m").append(func(data1));
        		//2楼数据
        		var data2 =rs.data.twoFloor;
        		for(var i=0;i<data2.length;i++){
        			data2[i].main_image=baseUrl+data2[i].main_image;
        		}
        		$("#floor_two_m").html();
        		$("#floor_two_m").append(func(data2));

        		//3楼数据
        		var data3 =rs.data.threeFloor;
        		for(var i=0;i<data3.length;i++){
        			data3[i].main_image=baseUrl+data3[i].main_image;
        		}
        		$("#floor_three_m").html();
        		$("#floor_three_m").append(func(data3));

        		//4楼数据
        		var data4 =rs.data.fourFloor;
        		for(var i=0;i<data4.length;i++){
        			data4[i].main_image=baseUrl+data4[i].main_image;
        		}
        		$("#floor_four_m").html();
        		$("#floor_four_m").append(func(data4));


        	}

        });

	}
	//4.首页轮播
	$(".slide_box").slide({mainCell:".bd ul",effect:"leftLoop",autoPlay:true});
	
	//5.产品分类
	$(".iten").live({
		mouseenter:function(){
			//获取对象
			var children_div =$(this).children("div");
			var t_this = $(this);
			//子分类显示
			t_this.find('.item_hd').css({border:'none'});
			t_this.prev().find('.item_hd').css({border:'none'});

			children_div.show();
			$(this).addClass("selected");
		},
		mouseleave:function(){
			//获取对象
			var children_div =$(this).children("div");
			//子分类隐藏
			$(this).find('.item_hd').css({ 'border-bottom':'1px dotted white'});
			$(this).prev().find('.item_hd').css({ 'border-bottom':'1px dotted white'});
			children_div.hide();
			$(this).removeClass("selected");

		}
	});
	
	//6、单击搜索功能
	$("#searchBtn").click(function(){
		var proName = $("#keyword").val();
		$(window).attr("location","product_search.html?name="+proName);
	});


	return{
		getParam:getParam,
		getHotProduct:getHotProduct,
		getFloors:getFloors
	};
});