package com.eventbooking.controller;

import java.io.IOException;

import com.eventbooking.dao.EventDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DeleteEventServlet")
public class DeleteEventServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int eventId =
                Integer.parseInt(request.getParameter("id"));

        EventDAO dao = new EventDAO();

        dao.deleteEvent(eventId);

        response.sendRedirect("manageEvents.jsp");

    }

}