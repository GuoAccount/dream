var DREAM={
	checkLogin : function(){
		var token = $.cookie("DREAM_TOKEN");
		if(!token){
			return ;
		}
		$.ajax({
			url : "http://localhost:8087/user/token/" + token,
			dataType : "jsonp",
			type : "GET",
			success : function(data){
				if(data.status == 200){
					var username = data.data.username;
					$("#loginbar").html("<a href='#' >"+username+"</a>,欢迎来到dream商城！");
					console.log("hahahhahahha");
				}
			}
		});
		console.log("未正确执行");
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	DREAM.checkLogin();
});