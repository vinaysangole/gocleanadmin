package com.org.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.org.services.LocationServices;

public class GetAllFloorHandler extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		Map<Integer, String> floorMap = LocationServices.getAllFloors();
		
		JsonArray floorList = new JsonArray();
		for( Entry<Integer, String> floorEntry : floorMap.entrySet()) {
			JsonObject floorJSON = new JsonObject();
			floorJSON.addProperty("floorId", floorEntry.getKey());
			floorJSON.addProperty("floorName", floorEntry.getValue());
			floorList.add(floorJSON);
		}
		
		JsonObject floorListJSON = new JsonObject();
		floorListJSON.add("floorList", floorList);
		
		PrintWriter printWriter = response.getWriter();
		printWriter.write(floorListJSON.toString());
		printWriter.close();
	}
}