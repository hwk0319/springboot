package com.it.webservice;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class Test {

	public String getValue() {
		return "hello";
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Endpoint.publish("http://localhost/service/test", new Test());
		System.out.println("service success");
	}

}
