package com.it.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.util.StringUtils;

import net.sf.json.JSONObject;


/**
 * 
 * @ClassName: Util  常用公共方法类
 * @Description: TODO  
 * @author Administrator  
 * @date 2019年11月2日  
 *
 */
public class Util {
	/**
	 * 特殊字符校验
	 * @param str
	 * @return true 含有特殊字符，false 不含有特殊字符
	 */
	public static final boolean validationStr(String str){
		if(str != null && !"".equals(str)){
			String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
			Pattern p=Pattern.compile(regEx);
			Matcher m=p.matcher(str);
			return m.find();
		}else{
			return false;
		}
	}

	
	/**
	 * 文件路径检查，防止用户通过../等方式取得其他文件
	 * @param filePath
	 * @return
	 */
	public static String filePathCheck(String filePath){
		while(filePath.contains("..")){
			filePath = filePath.replace("..", "");
		}
		return filePath;
	}
	
	/**  
     * 将容易引起xss漏洞的半角字符直接替换成全角字符 在保证不删除数据的情况下保存  
     * @return 过滤后的值  
     */  
    public static String xssEncode(String value) {  
        if(StringUtils.isEmpty(value)){
            return value;
        }else{
            if (value != null) {
                Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
                value = scriptPattern.matcher(value).replaceAll("");
                // 会误伤百度富文本编辑器
                // Avoid anything in a src="http://www.yihaomen.com/article/java/..." type of e­xpression
                scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
                value = scriptPattern.matcher(value).replaceAll("");
                scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
                value = scriptPattern.matcher(value).replaceAll("");
                // Remove any lonesome </script> tag
                scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
                value = scriptPattern.matcher(value).replaceAll("");
                // Remove any lonesome <script ...> tag
                scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
                value = scriptPattern.matcher(value).replaceAll("");
                // Avoid eval(...) e­xpressions
                scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
                value = scriptPattern.matcher(value).replaceAll("");
                // Avoid e­xpression(...) e­xpressions
                scriptPattern = Pattern.compile("e­xpression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
                value = scriptPattern.matcher(value).replaceAll("");
                // Avoid javascript:... e­xpressions
                scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
                value = scriptPattern.matcher(value).replaceAll("");
                // Avoid vbscript:... e­xpressions
                scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
                value = scriptPattern.matcher(value).replaceAll("");
                // Avoid onload= e­xpressions
                scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
                value = scriptPattern.matcher(value).replaceAll("");
                // Avoid document.cookie
                scriptPattern = Pattern.compile("document.cookie", Pattern.CASE_INSENSITIVE);
                value = scriptPattern.matcher(value).replaceAll("");
            }
            return value;
        } 
   }  
    
    
	/**
	 * 读取iscip.properties配置文件信息
	 * @param req
	 * @return
	 * @throws IOException
	 */
	public static Properties getProperties(String path) throws IOException {  
   	 Properties prop = new Properties();  
   	 try(InputStream in = new BufferedInputStream (new FileInputStream(path));  ) {
   		 prop.load(in);  
   	 } catch (IOException e) {  
   		 throw e;
   	 }  
   	 return prop;
	}
	/**
	 * 
	 * @Title: getMapFromJson  
	 * @Description: TODO  
	 * @param @param jsonString
	 * @param @return    参数  
	 * @return Map<String,String>    返回类型  
	 * @throws
	 */
	public static Map<String, String> getMapFromJson(JSONObject json) {
        JSONObject jsonObject = json;
        Map<String, String> map = new HashMap<String, String>();
        for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();) {
            String key = (String) iter.next();
            String value=jsonObject.getString(key);
            if(org.apache.commons.lang.StringUtils.isNotBlank(value)&&!"null".equalsIgnoreCase(value))
            {
                map.put(key, jsonObject.getString(key));
            }
        }
        return map;
    }
	
	/** 
      * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址, 
      * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？ 
      * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。 
      * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 
      * 用户真实IP为： 192.168.1.110 
      * @param request 
      * @return 
      */  
     public static String getIpAddress(HttpServletRequest request) {  
         String ip = request.getHeader("x-forwarded-for");  
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
             ip = request.getHeader("Proxy-Client-IP");  
         }  
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
             ip = request.getHeader("WL-Proxy-Client-IP");  
         }  
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
             ip = request.getHeader("HTTP_CLIENT_IP");  
         }  
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
             ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
         }  
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
             ip = request.getRemoteAddr();  
         }  
         return ip;  
     }  
     
     /**
      * 
      * @Title: getFormatDate  
      * @Description: TODO  获取当前时间并格式化
      * @param @return    参数  
      * @return String    返回类型  
      * @throws
      */
     public static String getFormatDate() {
    	 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	 return df.format(new Date());
     }
     
     /**
      * 
      * @Title: isAjaxRequest  
      * @Description: TODO  判断是否是ajax请求
      * @param @param request
      * @param @return    参数  
      * @return boolean    返回类型  
      * @throws
      */
     public static boolean isAjaxRequest(HttpServletRequest request) {
    	 String header = request.getHeader("X-Requested-With");
    	 if(header != null && header.equals("XMLHttpRequest")) {
    		 return true;
    	 }
    	 return false;
     }
     
     /**
      * 
      * @Title: outObject  
      * @Description: TODO  向页面输出内容
      * @param @param response
      * @param @param object    参数  
      * @return void    返回类型  
      * @throws
      */
     public static void outObject(HttpServletResponse response, String object) {
    	try {
    		 response.setCharacterEncoding("UTF-8");
    		 response.setContentType("application/json; charset=utf-8");
			 PrintWriter writer = response.getWriter();
			 writer.write(object);
			 writer.flush();
			 writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
     }
}
