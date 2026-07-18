package com.eventbooking.controller;

import java.io.IOException;

import com.eventbooking.dao.PaymentDAO;
import com.eventbooking.model.Payment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        String method = request.getParameter("paymentMethod");

        Payment payment = new Payment();

        payment.setBookingId(bookingId);
        payment.setPaymentMethod(method);
        payment.setPaymentStatus("SUCCESS");

        PaymentDAO dao = new PaymentDAO();

        if (dao.savePayment(payment)) {

            response.sendRedirect("paymentSuccess.jsp");

        } else {

            response.sendRedirect("payment.jsp");

        }

    }
}