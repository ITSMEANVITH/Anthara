package com.eventbooking.controller;

import java.io.IOException;

import com.eventbooking.dao.UserDAO;
import com.eventbooking.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/api/login")
public class ApiLoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        System.out.println("EMAIL = " + email);
        System.out.println("PASSWORD = " + password);

        UserDAO dao = new UserDAO();
        User user = dao.loginUser(email, password);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (user != null) {

            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            String json = String.format(
                "{\"success\":true,\"user\":{\"userId\":%d,\"fullName\":\"%s\",\"email\":\"%s\",\"phone\":\"%s\",\"profileImage\":\"%s\"}}",
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone() == null ? "" : user.getPhone(),
                user.getProfileImage() == null ? "" : user.getProfileImage()
            );

            response.getWriter().write(json);

        } else {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(
                "{\"success\":false,\"message\":\"Invalid email or password\"}"
            );

        }
    }
}