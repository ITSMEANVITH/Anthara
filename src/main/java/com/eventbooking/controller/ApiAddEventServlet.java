package com.eventbooking.controller;

import java.io.IOException;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;

import com.eventbooking.dao.EventDAO;
import com.eventbooking.model.Event;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/api/admin/addEvent")
@MultipartConfig
public class ApiAddEventServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("admin") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Event event = new Event();

        Part imagePart = request.getPart("image");

        String imageName = "";

        if (imagePart != null && imagePart.getSize() > 0) {

            imageName = imagePart.getSubmittedFileName();

            String uploadPath =
                    getServletContext().getRealPath("") + "images";

            java.io.File uploadDir = new java.io.File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            imagePart.write(uploadPath + java.io.File.separator + imageName);
        }

        event.setEventName(request.getParameter("eventName"));
        event.setEventDate(request.getParameter("eventDate"));
        event.setEventTime(request.getParameter("eventTime"));
        event.setVenue(request.getParameter("venue"));

        event.setTicketPrice(
                Double.parseDouble(request.getParameter("ticketPrice"))
        );

        event.setAvailableSeats(
                Integer.parseInt(request.getParameter("availableSeats"))
        );

        event.setCategory(request.getParameter("category"));

        event.setBookingOpen(
                Boolean.parseBoolean(request.getParameter("bookingOpen"))
        );

        // NEW
        event.setHomeSection(
                request.getParameter("homeSection")
        );

        event.setEventImage(imageName);

        EventDAO dao = new EventDAO();

        System.out.println("===== ADD EVENT =====");
        System.out.println("Name = " + event.getEventName());
        System.out.println("Date = " + event.getEventDate());
        System.out.println("Time = " + event.getEventTime());
        System.out.println("Venue = " + event.getVenue());
        System.out.println("Price = " + event.getTicketPrice());
        System.out.println("Seats = " + event.getAvailableSeats());
        System.out.println("Category = " + event.getCategory());
        System.out.println("Booking Open = " + event.isBookingOpen());
        System.out.println("Home Section = " + event.getHomeSection());
        System.out.println("Image = " + event.getEventImage());

        String uploadPath =
                getServletContext().getRealPath("") + "images";

        System.out.println("UPLOAD PATH = " + uploadPath);
        System.out.println("IMAGE NAME = " + imageName);

        boolean status = dao.addEvent(event);

        response.setContentType("application/json");

        if (status) {
            response.getWriter().print("{\"success\":true}");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("{\"success\":false}");
        }
    }
}