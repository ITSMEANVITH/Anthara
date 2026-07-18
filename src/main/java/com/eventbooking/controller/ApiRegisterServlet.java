package com.eventbooking.controller;

import java.io.IOException;

import com.eventbooking.dao.UserDAO;
import com.eventbooking.model.User;
import com.eventbooking.util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/register")
public class ApiRegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        User user = new User();

        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(PasswordUtil.hashPassword(password));

        UserDAO dao = new UserDAO();

        response.setContentType("application/json");

        if (dao.registerUser(user)) {

            response.getWriter().write(
                "{\"success\":true,\"message\":\"Registration Successful\"}"
            );

        } else {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            response.getWriter().write(
                "{\"success\":false,\"message\":\"Registration Failed\"}"
            );

        }
    }
}