package com.eventbooking.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.eventbooking.dao.EventDAO;
import com.eventbooking.model.Event;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");

        String category = request.getParameter("category");

        if (keyword == null)
            keyword = "";

        if (category == null)
            category = "";

        EventDAO dao = new EventDAO();

        ArrayList<Event> events =
                dao.searchEvents(keyword, category);

        request.setAttribute("events", events);

        request.getRequestDispatcher("events.jsp")
               .forward(request, response);

    }

}