package com.eventbooking.controller;

import java.io.IOException;

import com.eventbooking.dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/api/resetPassword")
public class ApiResetPasswordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.getWriter().write(
                "{\"success\":false,\"message\":\"Session Expired\"}"
            );

            return;
        }

        String email = (String) session.getAttribute("resetEmail");

        String password = request.getParameter("password");

        String confirmPassword =
                request.getParameter("confirmPassword");

        if (!password.equals(confirmPassword)) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            response.getWriter().write(
                "{\"success\":false,\"message\":\"Passwords do not match\"}"
            );

            return;

        }

        UserDAO dao = new UserDAO();

        boolean status = dao.updatePassword(email, password);

        if (status) {

            session.removeAttribute("otp");
            session.removeAttribute("resetEmail");

            response.getWriter().write(
                "{\"success\":true}"
            );

        } else {

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            response.getWriter().write(
                "{\"success\":false,\"message\":\"Unable to reset password\"}"
            );

        }

    }

}