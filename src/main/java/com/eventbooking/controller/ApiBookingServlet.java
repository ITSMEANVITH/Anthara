package com.eventbooking.controller;

import java.io.IOException;

import com.eventbooking.dao.BookingDAO;
import com.eventbooking.dao.EventDAO;
import com.eventbooking.model.Booking;
import com.eventbooking.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.eventbooking.model.Event;

@WebServlet("/api/bookings")
public class ApiBookingServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if(session==null){

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"success\":false}");
            return;

        }

        User user=(User)session.getAttribute("user");

        if(user==null){

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"success\":false}");
            return;

        }

        int eventId=Integer.parseInt(request.getParameter("eventId"));

        int tickets=Integer.parseInt(request.getParameter("tickets"));

        double totalAmount=Double.parseDouble(request.getParameter("totalAmount"));

        Booking booking=new Booking();

        booking.setUserId(user.getUserId());

        booking.setEventId(eventId);

        booking.setTickets(tickets);

        booking.setTotalAmount(totalAmount);

        BookingDAO dao=new BookingDAO();

        int bookingId=dao.bookTicket(booking);

        if(bookingId>0){

            EventDAO eventDAO=new EventDAO();

            eventDAO.updateSeats(eventId,tickets);

            response.getWriter().write(

                "{\"success\":true,\"bookingId\":"+bookingId+"}"

            );

        }
        

        else{

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            response.getWriter().write(

                "{\"success\":false}"

            );

        }

    }
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        int bookingId = Integer.parseInt(request.getParameter("id"));

        BookingDAO bookingDAO = new BookingDAO();
        Booking booking = bookingDAO.getBookingById(bookingId);

        if (booking == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        EventDAO eventDAO = new EventDAO();
        Event event = eventDAO.getEventById(booking.getEventId());

        String json =
            "{"
            + "\"bookingId\":" + booking.getBookingId() + ","
            + "\"eventId\":" + booking.getEventId() + ","
            + "\"title\":\"" + event.getEventName() + "\","
            + "\"venue\":\"" + event.getVenue() + "\","
            + "\"date\":\"" + event.getEventDate() + "\","
            + "\"time\":\"" + event.getEventTime() + "\","
            + "\"tickets\":" + booking.getTickets() + ","
            + "\"amount\":" + booking.getTotalAmount() + ","
            + "\"image\":\"" + (event.getEventImage()==null?"":event.getEventImage()) + "\""
            + "}";

        response.getWriter().print(json);
    }

}