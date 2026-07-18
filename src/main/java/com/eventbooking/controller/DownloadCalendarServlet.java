package com.eventbooking.controller;

import java.io.IOException;

import com.eventbooking.dao.BookingDAO;
import com.eventbooking.dao.EventDAO;
import com.eventbooking.model.Booking;
import com.eventbooking.model.Event;
import com.eventbooking.util.CalendarUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DownloadCalendarServlet")
public class DownloadCalendarServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int bookingId =
                    Integer.parseInt(request.getParameter("bookingId"));

            BookingDAO bookingDAO = new BookingDAO();

            Booking booking =
                    bookingDAO.getBookingById(bookingId);

            if (booking == null) {

                response.sendError(404);

                return;

            }

            EventDAO eventDAO = new EventDAO();

            Event event =
                    eventDAO.getEventById(booking.getEventId());

            byte[] calendar =
                    CalendarUtil.generateCalendarInvite(event);

            response.setContentType("text/calendar");

            response.setHeader(
                    "Content-Disposition",
                    "attachment; filename=Anthara_Event.ics");

            response.getOutputStream().write(calendar);

        }

        catch(Exception e){

            e.printStackTrace();

        }

    }

}