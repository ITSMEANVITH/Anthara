package com.eventbooking.controller;

import java.io.IOException;

import com.eventbooking.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/api/profile")
public class ApiProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.getWriter().write(
                    "{\"success\":false,\"message\":\"Not Logged In\"}");

            return;
        }

        User user = (User) session.getAttribute("user");

        if (user == null) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.getWriter().write(
                    "{\"success\":false,\"message\":\"Not Logged In\"}");

            return;
        }

        String json = String.format(
                "{\"success\":true,\"user\":{\"userId\":%d,\"fullName\":\"%s\",\"email\":\"%s\",\"phone\":\"%s\",\"profileImage\":\"%s\"}}",
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone() == null ? "" : user.getPhone(),
                user.getProfileImage() == null ? "" : user.getProfileImage());

        response.getWriter().write(json);

    }
}