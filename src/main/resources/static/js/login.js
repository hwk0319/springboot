$(function() {
	document.onkeydown = function(e){ //添加回车登录事件
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	var name = $("input[name='username']").val();
	    	var pwd = $("input[name='pwd']").val();
	    	if (name == "" || name == null) {
	    		layer.tips('请输入用户名', '#username');
	    		return;
	    	}
	    	if (pwd == "" || pwd == null) {
	    		layer.tips('请输入密码', '#pwd');
	    		return;
	    	}
	    	$("#loginBtn").button('loading');
	    	login();
	     }
	};
});

//获得用户角色
function login() {
	var name = $("input[name='username']").val();
	var pwd = $("input[name='pwd']").val();
	var md5pwd = $.md5(pwd);

	$.ajax({
		url : "login/login",
		type : "post",
		dataType : "json",
		data : {
			"name" : name,
			"pwd" : md5pwd
		},
		success : function(result) {
			var status = result.status;
			var msg = result.msg;
			if(status == "success"){
				top.location.href = "login/index";
			}else{
				$("#msg").html(msg);
				$("#loginBtn").button('reset');
			}
		},
		error : function() {
			layer.alert("登录失败");
			$("#loginBtn").button('reset');
		}
	});
}