package com.eventbooking.controller;

import java.io.IOException;

import com.eventbooking.dao.BookingDAO;
import com.eventbooking.dao.EventDAO;
import com.eventbooking.dao.SeatDAO;
import com.eventbooking.model.Booking;
import com.eventbooking.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        if(user==null){

            response.sendRedirect("login.jsp");
            return;

        }

        int eventId=Integer.parseInt(request.getParameter("eventId"));

        int tickets=Integer.parseInt(request.getParameter("tickets"));

        double totalAmount=Double.parseDouble(request.getParameter("totalAmount"));

        String seatNumbers=request.getParameter("seatNumbers");

        Booking booking=new Booking();

        booking.setUserId(user.getUserId());

        booking.setEventId(eventId);

        booking.setTickets(tickets);

        booking.setTotalAmount(totalAmount);

       

        BookingDAO bookingDAO=new BookingDAO();

        int bookingId=bookingDAO.bookTicket(booking);

        if(bookingId>0){

            EventDAO eventDAO=new EventDAO();

            eventDAO.updateSeats(eventId,tickets);

            SeatDAO seatDAO=new SeatDAO();

            seatDAO.bookSeats(eventId,seatNumbers,bookingId);

            response.sendRedirect("payment.jsp?bookingId="+bookingId);

        }

        else{

            response.sendRedirect("events.jsp");

        }

    }

}