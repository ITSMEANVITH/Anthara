package com.eventbooking.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.eventbooking.dao.BookingDAO;
import com.eventbooking.dao.EventDAO;
import com.eventbooking.model.Booking;
import com.eventbooking.model.Event;
import com.eventbooking.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/api/bookings/me")
public class ApiMyBookingsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if(session == null){

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;

        }

        User user = (User)session.getAttribute("user");

        if(user == null){

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;

        }

        BookingDAO bookingDAO = new BookingDAO();

        ArrayList<Booking> bookings =
                bookingDAO.getBookingsByUser(user.getUserId());

        EventDAO eventDAO = new EventDAO();

        StringBuilder json = new StringBuilder();

        json.append("[");

        for(int i=0;i<bookings.size();i++){

            Booking booking = bookings.get(i);

            Event event =
                    eventDAO.getEventById(booking.getEventId());

            json.append("{")
                    .append("\"bookingId\":")
                    .append(booking.getBookingId())
                    .append(",")

                    .append("\"title\":\"")
                    .append(event.getEventName())
                    .append("\",")

                    .append("\"venue\":\"")
                    .append(event.getVenue())
                    .append("\",")

                    .append("\"date\":\"")
                    .append(event.getEventDate())
                    .append("\",")

                    .append("\"tickets\":")
                    .append(booking.getTickets())
                    .append(",")

                    .append("\"amount\":")
                    .append(booking.getTotalAmount())
                    .append(",")

                    .append("\"image\":\"")
                    .append(event.getEventImage()==null?"":event.getEventImage())
                    .append("\"")
                    .append("}");

            if(i<bookings.size()-1){

                json.append(",");

            }

        }

        json.append("]");

        response.getWriter().print(json.toString());

    }

}