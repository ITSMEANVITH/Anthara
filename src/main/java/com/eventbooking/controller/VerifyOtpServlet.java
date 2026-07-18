package com.eventbooking.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/VerifyOtpServlet")
public class VerifyOtpServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if(session == null){

            response.sendRedirect("forgotPassword.jsp");

            return;

        }

        String userOtp = request.getParameter("otp");

        String sessionOtp = (String) session.getAttribute("otp");

        if(sessionOtp != null && sessionOtp.equals(userOtp)){

            response.sendRedirect("resetPassword.jsp");

        }else{

            response.sendRedirect("verifyOtp.jsp?error=1");

        }

    }

}