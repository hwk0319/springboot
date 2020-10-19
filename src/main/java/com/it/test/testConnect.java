package com.it.test;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.it.util.JDBCConnect;
import com.it.util.SSHConnect;

public class testConnect {

	public static void main(String[] args) {
//		SSHConnect.SSHJSch("192.168.43.215", 22, "root", "111111");
//		String res = SSHConnect.runCmd("java -version");
//		System.out.println(res);
//		SSHConnect.closeSession();
		
		String url = "jdbc:oracle:thin:@localhost:1521/mytest";
		String user = "xtgl";
		String password = "oracle";
		JDBCConnect.openJDCBConnect(url, user, password);
		String sql="select * from server";
		ResultSet rs = JDBCConnect.executeQuery(sql);
		try {
			while(rs.next()) {
				System.out.println(rs.getString("name")+rs.getString("ip"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCConnect.close();
		}
	}
}
