package com.it.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 
 * @ClassName: SendEmailUtil  
 * @Description: TODO  发送邮件类
 * @author Administrator  
 * @date 2020年8月27日  
 *
 */
public class SendEmailUtil {

	/**
	 * 
	 * @Title: sendEmail  
	 * @Description: TODO  发送邮件
	 * @param @param username 用户名
	 * @param @param password 密码，需要输入授权码
	 * @param @param senderEmail 发件人
	 * @param @param recipient 收件人
	 * @param @param smtpAddress SMTP地址
	 * @param @param smtpPort SMTP端口
	 * @param @param mailTittle 邮件标题
	 * @param @param mailText 邮件内容
	 * @param @throws AddressException
	 * @param @throws MessagingException    参数  
	 * @return void    返回类型  
	 * @throws
	 */
	public static void sendEmail(String username, String password, String senderEmail, String recipient, String smtpAddress, int smtpPort, String mailTittle, String mailText) throws AddressException, MessagingException {
		Properties properties = new Properties();
		properties.put("mail.transport.protocol", "smtp");// 连接协议
		properties.put("mail.smtp.host", smtpAddress);// SMTP地址
		properties.put("mail.smtp.port", smtpPort);// 端口号
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.ssl.enable", "true");// 设置是否使用ssl安全连接 ---一般都使用
//		properties.put("mail.debug", "true");// 设置是否显示debug信息 true 会在控制台显示相关信息
		
		// 得到会话对象
		Session session = Session.getInstance(properties);
		// 2、通过session得到transport对象
		Transport ts = session.getTransport();
		// 3、使用邮箱的用户名和密码连上邮件服务器
		ts.connect(smtpAddress, username, password);
		// 4、创建邮件
		Message message = createSimpleMail(session, senderEmail, recipient, mailTittle, mailText);
		// 5、发送邮件
		ts.sendMessage(message, message.getAllRecipients());
		ts.close();
	}
	
	/**
	 * 
	 * @Title: createSimpleMail  
	 * @Description: TODO  创建一封只包含文本的邮件
	 * @param @param session
	 * @param @param mailfrom
	 * @param @param mailTo
	 * @param @param mailTittle
	 * @param @param mailText
	 * @param @return
	 * @param @throws AddressException
	 * @param @throws MessagingException    参数  
	 * @return MimeMessage    返回类型  
	 * @throws
	 */
	public static MimeMessage createSimpleMail(Session session, String mailfrom, String mailTo, String mailTittle,
			String mailText) throws AddressException, MessagingException {
		// 创建邮件对象
		MimeMessage message = new MimeMessage(session);
		// 指明邮件的发件人
		message.setFrom(new InternetAddress(mailfrom));
		// 指明邮件的收件人
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
		// 邮件的标题
		message.setSubject(mailTittle);
		// 邮件的文本内容
		message.setContent(mailText, "text/html;charset=UTF-8");
		return message;
	}
}
