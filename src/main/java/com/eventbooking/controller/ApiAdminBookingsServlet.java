package com.eventbooking.controller;

import java.io.IOException;
import java.sql.ResultSet;

import com.eventbooking.dao.BookingDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/api/admin/bookings")
public class ApiAdminBookingsServlet extends HttpServlet {

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

        BookingDAO dao = new BookingDAO();

        ResultSet rs = dao.getBookingDetails();

        StringBuilder json = new StringBuilder();

        json.append("[");

        try {

            boolean first = true;

            while(rs.next()){

                if(!first){

                    json.append(",");

                }

                json.append("{");

                json.append("\"bookingId\":").append(rs.getInt("booking_id")).append(",");

                json.append("\"user\":\"").append(rs.getString("full_name")).append("\",");

                json.append("\"event\":\"").append(rs.getString("event_name")).append("\",");

                json.append("\"tickets\":").append(rs.getInt("tickets")).append(",");

                json.append("\"amount\":").append(rs.getDouble("total_amount")).append(",");

                json.append("\"date\":\"").append(rs.getString("booking_date")).append("\",");

                json.append("\"paymentMethod\":\"").append(rs.getString("payment_method")).append("\",");

                json.append("\"paymentStatus\":\"").append(rs.getString("payment_status")).append("\"");

                json.append("}");

                first = false;

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        json.append("]");

        response.getWriter().print(json.toString());

    }

}