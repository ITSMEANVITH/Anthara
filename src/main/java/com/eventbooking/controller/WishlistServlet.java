package com.eventbooking.controller;

import java.io.IOException;

import com.eventbooking.dao.WishlistDAO;
import com.eventbooking.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/WishlistServlet")
public class WishlistServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if(session == null){

            response.sendRedirect("login.jsp");

            return;

        }

        User user = (User) session.getAttribute("user");

        if(user == null){

            response.sendRedirect("login.jsp");

            return;

        }

        int eventId = Integer.parseInt(request.getParameter("eventId"));

        String action = request.getParameter("action");

        WishlistDAO dao = new WishlistDAO();

        if("add".equals(action)){

            dao.addToWishlist(user.getUserId(), eventId);

        }

        else if("remove".equals(action)){

            dao.removeFromWishlist(user.getUserId(), eventId);

        }

        response.sendRedirect("events.jsp");

    }

}