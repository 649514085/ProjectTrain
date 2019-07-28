require.config({
    
});

require(['jquery-1.7.2.min','register'],function(jquery,register){
    $(function(){
        register.registBtn();
    });
});