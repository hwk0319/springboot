package com.it.exception;

/**
 * 
 * @ClassName: SimpleException  
 * @Description: TODO  自定义异常
 * @author Administrator  
 * @date 2020年6月18日  
 *
 */
public class SimpleException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public SimpleException(String message) {
		super(message);
	}

}
