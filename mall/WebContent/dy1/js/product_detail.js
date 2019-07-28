	function change_big_small_pic1(){
	var bigimgObj=document.getElementById("big_pic");
	var imgObj1 =document.getElementById("small_01");
	var imgObj2 =document.getElementById("small_02");
	if(imgObj1.getAttribute("src",1)=="../src/details/small_pic_01.jpg")
	{
		big_pic.src="../src/details/small_pic_01.jpg";
		$('#small_01').addClass('product_picture_selected');
		$('#small_02').removeClass('product_picture_selected');
		$('#small_03').removeClass('product_picture_selected');
		$('#small_04').removeClass('product_picture_selected');
		$('#small_05').removeClass('product_picture_selected');

	}
}


function change_big_small_pic2(){
	var bigimgObj=document.getElementById("big_pic");
	var imgObj2 =document.getElementById("small_02");
	if(imgObj2.getAttribute("src",2)=="../src/details/small_pic_02.jpg")
	{
		big_pic.src="../src/details/small_pic_02.jpg";
		$('#small_01').removeClass('product_picture_selected');
		$('#small_02').addClass('product_picture_selected');
		$('#small_03').removeClass('product_picture_selected');
		$('#small_04').removeClass('product_picture_selected');
		$('#small_05').removeClass('product_picture_selected');

	}
}

function change_big_small_pic3(){
	var bigimgObj=document.getElementById("big_pic");
	var imgObj3 =document.getElementById("small_03");
	if(imgObj3.getAttribute("src",3)=="../src/details/small_pic_03.jpg")
	{
		big_pic.src="../src/details/small_pic_03.jpg";
		$('#small_01').removeClass('product_picture_selected');
		$('#small_02').removeClass('product_picture_selected');
		$('#small_03').addClass('product_picture_selected');
		$('#small_04').removeClass('product_picture_selected');
		$('#small_05').removeClass('product_picture_selected');


	}
}
function change_big_small_pic4(){
	var bigimgObj=document.getElementById("big_pic");
	var imgObj4 =document.getElementById("small_04");
	if(imgObj4.getAttribute("src",4)=="../src/details/small_pic_04.jpg")
	{
		big_pic.src="../src/details/small_pic_04.jpg";
		$('#small_01').removeClass('product_picture_selected');
		$('#small_02').removeClass('product_picture_selected');
		$('#small_03').removeClass('product_picture_selected');
		$('#small_04').addClass('product_picture_selected');
		$('#small_05').removeClass('product_picture_selected');
	}
}
function change_big_small_pic5(){
	var bigimgObj=document.getElementById("big_pic");
	var imgObj5 =document.getElementById("small_05");
	if(imgObj5.getAttribute("src",5)=="../src/details/small_pic_05.jpg")
	{
		big_pic.src="../src/details/small_pic_05.jpg";
		$('#small_01').removeClass('product_picture_selected');
		$('#small_02').removeClass('product_picture_selected');
		$('#small_03').removeClass('product_picture_selected');
		$('#small_04').removeClass('product_picture_selected');
		$('#small_05').addClass('product_picture_selected');
	}
}



//图片——收藏图片切换
function change_pic(){
	var imgObj = document.getElementById("favorites");

	if(imgObj.getAttribute("src",0)=="../src/common/favorites.jpg"){
		imgObj.src="../src/common/favorites2.jpg";
	}else{
		imgObj.src="../src/common/favorites.jpg";
	}
}

//图片——大小图的显示
	
	





//数量-+1

(function($){ 
 
	 function getRandom(n){
        return Math.floor(Math.random()*n+1)
     }
 



	var inputp={
		indexInput:0,
		
		addNew:function(obj,stepNum){
			this.initNew(obj,stepNum);
			this.indexInput++;
		},
		getDigit:function(val,num){
			var digitNum=0;
			if(num.toString().split(".")[1]){
				digitNum=num.toString().split(".")[1].length;
			}
			 
			if(digitNum>0){
		 		val=val.toFixed(digitNum);
			}
			return val;
			
		},
		initNew:function(obj,stepNum){
			
			
			
			
			var width=obj.width();
			var height=obj.height();
			var height1=height;
		 	 
			var _root = this;
		 	width+=3;
			//height+=0; 
			 
			obj.css("border-style","none");
			obj.css("border-width","0px");
		   
			obj.css("width",width-height1*2-7);
			obj.css("text-align","center");
			obj.css("outline","none");
			obj.css("vertical-align","middle");
			obj.css("line-height",height+"px");
			
			
			obj.wrap("<div id='"+obj.attr('id')+"T' style='overflow:hidden;width:"+width+"px;height:"+height+"px;border: 1px solid #CCC;'></div>");
			
			obj.before("<div id='"+obj.attr('id')+"l'  onselectstart='return false;' style='-moz-user-select:none;cursor:pointer;text-align:center;width:"+height1+"px;height:"+height1+"px;line-height:"+height1+"px;border-right-width: 1px;border-right-style: solid;border-right-color: #CCC;float:left'>-</div>");
			obj.after("<div id='"+obj.attr('id')+"r'  onselectstart='return false;' style='-moz-user-select:none;cursor:pointer;text-align:center;width:"+height1+"px;height:"+height1+"px;line-height:"+height1+"px;border-left-width: 1px;border-left-style: solid;border-left-color: #CCC;float:right'>+</div>");
			$("#"+obj.attr('id')+"l").click(function(){
				
				_root.leftDo(obj,stepNum);
			});
			$("#"+obj.attr('id')+"r").click(function(){
				_root.rightDos(obj,stepNum);
			});
			
		},
		leftDo:function(obj,stepNum){
			var val=this.formatNum(obj.val());
			val=Math.abs(val);
			val-=stepNum;
			
			val=this.getDigit(val,stepNum);
			 
			if(val<0){
				val=0;
				obj.val(0);
			}else{
				this.moveDo(obj,val,true,stepNum);
			};
			
			
		},
		rightDos:function(obj,stepNum){
			
			var val=this.formatNum(obj.val());
			val=Math.abs(val);
			val+=stepNum;
			val=this.getDigit(val,stepNum);
			
				
			this.moveDo(obj,val,false,stepNum);
			 
		},
		moveDo:function(obj,num,isup,stepNum){
			var startTop=0;
			var endTop=0;
			if(stepNum>=1){
				if(num.toString().split(".")[1]){
					 num=num.toString().split(".")[0];
					 obj.val(num);
				}
			}
			
			
			var num1=num;
			var lens1=num.toString().length;
			var divwidth=parseFloat(obj.css("font-size"))/2;
		 	if(isup){
				obj.val(num1);
				var isDecimal=false;
			 	for(i=lens1-1;i>=0;i--){
					var s=num.toString();
					var s1=s.substr(i,1);
					var s1num=parseFloat(s1);
					if(s1num!=9||isNaN(s1num)){
						if(isNaN(s1num)){
							//num=parseFloat(s.substr(i-1,lens1-i));
//							num++;
//							num=this.getDigit(num,stepNum);
						}else{
							num=parseFloat(s.substr(i,lens1-i));
							num++;
							break;
						}
						
					}
				}
				//num=this.getDigit(num,stepNum)
				startTop=0;
				endTop=-40;
			}else{
				var isDecimal=false;
			 	for(i=lens1-1;i>=0;i--){
					var s=num.toString();
					var s1=s.substr(i,1);
					var s1num=parseFloat(s1);
				 	if(s1num!=0||isNaN(s1num)){
						
						if(isNaN(s1num)){
							//num=parseFloat(s.substr(i-1,lens1-i));
//							num=this.getDigit(num,stepNum);
							isDecimal=true;
						}else{
							num=parseFloat(s.substr(i,lens1-i));
							break;
						}
					}
				}
				if(isDecimal){
					num=this.getDigit(num,stepNum);
				}
				startTop=40;
				endTop=0;
			}
		 
			
			if($("#"+obj.attr('id')+"Num").length <1){
				//background-color:#fff;
				var numstr=num.toString();
				var widths=divwidth*numstr.length;
				var stri="<div id='"+obj.attr('id')+"sy' style='position:relative;width:0px;height:0px'><div id='"+obj.attr('id')+"Num' style='width:"+widths+"px;z-index:100;position:absolute;height:"+obj.height()+"px;top:"+startTop+"px; line-height:"+obj.height()+"px;font-family: "+obj.css("font-family")+";font-size:"+obj.css("font-size")+";'>";
				for(i=0;i<numstr.length;i++){
					var nums=numstr.substr(i,1);
					if(nums=="."){
						stri+="<div style='float:left;width:"+divwidth+"px;'>&nbsp;";
					}else{
						stri+="<div style='float:left;width:"+divwidth+"px;background-color:#fff'>";
						stri+=nums;
					}
					stri+="</div>";
				}
				stri+="</div></div>";
				 
				$("#"+obj.attr('id')+"T").prepend(stri);
			 	var leftOff=0;
				if(num1.toString().length-num.toString().length>0){
					leftOff=(divwidth*(num1.toString().length-num.toString().length))/2;
				}
				var pz=0;
				if(/msie/.test(navigator.userAgent.toLowerCase())){
					pz=1; 
				}
     			if(/firefox/.test(navigator.userAgent.toLowerCase())){
					pz=1; 
				}
				if(/webkit/.test(navigator.userAgent.toLowerCase())){
					 
				}    
			 	if(/opera/.test(navigator.userAgent.toLowerCase())){
					pz=1;
				} 
				var leftpx=(obj.width()/2)+obj.height()-($("#"+obj.attr('id')+"Num").width()/2)+1+leftOff+pz;
			 	$("#"+obj.attr('id')+"Num").css("left",leftpx);
				$("#"+obj.attr('id')+"Num").animate({ 
					top:endTop+'px'
					//,opacity:0.4
				},  
				300,  
				function(){  
					$("#"+obj.attr('id')+"sy").remove();
					if(isup){
					
					}else{
						obj.val(num1);
					}
				});  
			}		
		}
		,
		
		formatNum:function(val){
			var val=parseFloat(val);
			if(isNaN(val)){ 
				val=0;
			}
			return val;	
		},
		
	};
	 
   
    $(function(){
	 	inputp.addNew($("#inputs"),0.1);
		inputp.addNew($("#inp"),1);
		})  
})(jQuery);  // JavaScript Document
// 把16进制颜色转换成rgb格式
 