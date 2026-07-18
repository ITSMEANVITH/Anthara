package com.eventbooking.controller;

import com.eventbooking.dao.ContactDAO;
import com.eventbooking.model.Contact;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/admin/contactMessages")
public class AdminContactServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

  

	@Override
	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response)
	        throws ServletException, IOException {

	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");

	    ContactDAO dao = new ContactDAO();

	    ArrayList<Contact> messages = dao.getAllMessages();

	    StringBuilder json = new StringBuilder();

	    json.append("[");

	    for (int i = 0; i < messages.size(); i++) {

	        Contact c = messages.get(i);

	        json.append("{");
	        json.append("\"id\":").append(c.getId()).append(",");
	        json.append("\"name\":\"").append(c.getName().replace("\"", "\\\"")).append("\",");
	        json.append("\"email\":\"").append(c.getEmail().replace("\"", "\\\"")).append("\",");
	        json.append("\"subject\":\"").append(c.getSubject().replace("\"", "\\\"")).append("\",");
	        json.append("\"message\":\"").append(c.getMessage().replace("\"", "\\\"")).append("\",");
	        json.append("\"createdAt\":\"").append(c.getCreatedAt()).append("\"");
	        json.append("}");

	        if (i < messages.size() - 1) {
	            json.append(",");
	        }
	    }

	    json.append("]");

	    response.getWriter().write(json.toString());
	}
}