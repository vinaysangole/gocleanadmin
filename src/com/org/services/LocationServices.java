package com.org.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.org.db.ConnectionManager;
import com.org.db.Queries;

public class LocationServices {
	
	public static Map<Integer, String> getAllFloors () {
		Map<Integer, String> floorMap = new HashMap<Integer, String>();
		
		Connection connection =  ConnectionManager.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(Queries.GET_ALL_FLOORS);
			
			while(resultSet.next()) {
				floorMap.put(resultSet.getInt("floorId"), resultSet.getString("name"));
				System.out.println(resultSet.getInt("floorId") + "  " + resultSet.getString("name"));
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
		
		return floorMap;
	}
	
	public static Map<Integer, String> getSectionsByFloor (int floorId) {
		Map<Integer, String> sectionsMap = new HashMap<Integer, String>();
		
		Connection connection =  ConnectionManager.getConnection();
		PreparedStatement  statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Queries.GET_SECTIONS_BY_FLOOR);
			statement.setInt(1, floorId);
			
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				sectionsMap.put(resultSet.getInt("sectionId"), resultSet.getString("name"));
				System.out.println(resultSet.getInt("sectionId") + "  " + resultSet.getString("name"));
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
		
		return sectionsMap;
	}
	
	public static Map<Integer, String> getClassroomByFloor (int floorId) {
		Map<Integer, String> classroomMap = new HashMap<Integer, String>();
		
		Connection connection =  ConnectionManager.getConnection();
		PreparedStatement  statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Queries.GET_CLASSROOM_BY_FLOOR);
			statement.setInt(1, floorId);
			
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				classroomMap.put(resultSet.getInt("classroomId"), resultSet.getString("name"));
				System.out.println(resultSet.getInt("classroomId") + "  " + resultSet.getString("name"));
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
		
		return classroomMap;
	}
	
	public static String getFloorByFloorId (int floorId) {
		String floorName = "";
		
		Connection connection =  ConnectionManager.getConnection();
		PreparedStatement  statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Queries.GET_FLOOR_BY_FLOORID);
			statement.setInt(1, floorId);
			
			resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				floorName = resultSet.getString("name");
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
		
		return floorName;
	}
	
	public static String getSectionBySectionId (int sectionId) {
		String sectionName = "";
		
		Connection connection =  ConnectionManager.getConnection();
		PreparedStatement  statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Queries.GET_SECTION_BY_SECTIONID);
			statement.setInt(1, sectionId);
			
			resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				sectionName = resultSet.getString("name");
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
		
		return sectionName;
	}
	
	public static String getClassroomByClassroomId (int classroomId) {
		String classroomName = "";
		
		Connection connection =  ConnectionManager.getConnection();
		PreparedStatement  statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Queries.GET_CLASSROOM_BY_CLASSROOMID);
			statement.setInt(1, classroomId);
			
			resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				classroomName = resultSet.getString("name");
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
		
		return classroomName;
	}
	
	public static void main(String args[]){
		Map<Integer, String> floorMap = getAllFloors();
		System.out.println(floorMap);
		
		Map<Integer, String> sectionsMap = getSectionsByFloor(1);
		System.out.println(sectionsMap);
		
		Map<Integer, String> classroomMap = getClassroomByFloor(1);
		System.out.println(classroomMap);
		
		String floorName = getFloorByFloorId(1);
		System.out.println("Floor Name :: " + floorName);
		
		String sectionName = getSectionBySectionId(1);
		System.out.println("Section Name :: " + sectionName);
		
		String classroomName = getClassroomByClassroomId(2);
		System.out.println("Classroom Name :: " + classroomName);
	}
}
