package com.org.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.org.services.ComplaintServices;
import com.org.services.LocationServices;

public class LodgeComplaintHandler extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		String description = request.getParameter("description");
		String image = request.getParameter("image");
		String userId = request.getParameter("userId");
		String floorId = request.getParameter("floorId");
		String sectionId = request.getParameter("sectionId");
		String classroomId = request.getParameter("classroomId");
		
		JsonObject lodgeComplaintJSON = new JsonObject();
		
		if(StringUtils.isNotEmpty(description) && StringUtils.isNotEmpty(image) && StringUtils.isNotEmpty(userId) 
				&& StringUtils.isNotEmpty(floorId) && StringUtils.isNotEmpty(sectionId)) {
			if(sectionId.equals("1")) {
				if(StringUtils.isNotEmpty(classroomId)) {
					int statusId = ComplaintServices.insertComplaint(description, image, Integer.parseInt(userId), Integer.parseInt(floorId), Integer.parseInt(sectionId), Integer.parseInt(classroomId));
					if(statusId != -1) {
						lodgeComplaintJSON.addProperty("statusCode", "200");
						lodgeComplaintJSON.addProperty("lodgeStatus", "Complaint lodged Successfully.");
					}
				} else {
					lodgeComplaintJSON.addProperty("statusCode", "210");
					lodgeComplaintJSON.addProperty("lodgeStatus", "classroomId is empty.");
				}
			} else {
				int statusId = ComplaintServices.insertComplaint(description, image, Integer.parseInt(userId), Integer.parseInt(floorId), Integer.parseInt(sectionId), 1);
				if(statusId != -1) {
					lodgeComplaintJSON.addProperty("statusCode", "200");
					lodgeComplaintJSON.addProperty("lodgeStatus", "Complaint lodged Successfully.");
				}
			}
		} else {
			lodgeComplaintJSON.addProperty("statusCode", "210");
			lodgeComplaintJSON.addProperty("lodgeStatus", "Either description, image, userId, floorId or sectionId is empty.");
		}
		
		PrintWriter printWriter = response.getWriter();
		printWriter.write(lodgeComplaintJSON.toString());
		printWriter.close();
	}
}