package com.it.threadPool;

public class MyThread extends Thread{

	@Override
	public void run() {
		// TODO Auto-generated method stub
//		super.run();
		System.out.println(Thread.currentThread().getName()+"正在执行。。。");
	}
	
	public static void main(String[] args) {
		MyThread t = new MyThread();
		t.start();
	}
}
