$(function() {
	document.onkeydown = function(e){ //添加回车登录事件
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	var name = $("input[name='username']").val();
	    	var pwd = $("input[name='pwd']").val();
	    	var identifyCode =$("#verification").val();
	    	if (name == "" || name == null) {
	    		layer.tips('请输入用户名', '#username');
	    		return;
	    	}
	    	if (pwd == "" || pwd == null) {
	    		layer.tips('请输入密码', '#pwd');
	    		return;
	    	}
	    	if (identifyCode == "" || identifyCode == null) {
				layer.tips('请输入验证码', '#verification');
				return;
			}
	    	$("#loginBtn").button('loading');
	    	login();
	     }
	};
});

//登录
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

//验证码触发事件
function checkCode(){
	var code = $("#verification").val();
	if(code.length == 4){
		verfify(code);
	}
}
//校验验证码
function verfify(code){
	$.ajax({
		url : "login/verfify",
		type : "post",
		data : {code:code},
		success : function(result){
			if(result != "success"){
				layer.tips('验证码输入错误', '#verification');
				$("#verification").val("");
				$("#vertity_img").click();
			}
		}
	});
}