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

@WebServlet("/api/profile/update")
public class ApiUpdateProfileServlet extends HttpServlet {

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

            response.getWriter().write("{\"success\":false}");

            return;

        }

        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser == null) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.getWriter().write("{\"success\":false}");

            return;

        }

        User user = new User();

        user.setUserId(sessionUser.getUserId());
        user.setFullName(request.getParameter("fullName"));
        user.setPhone(request.getParameter("phone"));
        user.setProfileImage(sessionUser.getProfileImage());

        UserDAO dao = new UserDAO();

        boolean status = dao.updateProfile(user);

        if (status) {

            sessionUser.setFullName(user.getFullName());
            sessionUser.setPhone(user.getPhone());

            session.setAttribute("user", sessionUser);

            response.getWriter().write("{\"success\":true}");

        } else {

            response.getWriter().write("{\"success\":false}");

        }

    }

}