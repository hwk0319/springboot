package com.it.util;

/**
 * 
 * @ClassName: ReturnCode  
 * @Description: TODO  接口返回码和返回值
 * 结合返回数据封装类Result，统一接口的数据返回格式
 * @author Administrator  
 * @date 2020年3月29日  
 *
 */
public enum ReturnCode {
	
	SUCCESS(200,"成功"),
    NODATA(201,"成功无记录"),
    FEAILED(202,"失败"),
    FORBIDDEN(403, "权限失败"),
    ACCOUNT_ERROR(1000, "账户不存在或被禁用"),
    API_NOT_EXISTS(1001, "请求的接口不存在"),
    API_NOT_PER(1002, "没有该接口的访问权限"),
    PARAMS_ERROR(1004, "参数为空或格式错误"),
    UNKNOWN_IP(1099, "非法IP请求"),
    SYSTEM_ERROR(9999, "系统异常");

	private int code;
    private String msg;
 
    public int getCode() {
        return code;
    }
 
    public String getMsg() {
        return msg;
    }
 
    ReturnCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
