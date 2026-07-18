package com.eventbooking.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.eventbooking.dao.EventDAO;
import com.eventbooking.model.Event;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/api/admin/events")
public class ApiAdminEventsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute("admin") == null){

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            return;

        }

        EventDAO dao = new EventDAO();

        ArrayList<Event> list = dao.getAllEvents();

        StringBuilder json = new StringBuilder();

        json.append("[");

        for(int i=0;i<list.size();i++){

            Event e = list.get(i);

            json.append("{");

            json.append("\"eventId\":").append(e.getEventId()).append(",");

            json.append("\"title\":\"").append(e.getEventName()).append("\",");

            json.append("\"date\":\"").append(e.getEventDate()).append("\",");

            json.append("\"time\":\"").append(e.getEventTime()).append("\",");

            json.append("\"venue\":\"").append(e.getVenue()).append("\",");

            json.append("\"price\":").append(e.getTicketPrice()).append(",");

            json.append("\"seats\":").append(e.getAvailableSeats()).append(",");

            json.append("\"image\":\"")
                .append(e.getEventImage()==null?"":e.getEventImage())
                .append("\"");

            json.append("}");

            if(i < list.size()-1){

                json.append(",");

            }

        }

        json.append("]");

        response.getWriter().print(json.toString());

    }

}