package com.it.util;

/**
 * 
 * @ClassName: GenericException  
 * @Description: TODO  自定义异常
 * @author Administrator  
 * @date 2019年11月7日  
 *
 */
public class GenericException extends RuntimeException{

	private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public GenericException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public GenericException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	public GenericException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}

	public GenericException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
