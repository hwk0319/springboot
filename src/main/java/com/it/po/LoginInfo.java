package com.it.po;

/**
 * 
 * @ClassName: LoginInfo  
 * @Description: TODO  登录信息
 * @author Administrator  
 * @date 2019年8月25日  
 *
 */
public class LoginInfo extends PagePo{
	private Integer id;
	private String userName;
	private String ip;
	private String location;
	private String browser;
	private String operatingSystem;
	private String status;
	private String message;
	private String createdate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public String getOperatingSystem() {
		return operatingSystem;
	}
	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return "LoginInfo [id=" + id + ", userName=" + userName + ", ip=" + ip + ", location=" + location + ", browser="
				+ browser + ", operatingSystem=" + operatingSystem + ", status=" + status + ", message=" + message
				+ ", createdate=" + createdate + "]";
	}
	
}
