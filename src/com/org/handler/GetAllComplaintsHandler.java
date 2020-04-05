package com.org.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.org.model.Complaint;
import com.org.services.ComplaintServices;
import com.org.services.LocationServices;

public class GetAllComplaintsHandler extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		List<Complaint> complaints = ComplaintServices.getAllComplaints();
		JsonArray complaintsArray = new JsonArray();
		
		for(Complaint complaint : complaints) {
			JsonObject complaintJSON = new JsonObject();
			complaintJSON.addProperty("complaintId", complaint.getComplaintId());
			complaintJSON.addProperty("cescription", complaint.getDescription());
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
		
		JsonObject complaintsJSON = new JsonObject();
		complaintsJSON.add("complaints", complaintsArray);
		
		PrintWriter printWriter = response.getWriter();
		printWriter.write(complaintsJSON.toString());
		printWriter.close();
	}
}