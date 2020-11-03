package com.it.util;

/**
 * 
 * @ClassName: Result  
 * @Description: TODO  统一数据返回实体
 * @author Administrator  
 * @date 2020年3月29日  
 *
 */
public class Result {
	
	//是否成功
	private boolean success;
	//状态码
	private String code;
	//返回消息
	private String msg;
	//返回数据
	private Object data;
	//数据总记录数
	private Integer total;
	
	 /**
     * 自定义返回结果
     * 建议使用统一的返回结果，特殊情况可以使用此方法
     * @param success
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static Result custom(boolean success,String code,String msg,Object data){
    	Result Result = new Result();
        Result.setSuccess(success);
        Result.setCode(code);
        Result.setMsg(msg);
        Result.setData(data);
        return Result;
    }
 
    /**
     * 参数为空或者参数格式错误
     * @return
     */
    public static Result paramError(){
        Result Result = new Result();
        Result.setSuccess(false);
        Result.setCode(ReturnCode.PARAMS_ERROR.getCode());
        Result.setMsg(ReturnCode.PARAMS_ERROR.getMsg());
        return Result;
    }
 
    /**
     * 查询失败
     * @return
     */
    public static Result error(){
        Result Result = new Result();
        Result.setSuccess(false);
        Result.setCode(ReturnCode.FEAILED.getCode());
        Result.setMsg(ReturnCode.FEAILED.getMsg());
        Result.setData(null);
        return Result;
    }
 
    /**
             * 查询成功但无数据
     * @return
     */
    public static Result success(){
        Result Result  = new Result();
        Result.setSuccess(true);
        Result.setCode(ReturnCode.NODATA.getCode());
        Result.setMsg(ReturnCode.NODATA.getMsg());
        Result.setData(null);
        return Result;
    }
 
    /**
             * 返回成功且有数据
     * @param data
     * @return
     */
    public static Result success(Object data){
        Result Result = new Result();
        Result.setSuccess(true);
        Result.setCode(ReturnCode.SUCCESS.getCode());
        Result.setMsg(ReturnCode.SUCCESS.getMsg());
        Result.setData(data);
        return Result;
    }
    
    /**
     * 查询成功且有数据，并返回总记录数
     * @param data
     * @return
     */
    public static Result success(Object data, Integer total){
        Result Result = new Result();
        Result.setSuccess(true);
        Result.setCode(ReturnCode.SUCCESS.getCode());
        Result.setMsg(ReturnCode.SUCCESS.getMsg());
        Result.setData(data);
        Result.setTotal(total);
        return  Result;
    }
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
}
