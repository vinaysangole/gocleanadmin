package com.org.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.org.db.ConnectionManager;
import com.org.db.Queries;

public class UserServices {
	
	public static int getUserByLoginIdNPassword (String loginId, String password) {
		int userId = -1;
		Connection connection =  ConnectionManager.getConnection();
		PreparedStatement  statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Queries.GET_USER_BY_LOGINID_PASSWORD);
			statement.setString(1, loginId);
			statement.setString(2, password);
			
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				userId = resultSet.getInt("userId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return userId;
	}
	
}
