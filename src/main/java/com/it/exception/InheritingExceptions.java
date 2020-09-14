package com.it.exception;

public class InheritingExceptions {
	public void f() throws SimpleException{
		System.out.println("Throw SimpleExecption from f()");
		throw new SimpleException("SimpleException");
	}
	
	public static void main(String[] args) {
		InheritingExceptions sed = new InheritingExceptions();
		try {
			sed.f();
		} catch (SimpleException e) {
			// TODO: handle exception
			System.out.println("Caught it!");
			e.printStackTrace();
		}
	}
}
