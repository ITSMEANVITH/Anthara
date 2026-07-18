package com.eventbooking.controller;

import java.io.IOException;

import java.util.ArrayList;

import com.eventbooking.dao.BookingDAO;
import com.eventbooking.dao.EventDAO;
import com.eventbooking.dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/api/admin/analytics")
public class ApiAdminAnalyticsServlet extends HttpServlet {

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

            response.getWriter().write("{\"success\":false}");

            return;

        }

        UserDAO userDAO = new UserDAO();

        EventDAO eventDAO = new EventDAO();

        BookingDAO bookingDAO = new BookingDAO();
        
        ArrayList<Double> monthlyRevenue =
                bookingDAO.getMonthlyRevenue();
        
        
        ArrayList<Integer> monthlyBookings =
                bookingDAO.getMonthlyBookings();

        String json =
        		"{"
        		+ "\"totalUsers\":" + userDAO.getTotalUsers() + ","
        		+ "\"totalEvents\":" + eventDAO.getTotalEvents() + ","
        		+ "\"totalBookings\":" + bookingDAO.getTotalBookings() + ","
        		+ "\"pendingBookings\":" + bookingDAO.getPendingBookings() + ","
        		+ "\"successfulBookings\":" + bookingDAO.getSuccessfulBookings() + ","
        		+ "\"totalRevenue\":" + bookingDAO.getTotalRevenue() + ","
        		+ "\"monthlyRevenue\":" + monthlyRevenue.toString() + ","
        		+ "\"monthlyBookings\":" + monthlyBookings.toString()
        		+ "}";
        response.getWriter().print(json);

    }

}