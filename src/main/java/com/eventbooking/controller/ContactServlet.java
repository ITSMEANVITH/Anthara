package com.eventbooking.controller;
import java.io.IOException;

import com.eventbooking.dao.ContactDAO;
import com.eventbooking.model.Contact;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ContactServlet")
public class ContactServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Contact contact = new Contact();

        contact.setName(request.getParameter("name"));
        contact.setEmail(request.getParameter("email"));
        contact.setSubject(request.getParameter("subject"));
        contact.setMessage(request.getParameter("message"));

        ContactDAO dao = new ContactDAO();

        boolean status = dao.saveMessage(contact);

       

        if (status) {

            response.getWriter().write("{\"success\":true}");

        } else {

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            response.getWriter().write("{\"success\":false}");

        }

    }
}