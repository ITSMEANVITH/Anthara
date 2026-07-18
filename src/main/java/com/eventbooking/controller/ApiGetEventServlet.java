package com.eventbooking.controller;

import java.io.IOException;

import com.eventbooking.dao.EventDAO;
import com.eventbooking.model.Event;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/api/admin/event")
public class ApiGetEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if(session==null || session.getAttribute("admin")==null){

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            return;

        }

        int eventId =
                Integer.parseInt(request.getParameter("eventId"));

        EventDAO dao=new EventDAO();

        Event event=dao.getEventById(eventId);
        
        if (event == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"success\":false,\"message\":\"Event not found\"}");
            return;
        }

       

        String json =
        		"{"
        		+ "\"eventId\":" + event.getEventId() + ","
        		+ "\"eventName\":\"" + event.getEventName() + "\","
        		+ "\"eventDate\":\"" + event.getEventDate() + "\","
        		+ "\"eventTime\":\"" + event.getEventTime() + "\","
        		+ "\"venue\":\"" + event.getVenue() + "\","
        		+ "\"ticketPrice\":" + event.getTicketPrice() + ","
        		+ "\"availableSeats\":" + event.getAvailableSeats() + ","
        		+ "\"bookingOpen\":" + event.isBookingOpen() + ","
        		+ "\"homeSection\":\"" +
        		(event.getHomeSection() == null ? "hide" : event.getHomeSection())
        		+ "\","
        		+ "\"eventImage\":\"" +
        		(event.getEventImage()==null ? "" : event.getEventImage())
        		+ "\""
        		+ "}";

        		response.setContentType("application/json");
        		response.setCharacterEncoding("UTF-8");
        		response.getWriter().print(json);


    }

}