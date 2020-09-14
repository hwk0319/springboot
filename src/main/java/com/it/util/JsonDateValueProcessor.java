package com.it.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * 将对象转成json时对时间格式化
 * @author Administrator
 *
 */
public class JsonDateValueProcessor implements JsonValueProcessor {
    private String format ="yyyy-MM-dd HH:mm:ss";  
 
    public JsonDateValueProcessor() {  
        super();  
    }  
 
    public JsonDateValueProcessor(String format) {  
        super();  
        this.format = format;  
    }  
 
    public Object processArrayValue(Object paramObject, JsonConfig paramJsonConfig) {  
        return process(paramObject);  
    }  
 
    public Object processObjectValue(String paramString, Object paramObject,  JsonConfig paramJsonConfig) {  
        return process(paramObject);  
    }  
 
    private Object process(Object value){  
        if(value instanceof Date){    
            SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.CHINA);    
            return sdf.format(value);  
        }    
        return value == null ? "" : value.toString();    
    }  
}
