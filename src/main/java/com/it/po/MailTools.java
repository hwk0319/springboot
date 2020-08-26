package com.it.po;

/**
 * 
 * @ClassName: MailTools  
 * @Description: TODO  邮件工具类
 * @author Administrator  
 * @date 2020年8月26日  
 *
 */
public class MailTools {
    private Integer id;

    private String senderEmail;//发件人邮箱

    private String senderUsername;//发件用户名

    private String emailPassword;//邮箱密码

    private String smtpAddress;//SMTP地址

    private Integer smtpPort;//SMTP端口

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getSenderUsername() {
		return senderUsername;
	}

	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
	}

	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	public String getSmtpAddress() {
		return smtpAddress;
	}

	public void setSmtpAddress(String smtpAddress) {
		this.smtpAddress = smtpAddress;
	}

	public Integer getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(Integer smtpPort) {
		this.smtpPort = smtpPort;
	}

	@Override
	public String toString() {
		return "MailTools [id=" + id + ", senderEmail=" + senderEmail + ", senderUsername=" + senderUsername
				+ ", emailPassword=" + emailPassword + ", smtpAddress=" + smtpAddress + ", smtpPort=" + smtpPort + "]";
	}
    
}