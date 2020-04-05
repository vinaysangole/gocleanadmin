package com.org.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonObject;
import com.org.services.UserServices;

public class UserLoginHandler extends HttpServlet{
	private static final String SUCCESS = "Success";
	private static final String FAILURE = "failure";
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		
		int userId = -1;
		
		if(StringUtils.isNotEmpty(loginId) || StringUtils.isNotEmpty(password)) {
			userId = UserServices.getUserByLoginIdNPassword(loginId, password);
		}
		
		JsonObject outputJson = new JsonObject();
		if(userId != -1) {
			outputJson.addProperty("logonStatus", SUCCESS);
			outputJson.addProperty("userId", userId);
		} else {
			outputJson.addProperty("logonStatus", FAILURE);
		}
		
		PrintWriter printWriter = response.getWriter();
		printWriter.write(outputJson.toString());
		printWriter.close();
	}
}