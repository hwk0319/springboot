/**
 * ajax 请求错误
 */
//$(document).ajaxError(function(e,xhr,opt){
//});

/**
 * 获取应用路路径，后面不带/
 * @returns
 */ 
function getContextPath() {
	    var pathName = document.location.pathname;
	    var index = pathName.substr(1).indexOf("/");
	    var result = pathName.substr(0,index+1);
	    return result;
}

/**
 * 获取页面参数
 */
(function ($) {
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return decodeURI(r[2]); return null;
    }
})(jQuery);


/**
 * 根据id异步删除数据
 * @param id
 * @param url
 * @param async
 * @returns
 */
function deleteData(id, url, async) {
	layer.confirm("您确定要删除这条数据吗？", {icon: 3, title:'提示'}, function(index){
		$.ajax({
			url : url,
			type : "post",
			async : true,
			data : {
				"id" : id
			},
			success : function(result) {
				layer.msg("删除成功！");
				refresh();
			},
			error : function() {
				layer.msg("删除失败！");
			}
		});
		layer.close(index);
	});
}

//获取URL地址参数
function getQueryString(name, url) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    if (!url || url == ""){
	    url = window.location.search;
    }else{	
    	url = url.substring(url.indexOf("?"));
    }
    r = url.substr(1).match(reg)
    if (r != null) return unescape(r[2]); return null;
}

/**
 * 根据字典类型获取字典数据并绑定到select上
 * @param type 字符串
 * @param selectId 字符串
 * @returns
 */
function getDictByType(type, selectId){
	$.ajax({
		url:"/dict/searchDictByType",
		type:"post",
		data:{
			"type" : type
		},
		datatype:"json",
		async:false,
		success:function (data){
			for (var i in data) {
				$(selectId).append("<option value='"+data[i].value+"'>"+data[i].name+"</option>");
			}
		}
	});
}

//获取随机数
function echartnum(){
	var seed = new Date().getTime();
	seed = (seed*9301+49297) % 233280;
	return Math.ceil(seed/233280.0 * 10000000000000000) * 0.0000000000000001;
}
//点击向上滑动隐藏
function slideToggle(id){
	$(id).slideToggle();
}
//重置
function repeat(){
	$(":text").val("");
}
/**
 * 显示系统提示，需要引入jquery.toast.min.js,jquery.toast.min.css
 * @param type 'info','success','warning','error',
 * @param msg
 * @returns
 * showSystemMsg('info', '刷新成功！');
 */
function showSystemMsg(type, msg){
    $.toast().reset('all'); 
	$.toast({
		heading: '系统提示', // Optional heading to be shown on the toast
		icon: type, // Type of toast icon
		text: msg, // Text that is to be shown in the toast
		showHideTransition: 'fade', // fade, slide or plain
		allowToastClose: true, // Boolean value true or false
		hideAfter: 2000, // false to make it sticky or number representing the miliseconds as time after which toast needs to be hidden
		stack: 5, // false if there should be only one toast at a time or a number representing the maximum number of toasts to be shown at a time
		position: 'top-center', // bottom-left or bottom-right or bottom-center or top-left or top-right or top-center or mid-center or an object representing the left, right, top, bottom values
		textAlign: 'left',  // Text alignment i.e. left, right or center
		loader: false,  // Whether to show loader or not. True by default
//		beforeShow: function () {}, // will be triggered before the toast is shown
//		afterShown: function () {}, // will be triggered after the toat has been shown
//		beforeHide: function () {}, // will be triggered before the toast gets hidden
//		afterHidden: function () {} 
    });
	return false;
}

/**
 * 打开模态框，加载自定义html页面
 * @param selector
 * @param url
 * @returns
 */
function openModel(selector, url) {
    $(selector).modal({
        backdrop: 'static',     // 点击空白不关闭
        keyboard: false,        // 按键盘esc也不会关闭
        remote: url				// 从远程加载html内容的地址
    });
}

//全局配置
/*$.ajaxSetup({
	  layerIndex:-1, //保存当前请求对应的提示框index,用于后面关闭使用
	  //在请求显示提示框
	  beforeSend: function(jqXHR, settings) {
	      this.layerIndex = layer.load(1, {
	    	  shade: [0.3,'#000']
	  		});
	  },
	  //请求完毕后（不管成功还是失败），关闭提示框
	  complete: function () {
	      layer.close(this.layerIndex);
	  },
	  //请求失败时，弹出错误信息
	  error: function (jqXHR, status, e) {
	    layer.alert('数据请求失败，请后再试!');
	  }
});*/