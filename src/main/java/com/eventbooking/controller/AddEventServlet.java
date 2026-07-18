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

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
@WebServlet("/AddEventServlet")
public class AddEventServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String eventName = request.getParameter("eventName");
        String eventDate = request.getParameter("eventDate");
        String eventTime = request.getParameter("eventTime");
        String venue = request.getParameter("venue");
        double ticketPrice = Double.parseDouble(request.getParameter("ticketPrice"));
        int availableSeats = Integer.parseInt(request.getParameter("availableSeats"));
        String category = request.getParameter("category");
        boolean bookingOpen = Boolean.parseBoolean(request.getParameter("bookingOpen"));
        String homeSection = request.getParameter("homeSection");

        // Upload Image — the frontend sends the file under the field name "image"
        Part filePart = request.getPart("image");
        String fileName = null;

        if (filePart != null && filePart.getSize() > 0) {

            fileName = filePart.getSubmittedFileName();

            // Use the same "images" folder the edit form and homepage read from
            // (${API_BASE_URL}/Anthara/images/...), not "eventImages".
            String uploadPath = getServletContext().getRealPath("/images");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            filePart.write(uploadPath + File.separator + fileName);
        }

        Event event = new Event();
        event.setEventName(eventName);
        event.setEventDate(eventDate);
        event.setEventTime(eventTime);
        event.setVenue(venue);
        event.setTicketPrice(ticketPrice);
        event.setAvailableSeats(availableSeats);
        event.setCategory(category);
        event.setBookingOpen(bookingOpen);
        event.setHomeSection(homeSection);
        event.setEventImage(fileName); // stays null if no image was uploaded

        EventDAO dao = new EventDAO();
        if (dao.addEvent(event)) {
            response.sendRedirect("manageEvents.jsp");
        } else {
            response.sendRedirect("addEvent.jsp");
        }
    }
}