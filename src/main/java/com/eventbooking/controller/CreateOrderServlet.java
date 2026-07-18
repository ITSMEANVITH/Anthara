package com.eventbooking.controller;

import java.io.IOException;

import org.json.JSONObject;

import com.eventbooking.util.RazorpayConfig;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import com.eventbooking.dao.BookingDAO;
import com.eventbooking.model.Booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CreateOrderServlet")
public class CreateOrderServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        try {

            // Amount received from payment.jsp
        	int bookingId = Integer.parseInt(request.getParameter("bookingId"));

        	BookingDAO bookingDAO = new BookingDAO();

        	Booking booking = bookingDAO.getBookingById(bookingId);

        	if (booking == null) {

        	    JSONObject error = new JSONObject();

        	    error.put("status", "failed");
        	    error.put("message", "Booking not found");

        	    response.getWriter().print(error.toString());
        	    return;
        	}

        	double amount = booking.getTotalAmount();

        	int amountInPaise = (int)(amount * 100);
            RazorpayClient client = new RazorpayClient(
                    RazorpayConfig.KEY_ID,
                    RazorpayConfig.KEY_SECRET);

            JSONObject orderRequest = new JSONObject();

            orderRequest.put("amount", amountInPaise);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "receipt_" + System.currentTimeMillis());
            orderRequest.put("payment_capture", 1);

            Order order = client.orders.create(orderRequest);
            
            JSONObject responseJson = new JSONObject();

            responseJson.put("orderId", order.get("id").toString());
            responseJson.put("amount", Integer.parseInt(order.get("amount").toString()));
            responseJson.put("currency", order.get("currency").toString());
            responseJson.put("key", RazorpayConfig.KEY_ID);

            response.getWriter().print(responseJson.toString());

            

        } catch (Exception e) {

            e.printStackTrace();

            JSONObject error = new JSONObject();

            error.put("status", "failed");
            error.put("message", e.getMessage());

            response.getWriter().print(error.toString());

        }

    }

}