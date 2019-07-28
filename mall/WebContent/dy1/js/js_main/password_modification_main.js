require.config({
    
});

require(['jquery-1.7.2.min','common','password_modification'],function(jquery,common,password_modification){
    $(function(){
       
        common.getUserInfo();
        
        common.loginOut();
        
        password_modification.ready();
    });
});