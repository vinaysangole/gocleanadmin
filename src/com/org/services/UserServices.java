package com.org.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;

import com.org.db.ConnectionManager;
import com.org.db.Queries;
import com.org.model.User;

public class UserServices {
	
	public static User getUser(String loginId, String password) {
		User user = getUserByLoginIdNPassword (loginId, password);
		if(user != null) {
			String role = getRoleByRoleId(user.getRoleId());
			
			if(StringUtils.isNotEmpty(role))
				user.setRole(role);
		}
		
		return user;
	}
	
	public static User getUserByLoginIdNPassword (String loginId, String password) {
		User user = null;
		Connection connection =  ConnectionManager.getConnection();
		PreparedStatement  statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Queries.GET_USER_BY_LOGINID_PASSWORD);
			statement.setString(1, loginId);
			statement.setString(2, password);
			
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				user = new User();
				user.setUserId(resultSet.getInt("userId"));
				user.setRoleId(resultSet.getInt("roleId"));
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
		
		return user;
	}
	
	public static String getRoleByRoleId (int roleId) {
		String role = null;
		Connection connection =  ConnectionManager.getConnection();
		PreparedStatement  statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Queries.GET_ROLE_BY_ROLEID);
			statement.setInt(1, roleId);
			
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				role = resultSet.getString("roleName");
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
		
		return role;
	}
}
