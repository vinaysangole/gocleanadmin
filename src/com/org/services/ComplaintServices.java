package com.org.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.org.db.ConnectionManager;
import com.org.db.Queries;
import com.org.model.Complaint;

public class ComplaintServices {
	private final static String COMPLAINT_LOGGED = "Complaint Logged";
	
	public static JsonArray getAllComplaintsJsonArray () {
		List<Complaint> complaints = ComplaintServices.getAllComplaints();
		JsonArray complaintsArray = prepareJSONFromComplaints(complaints);
		return complaintsArray;
	}
	
	public static JsonArray getComplaintsByUserIdJsonArray (int userId) {
		List<Complaint> complaints = ComplaintServices.getComplaintsByUserId(userId);
		JsonArray complaintsArray = prepareJSONFromComplaints(complaints);
		return complaintsArray;
	}
	
	private static JsonArray prepareJSONFromComplaints(List<Complaint> complaints) {
		JsonArray complaintsArray = new JsonArray();
		
		for(Complaint complaint : complaints) {
			JsonObject complaintJSON = new JsonObject();
			complaintJSON.addProperty("complaintId", complaint.getComplaintId());
			complaintJSON.addProperty("description", complaint.getDescription());
			complaintJSON.addProperty("loggedDate", complaint.getLoggedDate().toString());
			complaintJSON.addProperty("image", complaint.getImage() != null ? complaint.getImage().toString() : null);
			complaintJSON.addProperty("complaintStatusId", complaint.getComplaintStatusId());
			complaintJSON.addProperty("userId", complaint.getUserId());
			
			JsonObject floorJSON = new JsonObject();
			floorJSON.addProperty("floorId", complaint.getFloorId());
			floorJSON.addProperty("floorName", LocationServices.getFloorByFloorId(complaint.getFloorId()));
			complaintJSON.add("floor", floorJSON);
			
			JsonObject sectionJSON = new JsonObject();
			sectionJSON.addProperty("sectionId", complaint.getSectionId());
			sectionJSON.addProperty("sectionName", LocationServices.getSectionBySectionId(complaint.getSectionId()));
			complaintJSON.add("section", sectionJSON);
			
			if(complaint.getClassroomId() > 1) {
				JsonObject classroomJSON = new JsonObject();
				classroomJSON.addProperty("classroomId", complaint.getClassroomId());
				classroomJSON.addProperty("classroomName", LocationServices.getClassroomByClassroomId(complaint.getClassroomId()));
				complaintJSON.add("classroom", classroomJSON);
			}
			
			complaintsArray.add(complaintJSON);
		}
		
		return complaintsArray;
	}

	public static List<Complaint> getComplaintsByUserId (int userId) {
		List<Complaint> complaints = new ArrayList<Complaint>();
		Connection connection =  ConnectionManager.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(Queries.GET_COMPLAINTS_BY_USERID);
			statement.setInt(1, userId);
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				Complaint complaint = new Complaint();
				complaint.setComplaintId(resultSet.getInt("complaintId"));
				complaint.setDescription(resultSet.getString("description"));
				complaint.setLoggedDate(resultSet.getDate("loggedDate"));
				complaint.setImage(resultSet.getString("image"));
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
				complaint.setImage(resultSet.getString("image"));
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
	
	
	public static int insertComplaint ( String description, String image, int userId, int floorId, int sectionId, Integer classroomId) {
		int insertStatusId = -1;
		
		int complaintStatusId = getComplaintStatusByName(COMPLAINT_LOGGED);
		Date loggedDate = new Date();
		
		Connection connection =  ConnectionManager.getConnection();
		PreparedStatement  statement = null;
		try {
			statement = connection.prepareStatement(Queries.INSERT_COMPLAINTS);
			statement.setString(1, description);
			statement.setDate(2, new java.sql.Date(loggedDate.getTime()));
			statement.setString(3, image);
			statement.setInt(4, complaintStatusId);
			statement.setInt(5, userId);
			statement.setInt(6, floorId);
			statement.setInt(7, sectionId);
			statement.setInt(8, classroomId);
			
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
