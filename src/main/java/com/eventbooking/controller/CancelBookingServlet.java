package com.eventbooking.controller;

import java.io.IOException;

import com.eventbooking.dao.UserDAO;
import com.eventbooking.dao.EventDAO;
import com.eventbooking.model.User;
import com.eventbooking.model.Event;
import com.eventbooking.util.EmailUtil;

import com.eventbooking.dao.BookingDAO;

import com.eventbooking.model.Booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CancelBookingServlet")
public class CancelBookingServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int bookingId =
        Integer.parseInt(request.getParameter("bookingId"));

        BookingDAO bookingDAO = new BookingDAO();

        Booking booking =
        bookingDAO.getBookingById(bookingId);

        if(booking != null){

            bookingDAO.cancelBooking(bookingId);
            
            UserDAO userDAO = new UserDAO();

            User user =
            userDAO.getUserById(booking.getUserId());

            Event event =
            new EventDAO().getEventById(booking.getEventId());

            try {

                EmailUtil.sendCancellationEmail(

                        user.getEmail(),

                        user.getFullName(),

                        event.getEventName(),

                        booking.getBookingId(),

                        booking.getTickets(),

                        booking.getTotalAmount()

                );

            } catch (Exception e) {

                e.printStackTrace();

            }

            EventDAO eventDAO = new EventDAO();

            eventDAO.restoreSeats(

                    booking.getEventId(),

                    booking.getTickets()

            );

        }

        response.sendRedirect(request.getContextPath() + "/myBookings.jsp");

    }

}