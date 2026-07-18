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

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        String hashedPassword = PasswordUtil.hashPassword(password);

        User user = new User();

        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(hashedPassword);

        UserDAO dao = new UserDAO();

        if (dao.registerUser(user)) {

            response.sendRedirect("login.jsp");

        } else {

            response.getWriter().println("<h2>Registration Failed!</h2>");

        }
    }
}