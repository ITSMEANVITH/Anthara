package com.eventbooking.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.eventbooking.dao.WishlistDAO;
import com.eventbooking.model.Event;
import com.eventbooking.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/api/wishlist")
public class ApiWishlistServlet extends HttpServlet {

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

            response.getWriter().write("[]");

            return;
        }

        User user = (User) session.getAttribute("user");

        if (user == null) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.getWriter().write("[]");

            return;
        }

        WishlistDAO dao = new WishlistDAO();

        ArrayList<Event> list =
                dao.getWishlist(user.getUserId());

        StringBuilder json = new StringBuilder();

        json.append("[");

        for (int i = 0; i < list.size(); i++) {

            Event e = list.get(i);

            json.append("{");

            json.append("\"eventId\":").append(e.getEventId()).append(",");

            json.append("\"title\":\"")
                .append(e.getEventName()).append("\",");

            json.append("\"venue\":\"")
                .append(e.getVenue()).append("\",");

            json.append("\"date\":\"")
                .append(e.getEventDate()).append("\",");

            json.append("\"time\":\"")
                .append(e.getEventTime() == null ? "" : e.getEventTime()).append("\",");

            json.append("\"price\":")
                .append(e.getTicketPrice()).append(",");

            json.append("\"image\":\"")
                .append(e.getEventImage() == null ? "" : e.getEventImage()).append("\"");

            json.append("}");

            if (i != list.size() - 1)
                json.append(",");
        }

        json.append("]");

        response.getWriter().write(json.toString());

    }
    

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
    	
    	System.out.println("METHOD = " + request.getMethod());
    	System.out.println("URI = " + request.getRequestURI());
    	System.out.println("Origin = " + request.getHeader("Origin"));
    	System.out.println("Cookie = " + request.getHeader("Cookie"));
    	
    	

    	System.out.println("===== WISHLIST =====");
    	System.out.println("Session = " + request.getSession(false));

    	HttpSession session = request.getSession(false);

    	if (session != null) {
    	    System.out.println("User = " + session.getAttribute("user"));
    	}

        if (session == null) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.getWriter().write("{\"success\":false}");

            return;

        }

        User user = (User) session.getAttribute("user");

        if (user == null) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.getWriter().write("{\"success\":false}");

            return;

        }

        int eventId =
                Integer.parseInt(request.getParameter("eventId"));

        String action =
                request.getParameter("action");

        WishlistDAO dao =
                new WishlistDAO();

        boolean status = false;

        if ("add".equals(action)) {

            status =
                    dao.addToWishlist(user.getUserId(), eventId);

        }

        else if ("remove".equals(action)) {

            status =
                    dao.removeFromWishlist(user.getUserId(), eventId);

        }

        response.getWriter().write(
                "{\"success\":" + status + "}");

    }

}