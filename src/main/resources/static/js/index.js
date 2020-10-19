//页面加载时调用
$(function() {
//	$("#left-menu a").click(function(){
//		var e = window.event;
//		//获取事件点击元素
//		var targ = e.target;
//		//获取元素名称
//		var tname = targ.tagName;
//		var tname = targ.innerText;
//		console.log(tname);
//	})
});


function onClickMenu(obj){
	//加载当前在哪一个菜单，并显示
	var titleName = obj.textContent;
	$("#titleName").html(titleName);
}

//注销操作
function logOut(){
	$.ajax({
		url:"login/logout",
		type:"post",
		success : function(result){
			if (result == "success") {
				window.location.href = "login";
			}
		},
		error : function(){
			layer.msg("请求失败！");
		}
	});
}

//修改密码
function updatePwd(){
	var url = "common/loadPage?path=system/user/userModifyPass";
	openModel("#updatePwd", url);
}

//全屏事件
var exitFullscreen = false;
function handleFullScreen() {
	var element = document.documentElement;
	if(exitFullscreen) {
		if(document.exitFullscreen) {
			document.exitFullscreen();
		} else if(document.webkitCancelFullScreen) {
			document.webkitCancelFullScreen();
		} else if(document.mozCancelFullScreen) {
			document.mozCancelFullScreen();
		} else if(document.msExitFullscreen) {
			document.msExitFullscreen();
		}
		$("#handleFullScreen").html("<i title='全屏显示' class='fa icon-size-fullscreen'></i>");
		exitFullscreen = false;
	} else {
		if(element.requestFullscreen) {
			element.requestFullscreen();
		} else if(element.webkitRequestFullScreen) {
			element.webkitRequestFullScreen();
		} else if(element.mozRequestFullScreen) {
			element.mozRequestFullScreen();
		} else if(element.msRequestFullscreen) {
			// IE11
			element.msRequestFullscreen();
		}
		$("#handleFullScreen").html("<i title='退出全屏' class='fa icon-size-actual'></i>");
		exitFullscreen = true;
	}
}