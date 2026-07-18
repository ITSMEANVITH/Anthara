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
import jakarta.servlet.http.Part;

@WebServlet("/UpdateEventServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class UpdateEventServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int eventId = Integer.parseInt(request.getParameter("eventId"));

        String eventName = request.getParameter("eventName");
        String eventDate = request.getParameter("eventDate");
        String eventTime = request.getParameter("eventTime");
        String venue = request.getParameter("venue");
        double ticketPrice = Double.parseDouble(request.getParameter("ticketPrice"));
        int availableSeats = Integer.parseInt(request.getParameter("availableSeats"));
        String category = request.getParameter("category");
        boolean bookingOpen = Boolean.parseBoolean(request.getParameter("bookingOpen"));
        String homeSection = request.getParameter("homeSection");

        // ---- TEMP DEBUG: remove once confirmed working ----
        System.out.println("========== UPDATE EVENT SERVLET ==========");
        System.out.println("eventId       = " + eventId);
        System.out.println("homeSection   = [" + homeSection + "]");
        System.out.println("bookingOpen   = [" + request.getParameter("bookingOpen") + "]");
        System.out.println("category      = [" + category + "]");
        System.out.println("===========================================");
        // ----------------------------------------------------

        EventDAO dao = new EventDAO();

        // Load existing event first so fields the client didn't resend
        // (in particular the image, when no new file was chosen) aren't wiped out.
        Event event = dao.getEventById(eventId);
        if (event == null) {
            event = new Event();
            event.setEventId(eventId);
        }

        event.setEventName(eventName);
        event.setEventDate(eventDate);
        event.setEventTime(eventTime);
        event.setVenue(venue);
        event.setTicketPrice(ticketPrice);
        event.setAvailableSeats(availableSeats);
        event.setCategory(category);
        event.setBookingOpen(bookingOpen);
        event.setHomeSection(homeSection);

        // Only overwrite the stored image if a new file was actually uploaded.
        Part filePart = request.getPart("image");
        if (filePart != null && filePart.getSize() > 0) {

            String fileName = filePart.getSubmittedFileName();
            String uploadPath = getServletContext().getRealPath("/images");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            filePart.write(uploadPath + File.separator + fileName);

            event.setEventImage(fileName);
        }
        // else: event.getEventImage() keeps whatever was loaded from the DB above.

        boolean updated = dao.updateEvent(event);

        // ---- TEMP DEBUG: remove once confirmed working ----
        System.out.println("updateEvent() returned = " + updated);
        System.out.println("event.getHomeSection() after set = [" + event.getHomeSection() + "]");
        // ----------------------------------------------------

        if (updated) {
            response.sendRedirect("manageEvents.jsp");
        } else {
            response.sendRedirect("editEvent.jsp?id=" + eventId);
        }
    }
}