package com.org.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.org.db.ConnectionManager;
import com.org.db.Queries;
import com.org.model.Complaint;

public class ComplaintServices {
	private final static String COMPLAINT_LOGGED = "Complaint Logged";
	
	public static List<Complaint> getAllComplaints () {
		List<Complaint> complaints = new ArrayList<Complaint>();
		Connection connection =  ConnectionManager.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(Queries.GET_ALL_COMPLAINTS);
			
			while(resultSet.next()) {
				Complaint complaint = new Complaint();
				complaint.setComplaintId(resultSet.getInt("complaintId"));
				complaint.setDescription(resultSet.getString("description"));
				complaint.setLoggedDate(resultSet.getDate("loggedDate"));
				complaint.setImage(resultSet.getBlob("image") != null ? resultSet.getBlob("image").toString().getBytes() : null);
				complaint.setComplaintStatusId(resultSet.getInt("complaintStatusId"));
				complaint.setUserId(resultSet.getInt("userId"));
				complaint.setFloorId(resultSet.getInt("floorId"));
				complaint.setSectionId(resultSet.getInt("sectionId"));
				complaint.setClassroomId(resultSet.getInt("classroomId"));
				complaints.add(complaint);
				System.out.println(complaint);
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
		
		return complaints;
	}
	
	public static int getComplaintStatusByName (String complaintType) {
		int complaintStatusId = -1;
		Connection connection =  ConnectionManager.getConnection();
		PreparedStatement  statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Queries.GET_COMPLAINT_STATUS_BY_NAME);
			statement.setString(1, complaintType);
			
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				complaintStatusId = resultSet.getInt("complaintStatusId");
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
		
		return complaintStatusId;
	}
	
	
	public static int insertComplaint ( String description, byte [] image, int userId, int floorId, int sectionId, Integer classroomId) {
		int insertStatusId = -1;
		
		int complaintStatusId = getComplaintStatusByName(COMPLAINT_LOGGED);
		Date loggedDate = new Date();
		
		Connection connection =  ConnectionManager.getConnection();
		PreparedStatement  statement = null;
		try {
			statement = connection.prepareStatement(Queries.INSERT_COMPLAINTS_TEMP);
			statement.setString(1, description);
			statement.setDate(2, new java.sql.Date(loggedDate.getTime()));
			statement.setInt(3, complaintStatusId);
			statement.setInt(4, userId);
			statement.setInt(5, floorId);
			statement.setInt(6, sectionId);
			statement.setInt(7, classroomId);
			
			insertStatusId = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return insertStatusId;
	}
	
	public static void main(String args[]){
		List<Complaint> complaints = getAllComplaints ();
		System.out.println(complaints);
		
		int complaintStatusId = getComplaintStatusByName (COMPLAINT_LOGGED);
		System.out.println("Complaint Status Fetched :: " + complaintStatusId);
		
		int insertComplaintId = insertComplaint ( "Third Floor Washroom", null, 2, 4, 4, 1);
		System.out.println("Complaint Inserted :: " + insertComplaintId);
	}
}
