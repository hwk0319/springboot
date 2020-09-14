package com.it.po;

import java.util.Date;

/**
 * 
 * @ClassName: OperationLogs  
 * @Description: TODO  操作日志
 * @author Administrator  
 * @date 2019年7月25日  
 *
 */
public class OperationLogs extends PagePo{
	  //主键
	  private Integer Id;
	  //用户名
	  private String userName;
	  //访问的IP
	  private String ip;
	  //访问接口名称
	  private String methodName;
	  //访问的类名称
	  private String className;
	  //访问时间
	  private String createDate;
	  //主机名
	  private String remoteHost;
	  //访问的URL
	  private String url;
	  //操作
	  private String operation;
	  //请求方法
	  private String requestMethod;
	  
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getRemoteHost() {
		return remoteHost;
	}
	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
      
}
