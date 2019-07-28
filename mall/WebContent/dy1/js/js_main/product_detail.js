define(['jquery','common'],function(jquery,common){
    
    //读取传递过来的商品编号
    var pid=common.getParam("pid");
    var iscollected=false;
    //1.获取商品数据
    function ready(){
    	$.ajax({
			url:baseUrl+"collect/checkcollected.do",
			xhrFields:{withCredentials:true},
			crossDomain:true,
			data:{"productId":pid},
			type:"post",
			success:function(rs){
				if(rs.status==0){
					var img = document.getElementById('favorites');
					if(rs.msg=="已收藏"){
						iscollected=true;
						img.src='../src/common/favorites2.jpg';
					}else{
						iscollected=false;
						img.src='../src/common/favorites.jpg';
					}
				}else{
					alert(rs.msg);
					 $(window).attr("location","login.html");
					
				}
			}
			});
        $.ajax({
            url:baseUrl+"product/getdetail.do",//商品详情数据接口
            xhrFields:{withCredentials:true},
            crossDomain:true,
            data:{'productId':pid},
            type:"post",
            success:function(result){
                //判断方法是否成功
                if(result.status==0){
//                	var json = JSON.stringify(result.data);
//                	alert(json);
                    //产品名称
                    $("#product_name_container").html(result.data.product_name);
                    
                    //将产品Id作为属性添加到product_name中
                    $("#product_name_container").attr("data-id",result.data.product_id);
                    
                    //产品价格
                    $("#product_price_container").html("￥"+result.data.price);
                    
                    //主图
                    $("#big_pic").attr("src",baseUrl+result.data.main_image);
                    $("#big_pic").addClass(".product_picture_img");
                    
                    //详情
                    $("#detailContainer").html(result.data.detail);
                   
                    //规格参数
                    $("#specParamContainer").html(result.data.spec_param);
                    //html中需要添加<li><div class="product_tab_content" id="specParamContainer"></div><li>
                    
                    //产品库存
                    $("#product_num").attr("data-stock",result.data.stock);//??????
                    $("#stock_container").html("库存："+result.data.stock);
                    
                    //商品子图
                    //获取整个图片的数据 
                    var small_item ="<li><img src=\'"+baseUrl+result.data.main_image+"\'></li>";
                    var subimages=result.data.sub_images;
                    if(subimages!=null){
                    	subimages=subimages.substring(0,subimages.length);
	                    //切割
	                    var images=subimages.split(",");
	                    for(var i=0;i<images.length;i++){
	                        small_item+="<li><img src=\'";
	                        images[i]=baseUrl+images[i];
	                        small_item+=images[i];
	                        small_item+="\'></li>";
	                    }
                    }
                    //alert(small_item);
                    //将子图插入页面
                    $("#piclist_container").html();
                    $("#piclist_container").append(small_item);
                    //html中需添加子图

                }else{
                    //数据加载失败
                }
            }
        });
    }

    //需在html中做修改
    //购买数量增加
    $(".product_plus_1").click(function(){
        //获取库存
        var stock=$("#product_num").attr("data-stock");//??????
        var num=$("#product_num").val();
        //点击增加
        num=parseInt(num)+1;
        if(num>=stock){
            num=stock;
        }
        $("#product_num").val(num);
    });

    //购买数量减少
    $(".product_minus_2").click(function(){
        //获取库存
        var stock=$("#product_num").attr("data-stock");
        var num=$("#product_num").val();
        //点击减少
        num=parseInt(num)-1;
        if(num<=0){
            num=0;
        }
        $("#product_num").val(num);
    });

    //加入购物车
    $("#product_cart").click(function(){
        //验证数量是否符规范
        var count = $("#product_num").val();
        if(count<=0){
            alert("请填写正确数量，且不能小于1");
            return;
        }
        //请求服务器，加入购物车
        $.ajax({
            url:baseUrl+"cart/savecart.do",
            xhrFields:{withCredentials:true},
            crossDomain:true,
            data:{'productId':pid,'count':count},
            type:"post",
            success:function(rs){
                //判断方法是否成功
                if(rs.status==0){
                    //弹出提示消息
                    alert(rs.msg);
                }else{
                    alert(rs.msg);
                }
            }
        });
    });
    //直接购买
    $(".product_buy").click(function(){
        //验证数量是否符规范
        var count = $("#product_num").val();
        if(count<=0){
            alert("请填写正确数量，且不能小于1");
            return;
        }
        //请求服务器，加入购物车
        $.ajax({
            url:baseUrl+"cart/clearchecked.do",
            xhrFields:{withCredentials:true},
            crossDomain:true,
            type:"post",
            success:function(rs){
                //判断方法是否成功
                if(rs.status==0){
                    //弹出提示消息
                  //  alert(rs.msg);
                }else{
                    alert(rs.msg);
                }
            }
        });
        $.ajax({
            url:baseUrl+"cart/savecart.do",
            xhrFields:{withCredentials:true},
            crossDomain:true,
            data:{'productId':pid,'count':count},
            type:"post",
            success:function(rs){
                //判断方法是否成功
                if(rs.status==0){
                    //弹出提示消息
                   // alert(rs.msg);
                }else{
                    alert(rs.msg);
                }
            }
        });
        $(window).attr("location","order_confirm.html");
    });

    //商品小图切换成大图,html需做修改
    $(".product_picture_table_main ul li img").live("click",function(){
        //去除其他图片的选中样式
        $(".product_picture_table_main ul li img").removeClass("product_picture_selected");//可以到CSS中查看？？？
        $(this).addClass("product_picture_selected");
        //将小图放置到主图位置
        var imgSrc=$(this).attr("src");
        $(".product_picture_img").attr("src",imgSrc);
    });

    //Table页的切换
    $(".product_tab_bar li").click(function(){
        $(".product_tab_bar li").removeClass("product_tab_selected");
        $(this).addClass("product_tab_selected");
        
        //获取点击tab页的index
        var index=$(this).attr("data-index");
        $(".product_tab_detail li").css("display","none");
        $(".product_tab_detail").find("li").eq(index).css("display","block")

    });

    //搜索商品
    $("#searchBtn").click(function(){
        var proName=$("#keyword").val();
        $(window).attr("location","product_search.html?name="+proName);
    });
//添加到我的收藏
    $(".myfavorite").click(function(){
        //验证数量是否符规范
        //请求服务器，加入购物车
    	var img = document.getElementById('favorites');
    	if(!iscollected){
    		$.ajax({
    			url:baseUrl+"collect/addcollect.do",
    			xhrFields:{withCredentials:true},
    			crossDomain:true,
    			data:{"productId":pid},
    			type:"post",
    			success:function(rs){
    				alert("收藏成功");
    				img.src='../src/common/favorites2.jpg';
    				iscollected=true;
    			}
    			});
    	}else{
    		$.ajax({
    			url:baseUrl+"collect/deletecollect.do",
    			xhrFields:{withCredentials:true},
    			crossDomain:true,
    			data:{"productId":pid},
    			type:"post",
    			success:function(rs){
    				alert("取消收藏成功");
    				img.src='../src/common/favorites.jpg';
    				iscollected=false;
    			}
    			});
    	}
    });
    return{
        ready:ready
    };
});