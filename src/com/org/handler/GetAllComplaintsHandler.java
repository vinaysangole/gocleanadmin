package com.org.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.org.services.ComplaintServices;

public class GetAllComplaintsHandler extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		JsonArray complaintsArray = ComplaintServices.getAllComplaintsJsonArray();
		
		JsonObject complaintsJSON = new JsonObject();
		complaintsJSON.add("complaints", complaintsArray);
		
		PrintWriter printWriter = response.getWriter();
		printWriter.write(complaintsJSON.toString());
		printWriter.close();
	}
}