package com.it.util;

import net.sf.json.JSONNull;
import net.sf.json.processors.DefaultValueProcessor;

/**
 * 将对象转成json时空值处理
 * @author Administrator
 *
 */
public class JsonDefaultValueProcessor implements DefaultValueProcessor {
	public Object getDefaultValue(Class type) {  
        if (type != null && Integer.class.isAssignableFrom(type)) {  
            return "";  
        }  
        return JSONNull.getInstance();  
    }  
}
