function validator(){
	var name = $("input[name='username']").val();
	var pwd = $("input[name='pwd']").val();
	var identifyCode =$("#verification").val();
	if (name == "" || name == null) {
		$("#usernameWarn").html("请输入用户名");
		return;
	}
	if (pwd == "" || pwd == null) {
		$("#pwdWarn").html("请输入密码");
		return;
	}
	if (identifyCode == "" || identifyCode == null) {
		$("#verificationWarn").html("请输入验证码");
		return;
	}else{
		var result = checkCode();
		if(!result){
			$("#verificationWarn").html("验证码输入错误");
			return;
		}
	}
    $(this).button('loading');
    login();
}

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
	var res;
	if(code.length == 4){
		$.ajax({
			url : "login/verfify",
			type : "post",
			async: false,
			data : {code:code},
			success : function(result){
				if(result != "success"){
					$("#verificationWarn").html("验证码输入错误");
					res = false;
				}else{
					$("#verificationWarn").html("");
					res = true;
				}
			}
		});
		return res;
	}
}