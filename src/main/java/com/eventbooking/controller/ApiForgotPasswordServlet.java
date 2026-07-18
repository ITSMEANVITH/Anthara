package com.eventbooking.controller;

import java.io.IOException;
import java.util.Random;

import com.eventbooking.dao.UserDAO;
import com.eventbooking.model.User;
import com.eventbooking.util.EmailUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/api/forgotPassword")
public class ApiForgotPasswordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");

        UserDAO dao = new UserDAO();

        User user = dao.getUserByEmail(email);

        if (user == null) {

            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

            response.getWriter().write(
                "{\"success\":false,\"message\":\"Email not found\"}"
            );

            return;
        }

        int otp = 100000 + new Random().nextInt(900000);

        HttpSession session = request.getSession();

        session.setAttribute("otp", String.valueOf(otp));
        session.setAttribute("resetEmail", email);

        try {

            EmailUtil.sendEmail(
                    email,
                    "Anthara Password Reset OTP",
                    "<h2>Your OTP is : <b>" + otp + "</b></h2>"
                    + "<p>This OTP is valid for password reset.</p>");

            response.getWriter().write(
                "{\"success\":true}"
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            response.getWriter().write(
                "{\"success\":false,\"message\":\"Unable to send OTP\"}"
            );

        }

    }

}