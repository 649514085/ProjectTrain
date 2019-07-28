require.config({       
    paths:{
        "jquery":"/mall/dy1/js/js_main/jquery-1.7.2.min",
    }
});

require(['jquery','common','pay'],function(jquery,common,pay){
    $(function () {
       pay.ready();
    });
});