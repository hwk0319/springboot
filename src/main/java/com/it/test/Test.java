package com.it.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Test implements Light{
	public static void main(String[] args) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("Tom", "CoreJava");
			map.put("John", "Oracle");
			map.put("Susan", "Oracle");
			map.put("Lucy", "JSP");
			
//			for (String m:map.keySet()) {
//				System.out.println(m);
//				System.out.println(map.get(m));
//			}
			
			for(String m:map.values()) {
				System.out.println(m);
			}
			
//			for(Map.Entry<String, String> m:map.entrySet()) {
//				System.out.println(m.getKey()+":"+m.getValue());
//			}
			
			Iterator<Map.Entry<String,String>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
				System.out.println(entry.getKey()+":"+entry.getValue());
			}
	}

	@Override
	public void shine() {
		// TODO Auto-generated method stub
		
	}
}
