package com.eventbooking.controller;

import java.io.File;
import java.io.IOException;

import com.eventbooking.dao.EventDAO;
import com.eventbooking.model.Event;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/api/admin/updateEvent")
@MultipartConfig
public class ApiUpdateEventServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("admin") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        int eventId = Integer.parseInt(request.getParameter("eventId"));

        EventDAO dao = new EventDAO();

        // Load the existing row first so any field not present on the
        // request (in particular the image) doesn't get wiped out below.
        Event oldEvent = dao.getEventById(eventId);
        if (oldEvent == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\":false,\"message\":\"Event not found\"}");
            return;
        }

        Event event = oldEvent;

        event.setEventId(eventId);
        event.setEventName(request.getParameter("eventName"));
        event.setEventDate(request.getParameter("eventDate"));
        event.setEventTime(request.getParameter("eventTime"));
        event.setVenue(request.getParameter("venue"));
        event.setTicketPrice(Double.parseDouble(request.getParameter("ticketPrice")));
        event.setAvailableSeats(Integer.parseInt(request.getParameter("availableSeats")));

        // These three were previously never read — this was the actual bug.
        String category = request.getParameter("category");
        if (category != null) {
            event.setCategory(category);
        }

        String bookingOpenParam = request.getParameter("bookingOpen");
        if (bookingOpenParam != null) {
            event.setBookingOpen(Boolean.parseBoolean(bookingOpenParam));
        }

        String homeSection = request.getParameter("homeSection");
        if (homeSection != null) {
            event.setHomeSection(homeSection);
        }

        // ---------- Image Upload ----------
        Part imagePart = request.getPart("image");

        if (imagePart != null && imagePart.getSize() > 0) {

            String imageName = imagePart.getSubmittedFileName();

            String uploadPath = getServletContext().getRealPath("/images");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            imagePart.write(uploadPath + File.separator + imageName);
            event.setEventImage(imageName);
        }
        // else: event.getEventImage() keeps the value loaded from oldEvent above.

        boolean status = dao.updateEvent(event);

        if (status) {
            response.getWriter().write("{\"success\":true}");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\":false}");
        }
    }
}