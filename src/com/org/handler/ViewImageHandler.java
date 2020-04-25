package com.org.handler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class ViewImageHandler extends HttpServlet {

	private String filePath;

	public void init() {
		filePath = getServletContext().getInitParameter("file-upload");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("image/jpeg");
		String imageName = request.getParameter("imageName");

		if (StringUtils.isNotEmpty(imageName)) {
			ServletOutputStream out = response.getOutputStream();
			FileInputStream flinp = new FileInputStream(filePath + "\\" + imageName);
			BufferedInputStream buffinp = new BufferedInputStream(flinp);
			BufferedOutputStream buffoup = new BufferedOutputStream(out);
			int ch = 0;
			while ((ch = buffinp.read()) != -1) {
				buffoup.write(ch);
			}
			buffinp.close();
			flinp.close();
			buffoup.close();
			out.close();
		}
	}
}