package com.eventbooking.controller;

import java.io.IOException;

import com.eventbooking.dao.BookingDAO;
import com.eventbooking.model.Booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/VerifyTicketServlet")
public class VerifyTicketServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if(session == null){

            response.sendRedirect("login.jsp");

            return;

        }

        String bookingIdStr = request.getParameter("bookingId");

        if(bookingIdStr == null){

            response.sendRedirect("myBookings.jsp");

            return;

        }

        int bookingId = Integer.parseInt(bookingIdStr);

        BookingDAO dao = new BookingDAO();

        Booking booking = dao.getBookingById(bookingId);

        if(booking == null){

            request.setAttribute("message", "INVALID TICKET");

            request.setAttribute("status", "INVALID");

        }else{

            request.setAttribute("booking", booking);

            if("CANCELLED".equalsIgnoreCase(booking.getStatus())){

                request.setAttribute("status", "CANCELLED");

                request.setAttribute("message", "THIS TICKET HAS BEEN CANCELLED");

            }else{

                request.setAttribute("status", "VALID");

                request.setAttribute("message", "VALID TICKET");

            }

        }

        request.getRequestDispatcher("verifyTicket.jsp")
               .forward(request, response);

    }

}