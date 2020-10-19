package com.it.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: JDBCConnect  
 * @Description: TODO  JDBC连接数据库类
 * @author Administrator  
 * @date 2020年9月29日  
 *
 */
public class JDBCConnect {
	private static final Logger log = LoggerFactory.getLogger(JDBCConnect.class);
	private static Connection connection = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	
	
	/**
	 * 
	 * @Title: openJDCBConnect  
	 * @Description: TODO  加载数据库驱动，获取连接
	 * @param     参数  
	 * @return void    返回类型  
	 * @throws
	 */
	public static void openJDCBConnect(String url, String user, String password) {
//		String url = "jdbc:oracle:thin:@localhost:1521/mytest";
//		String user = "xtgl";
//		String password = "oracle";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @Title: close  
	 * @Description: TODO  关闭连接
	 * @param     参数  
	 * @return void    返回类型  
	 * @throws
	 */
	public static void close() {
		try {
			if(connection != null) {
				connection.close();
			}
			if(statement != null) {
				statement.close();
			}
			if(resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @Title: executeQuery  
	 * @Description: TODO  根据sql查询数据,需要先获取connection
	 * @param @param sql
	 * @param @return    参数  
	 * @return ResultSet    返回类型  
	 * @throws
	 */
	public static ResultSet executeQuery(String sql) {
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			return resultSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @Title: testConnect  
	 * @Description: TODO  测试jdbc连接数据库
	 * @param @param url
	 * @param @param user
	 * @param @param password
	 * @param @return    参数  
	 * @return boolean    返回类型  
	 * @throws
	 */
	public static boolean testConnect(String url, String user, String password) {
		openJDCBConnect(url, user, password);
		if(connection != null) {
			close();
			return true;
		}else {
			return false;
		}
	}
}
