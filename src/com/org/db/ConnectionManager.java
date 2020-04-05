package com.org.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String CONNEECTION_STRING = "jdbc:mysql://localhost:3306/gocleandb";
	
	private static Connection connection = null;
	
	public static Connection getConnection() {
		if(connection == null) {
			try {
				Class.forName(DRIVER);
				connection = DriverManager.getConnection(CONNEECTION_STRING,"root","root");
			} catch(Exception e) { 
				System.out.println(e);
			}
		}
		return connection;
	}
}
