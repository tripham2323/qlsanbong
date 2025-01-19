package bailam;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.*;
public class db {
	private static String url = "jdbc:mysql://localhost:3306/soccerfield?autoReconnect=true&useSSL=false";
	private static String username = "root";
	private static String password = "ductri123";
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		//nap driver
		Class.forName("com.mysql.cj.jdbc.Driver");
		return (Connection)DriverManager.getConnection(url, username, password);
	}
	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		if (conn == null) {
			System.out.println("Fail");
		} else {
			System.out.println("Success");
		}
		
	}

}
