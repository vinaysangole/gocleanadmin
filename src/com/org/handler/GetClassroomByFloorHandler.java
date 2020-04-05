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
import com.org.services.LocationServices;

public class GetClassroomByFloorHandler extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		String floorId = request.getParameter("floorId");
		
		Map<Integer, String> classroomMap = null;
		
		if(StringUtils.isNotEmpty(floorId)) {
			classroomMap = LocationServices.getClassroomByFloor(Integer.valueOf(floorId));
		}
		
		JsonArray classroomList = new JsonArray();
		for( Entry<Integer, String> classroomEntry : classroomMap.entrySet()) {
			JsonObject classroomJSON = new JsonObject();
			classroomJSON.addProperty("classroomId", classroomEntry.getKey());
			classroomJSON.addProperty("classroomName", classroomEntry.getValue());
			classroomList.add(classroomJSON);
		}
		
		JsonObject classroomListJSON = new JsonObject();
		classroomListJSON.add("classroomList", classroomList);
		
		PrintWriter printWriter = response.getWriter();
		printWriter.write(classroomListJSON.toString());
		printWriter.close();
	}
}