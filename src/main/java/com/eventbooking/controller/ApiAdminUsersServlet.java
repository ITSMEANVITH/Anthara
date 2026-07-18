package com.eventbooking.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.eventbooking.dao.UserDAO;
import com.eventbooking.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/api/admin/users")
public class ApiAdminUsersServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if(session==null || session.getAttribute("admin")==null){

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            return;

        }

        UserDAO dao = new UserDAO();

        ArrayList<User> users = dao.getAllUsers();

        StringBuilder json = new StringBuilder();

        json.append("[");

        for(int i=0;i<users.size();i++){

            User u = users.get(i);

            json.append("{");

            json.append("\"userId\":").append(u.getUserId()).append(",");

            json.append("\"fullName\":\"").append(u.getFullName()).append("\",");

            json.append("\"email\":\"").append(u.getEmail()).append("\",");

            json.append("\"phone\":\"").append(u.getPhone()).append("\"");

            json.append("}");

            if(i<users.size()-1){

                json.append(",");

            }

        }

        json.append("]");

        response.getWriter().print(json.toString());

    }

}