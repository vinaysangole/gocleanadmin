package com.org.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.org.model.Complaint;
import com.org.services.ComplaintServices;
import com.org.services.LocationServices;

public class GetComplaintsByUserHandler extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		String userId = request.getParameter("userId");
		
		JsonArray complaintsArray = new JsonArray();
		
		if(StringUtils.isNotEmpty(userId)) {
			complaintsArray = ComplaintServices.getComplaintsByUserIdJsonArray(Integer.valueOf(userId));
		}
		
		JsonObject complaintsJSON = new JsonObject();
		complaintsJSON.add("complaints", complaintsArray);
		
		PrintWriter printWriter = response.getWriter();
		printWriter.write(complaintsJSON.toString());
		printWriter.close();
	}
}