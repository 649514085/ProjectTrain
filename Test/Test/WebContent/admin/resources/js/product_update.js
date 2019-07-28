d = "";
define(['jquery', 'WebUploader','common','/Test/admin/resources/js/handlebars-v4.1.2.js'],function(jquery, WebUploader, common,Handlebars){
	window.onerror = function (message, url, line, column, error) {
		  console.log('log---onerror::::',message, url, line, column, error);
		}
	var product_id = getParam("product_id");
	var image = "";
	var $ = jQuery,
    $list = $('#fileList'),
    // 优化retina, 在retina下这个值是2
    ratio = window.devicePixelRatio || 1,

    // 缩略图大小
    thumbnailWidth = 100 * ratio,
    thumbnailHeight = 100 * ratio,

    // Web Uploader实例
    uploader;

	// 初始化Web Uploader
	uploader = WebUploader.create({
	
	    // 自动上传。
	    auto: true,
	
	    // swf文件路径
	    swf: baseUrl + '/admin/resources/webuploader/Uploader.swf',
	
	    // 文件接收服务端。
	    server: 'http://localhost:8080/mall/image/uploadimage.do',
	
	    // 选择文件的按钮。可选。
	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	    pick: '#filePicker',
	
	    // 只允许选择文件，可选。
	    accept: {
	        title: 'Images',
	        extensions: 'gif,jpg,jpeg,bmp,png',
	        mimeTypes: 'image/*'
	    }
	});
	
	// 当有文件添加进来的时候
	uploader.on( 'fileQueued', function( file ) {
	    var $li = $(
	            '<div id="' + file.id + '" class="file-item thumbnail">' +
	                '<img>' +
	                '<div class="info" style="text-align:center;">' + file.name + '</div>' +
	            '</div>'
	            ),
	        $img = $li.find('img');
	
	    $list.append( $li );
	
	    // 创建缩略图
	    uploader.makeThumb( file, function( error, src ) {
	        if ( error ) {
	            $img.replaceWith('<span>不能预览</span>');
	            return;
	        }
	
	        $img.attr( 'src', src );
	    }, thumbnailWidth, thumbnailHeight );
	});
	
	// 文件上传过程中创建进度条实时显示。
	uploader.on( 'uploadProgress', function( file, percentage ) {
	    var $li = $( '#'+file.id ),
	        $percent = $li.find('.progress span');
	
	    // 避免重复创建
	    if ( !$percent.length ) {
	        $percent = $('<p class="progress"><span></span></p>')
	                .appendTo( $li )
	                .find('span');
	    }
	
	    $percent.css( 'width', percentage * 100 + '%' );
	});
	
	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader.on( 'uploadSuccess', function( file, rs) {
	    $( '#'+file.id ).addClass('upload-state-done');
	    alert(rs.data);
	    if(image==""){
	    	image+=rs.data;
	    }else{
	    	image+=","+rs.data;
	    }
	});
	
	// 文件上传失败，现实上传出错。
	uploader.on( 'uploadError', function( file ) {
	    var $li = $( '#'+file.id ),
	        $error = $li.find('div.error');
	
	    // 避免重复创建
	    if ( !$error.length ) {
	        $error = $('<div class="error"></div>').appendTo( $li );
	    }
	
	    $error.text('上传失败');
	});
	
	// 完成上传完了，成功或者失败，先删除进度条。
	uploader.on( 'uploadComplete', function( file ) {
	    $( '#'+file.id ).find('.progress').remove();
	});
	
	//alert(product_id);
	//获取商品的类型
	function getType(){
		$.ajax({
			"xhrFields":{withCredentials:true},
			"crossDomain":true,
			"url":baseUrl+"mgr/type/findtypebyrank.do",
			data:{
				"rank":3
			},
			dataType:"json",
			success:function(rs){
				//alert("rs"+rs);
				if(rs.status==0){
					var tpl = $("#param_item_tpl").html();
					console.log( tpl);
					var func = Handlebars.compile(tpl);
					var data = rs.data;
					var result = func(data);
					$("#product_type").html(result);
					$("#product_type").prepend("<option selected value='-1'>请选择产品类型</option>");
					loadProductInfo(product_id);
					if(location.href.indexOf('#reloaded')==-1){
						location.href=location.href+"#reloaded";
						location.reload();
						}
					
				}
				if(rs.status==1){
					alert(rs.msg);
				}
			}
		});
		CKEDITOR.instances.detail.setData(d);
	}
	
	function updateProductInfo(){
		$("#save").click(function(){
			var product_id = $("#product_id").val();
			var product_name = $("#product_name").val();
			var product_type = $("#product_type").val();
			var product_price = $("#product_price").val();
			var stock = $("#stock").val();
			
			var spec_param = $("#spec_param").val();
			var detail = CKEDITOR.instances.detail.getData();
			var spec_param_real = null;
			var detail_real = null;
			
			
			if(product_name=="" || product_type==-1 || product_price=="" || stock==""){
				alert("信息填写不完整！");
			}
			else if(spec_param!="" || detail!=""){
				spec_param_real = spec_param;
				detail_real = detail;
				
				$.ajax({
					"xhrFields":{withCredentials:true},
					"crossDomain":true,
					"url":baseUrl+"mgr/product/updateproduct.do",
					type:"post",
					data:{
						"product_id":product_id,
						"product_name":product_name,
						"type_id":product_type,
						"price":product_price,
						"stock":stock,
						"spec_param":spec_param_real,
						"detail":detail_real,
						"url":image
					},
					dataType:"json",
					success:function(rs){
						if(rs.status==0){
							alert("修改成功！");
							$(window).attr("location","product-manager.html");
						}
						if(rs.status==1){
							alert(rs.msg);
						}
					}
				});
			}	
		});
	}
	
	
	return {
		getType:getType,
		updateProductInfo:updateProductInfo
	};
});


//获取url中的参数
function getParam(name){
	var reg = new RegExp("(^|&)"+name+"=([^&]*)(&|$)");//构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg);
	if (r != null){
		return decodeURI(r[2]);
	}	
	else
		return null;
}

//获得商品信息
function loadProductInfo(product_id){
	$.ajax({
		"xhrFields":{withCredentials:true},
		"crossDomain":true,
		"url":baseUrl+"mgr/product/findproductbyid.do",
		data:{
			"product_id":product_id
		},
		type:"get",
		dataType:"json",
		success:function(rs){
			//alert(rs.data);
			if(rs.status==0){
				$("#product_id").val(rs.data[0].product_id);
				//alert(rs.data.product_id);
				$("#product_name").val(rs.data[0].product_name);
				$("#product_type").val(rs.data[0].type_id);
				$("#product_price").val(rs.data[0].price);
				$("#stock").val(rs.data[0].stock);
				$("#spec_param").val(rs.data[0].spec_param);
				d = rs.data[0].detail;
				CKEDITOR.instances.detail.setData(rs.data[0].detail);
				$("#image").val(rs.data[0].url);
				
			}
			if(rs.status==1){
				alert(rs.msg);
			}
		}
	});
}

