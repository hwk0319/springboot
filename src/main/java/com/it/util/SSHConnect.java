package com.it.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import ch.ethz.ssh2.Connection;

/**
 * 
 * @ClassName: SSHConnection  
 * @Description: TODO  SSH工具类
 * @author Administrator  
 * @date 2020年9月29日  
 *
 */
public class SSHConnect {
	private static final Logger log = LoggerFactory.getLogger(SSHConnect.class);
	private static final int TIMEOUT = 5*60*1000;//超时时间5分钟
	private static Session session = null;
	private static ChannelExec channelExec = null;
	private static Connection connection = null;

	/**
	 * 
	 * @Title: getSSHConnection  
	 * @Description: TODO  通过ip，端口，用户，密码，SSH获取连接，需要手动关闭
	 * @param @param ip
	 * @param @param port
	 * @param @param accout
	 * @param @param password
	 * @param @return    参数  
	 * @throws
	 */
	public static Connection getSSHConnect(String ip, int port, String accout, String password) {
		connection = new Connection(ip, port);
		try {
			connection.connect();
			boolean result = connection.authenticateWithPassword(accout, password);
			if(result) {
				return connection;
			}else {
				log.error("SSH连接失败，请检查用户名、密码");
				return null;
			}
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @Title: closeSSHConnection  
	 * @Description: TODO  关闭SSHConnection
	 * @return void    返回类型  
	 * @throws
	 */
	public static void closeSSHConnect() {
		if(connection != null) {
			connection.close();
		}
	}
	
	/**
	 * 
	 * @Title: testSSHConnection  
	 * @Description: TODO  测试ssh连接
	 * @param @param ip
	 * @param @param port
	 * @param @param accout
	 * @param @param password
	 * @param @return    参数  
	 * @return boolean    返回类型  
	 * @throws
	 */
	public static boolean testSSHConnect(String ip, int port, String account, String password) {
		connection = getSSHConnect(ip, port, account, password);
		if(connection != null) {
			closeSSHConnect();
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 
	 * @Title: getSSHJSch  
	 * @Description: TODO  连接远程服务器, 通过ip，端口，用户，密码，需要手动关闭
	 * @param @param ip
	 * @param @param port
	 * @param @param accout
	 * @param @param password    参数  
	 * @return void    返回类型  
	 * @throws
	 */
	public static void SSHJSch(String ip, int port, String accout, String password) {
		JSch jsch = new JSch();
		try {
			session = jsch.getSession(accout, ip, port);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(password);
			session.setTimeout(TIMEOUT);
			session.connect();
		} catch (JSchException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @Title: runCmd  
	 * @Description: TODO  执行命令，需要先执行SSHJSch获取连接
	 * @param @param cmd
	 * @param @return    参数  
	 * @return String    返回类型  
	 * @throws
	 */
	public static String runCmd(String cmd) {
		String buf = null;
		StringBuffer bf = new StringBuffer();
		try {
			channelExec = (ChannelExec) session.openChannel("exec");
			channelExec.setCommand(cmd);
			channelExec.setInputStream(null);
			channelExec.setErrStream(System.err);
			channelExec.connect(TIMEOUT);
			InputStream is = channelExec.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			while((buf = br.readLine()) != null) {
				bf.append(buf).append(";");
			}
			br.close();
			channelExec.disconnect();
		} catch (JSchException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return bf.toString();
	}
	
	/**
	 * 
	 * @Title: closeSession  
	 * @Description: TODO  关闭session
	 * @param     参数  
	 * @return void    返回类型  
	 * @throws
	 */
	public static void closeSession() {
		if(session != null) {
			session.disconnect();
		}
	}
}
