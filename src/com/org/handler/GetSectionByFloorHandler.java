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

public class GetSectionByFloorHandler extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		String floorId = request.getParameter("floorId");
		
		Map<Integer, String> sectionsMap = null;
		
		if(StringUtils.isNotEmpty(floorId)) {
			sectionsMap = LocationServices.getSectionsByFloor(Integer.valueOf(floorId));
		}
		
		JsonArray sectionList = new JsonArray();
		for( Entry<Integer, String> sectionEntry : sectionsMap.entrySet()) {
			JsonObject sectionJSON = new JsonObject();
			sectionJSON.addProperty("sectionId", sectionEntry.getKey());
			sectionJSON.addProperty("sectionName", sectionEntry.getValue());
			sectionList.add(sectionJSON);
		}
		
		JsonObject sectionListJSON = new JsonObject();
		sectionListJSON.add("sectionList", sectionList);
		
		PrintWriter printWriter = response.getWriter();
		printWriter.write(sectionListJSON.toString());
		printWriter.close();
	}
}