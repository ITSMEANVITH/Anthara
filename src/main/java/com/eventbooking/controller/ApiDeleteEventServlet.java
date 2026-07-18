package com.eventbooking.controller;

import java.io.IOException;

import com.eventbooking.dao.EventDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/api/admin/deleteEvent")
public class ApiDeleteEventServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if(session==null || session.getAttribute("admin")==null){

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            return;

        }

        int eventId = Integer.parseInt(request.getParameter("eventId"));

        System.out.println("DELETE EVENT ID = " + eventId);

        EventDAO dao = new EventDAO();

        boolean status = dao.deleteEvent(eventId);

        System.out.println("DELETE STATUS = " + status);

        if(status){

            response.getWriter().write("{\"success\":true}");

        }
        else{

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            response.getWriter().write(
                "{\"message\":\"This event has bookings and cannot be deleted.\"}"
            );

        }
        

    }

}