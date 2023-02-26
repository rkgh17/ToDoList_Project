package com.hjh.todolist;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.jupiter.api.Test;

public class MySQLConnectionTest {

	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/todo?useSSL=false&useUnicode=true&serverTimezone=Asia/Seoul";
	private static final String USER = "root";
	private static final String PASSWORD = "hj1217";
	
	@Test
	public void testConnection() throws Exception{
		Class.forName(DRIVER);
		try {
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println(connection);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
