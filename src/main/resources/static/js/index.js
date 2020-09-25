//页面加载时调用
$(function() {
//	loadMenu();
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

//加载菜单
function loadMenu() {
	$.ajax({
	    url : "menu/menu",
		type : "post",
		dataType : "json",
		async : false,
		success : function(data) {
			var menuHtml = "";
			//遍历一级菜单
			for ( var j in data) {
				var childern = data[j].childern;
				var name = data[j].name;
				var classStr = childern.length != 0 ? "class='menu-list'" : "";
				if(childern.length == 0){
					menuHtml += "<li "+classStr+"><a href='"+data[j].url+"'><i class='"+ data[j].image + "'></i><span> "+name+"</span></a>";
				}else{
					menuHtml += "<li "+classStr+"><a href='javascript:void(0)' onClick='parentClick(this)'><i class='"+ data[j].image + "'></i><span> "+name+"</span></a>";
				}
				//遍历二级菜单
				if(childern.length != 0){
					menuHtml += "<ul class='sub-menu-list'>";
					for ( var i in childern) {
						menuHtml += "<li><a href='javascript:void(0)' onclick=\"onLoad('"+childern[i].url+"', this)\"> "+childern[i].name+"</a></li>";
					}
			        menuHtml += "</ul>";
				}
		        menuHtml += "</li>";
			}
			$("#left-menu").html(menuHtml);
		}
	});
}

function onClickMenu(obj){
	//加载当前在哪一个菜单，并显示
	var titleName = obj.textContent;
	$("#titleName").html(titleName);
}


//窗口改变大小时重新设置div的宽高
//$(window).resize(function(){
//	$('#menu_toggle').css({
//		"width":"calc(100% - 200px)",
//		"height":"calc(100% - 50px)"
//	})
//});

//注销操作
function logOut(){
//	layer.confirm('确定要注销吗？', {
//		btn: ['确定','取消'] //按钮
//	}, function(){
		$.ajax({
			url:"login/logout",
			type:"post",
//			async:false,
			success : function(result){
//				if (result[0] == "success") {
					window.location.href = "login";
//				}
			},
			error : function(){
				layer.msg("操作失败！");
			}
		});
//	}, function(){
//		return;
//	});
}

//登录超时
function timeOut(){
	window.location.href = getContextPath();
}