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

@WebServlet("/api/events")
public class ApiEventsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        EventDAO dao = new EventDAO();

        // ---------- SINGLE EVENT ----------
        String id = request.getParameter("id");

        if (id != null) {

            Event e = dao.getEventById(Integer.parseInt(id));

            if (e == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            String json =
                    "{"
                    + "\"eventId\":" + e.getEventId() + ","
                    + "\"title\":\"" + e.getEventName() + "\","
                    + "\"venue\":\"" + e.getVenue() + "\","
                    + "\"date\":\"" + e.getEventDate() + "\","
                    + "\"time\":\"" + e.getEventTime() + "\","
                    + "\"price\":" + e.getTicketPrice() + ","
                    + "\"availableSeats\":" + e.getAvailableSeats() + ","
                    + "\"image\":\"" + (e.getEventImage() == null ? "" : e.getEventImage()) + "\","
                    + "\"category\":\"" + (e.getCategory() == null ? "" : e.getCategory()) + "\","
                    + "\"bookingOpen\":" + e.isBookingOpen() + ","
                    + "\"homeSection\":\"" + (e.getHomeSection() == null ? "hide" : e.getHomeSection()) + "\""
                    + "}";

            response.getWriter().print(json);
            return;
        }

        // ---------- ALL EVENTS ----------

        ArrayList<Event> events = dao.getAllEvents();

        StringBuilder json = new StringBuilder();

        json.append("[");

        for (int i = 0; i < events.size(); i++) {

            Event e = events.get(i);

            json.append("{")
                    .append("\"eventId\":").append(e.getEventId()).append(",")
                    .append("\"title\":\"").append(e.getEventName()).append("\",")
                    .append("\"venue\":\"").append(e.getVenue()).append("\",")
                    .append("\"date\":\"").append(e.getEventDate()).append("\",")
                    .append("\"time\":\"").append(e.getEventTime()).append("\",")
                    .append("\"price\":").append(e.getTicketPrice()).append(",")
                    .append("\"availableSeats\":").append(e.getAvailableSeats()).append(",")
                    .append("\"image\":\"").append(e.getEventImage() == null ? "" : e.getEventImage()).append("\",")
                    .append("\"category\":\"").append(e.getCategory() == null ? "" : e.getCategory()).append("\",")
                    .append("\"bookingOpen\":").append(e.isBookingOpen()).append(",")
                    .append("\"homeSection\":\"").append(e.getHomeSection() == null ? "hide" : e.getHomeSection()).append("\"")
                    .append("}");

            if (i < events.size() - 1) {
                json.append(",");
            }
        }

        json.append("]");

        response.getWriter().print(json);
    }
}