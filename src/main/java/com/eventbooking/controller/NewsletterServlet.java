package com.eventbooking.controller;

import com.eventbooking.dao.NewsletterDAO;
import com.eventbooking.util.EmailUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/newsletter")
public class NewsletterServlet extends HttpServlet {
	
	

    private static final long serialVersionUID = 1L;
	
	
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        String email = request.getParameter("email");

        if (email == null || email.trim().isEmpty()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            response.getWriter().write(
                "{\"success\":false,\"message\":\"Email is required\"}"
            );

            return;
        }

        try {

        	NewsletterDAO dao = new NewsletterDAO();

        	boolean subscribed = dao.subscribe(email);

        	if (!subscribed) {

        	    response.setStatus(HttpServletResponse.SC_CONFLICT);

        	    response.getWriter().write(
        	        "{\"success\":false,\"message\":\"Email is already subscribed.\"}"
        	    );

        	    return;

        	}

        	String subject = "Welcome to Anthara!";

            String body =
                    "<div style='font-family:Arial;padding:25px;'>"

                    + "<h2 style='color:#7C3AED;'>Welcome to Anthara 🎉</h2>"

                    + "<p>Thank you for subscribing to <b>Anthara</b>.</p>"

                    + "<p>You will now receive:</p>"

                    + "<ul>"

                    + "<li> New Event Announcements</li>"

                    + "<li> Exclusive Offers</li>"

                    + "<li> Festival Updates</li>"

                    + "<li> Early Bird Discounts</li>"

                    + "</ul>"

                    + "<p>We're excited to have you with us!</p>"

                    + "<br>"

                    + "<b>Team Anthara</b>"

                    + "</div>";

            EmailUtil.sendEmail(email, subject, body);

            response.getWriter().write(
                "{\"success\":true,\"message\":\"Subscribed Successfully\"}"
            );

        }
        catch (Exception e) {

            e.printStackTrace();

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            response.getWriter().write(
                "{\"success\":false,\"message\":\"Subscription Failed\"}"
            );

        }

    }

}