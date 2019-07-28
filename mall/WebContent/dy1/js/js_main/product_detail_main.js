require.config({
	paths:{
		"jquery":"/mall/dy1/js/js_main/jquery-1.7.2.min",
		"handlebar":"/mall/dy1/js/js_main/handlerbars-v4.1.2",
		}
});

require(['jquery','handlebar','common','product_detail'],function(jquery,handlebar,common,product_detail){
    $(function(){
        
        common.getUserInfo();
       
        common.loginOut();
        product_detail.ready();
    });
});