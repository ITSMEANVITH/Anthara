package com.eventbooking.controller;

import java.io.IOException;

import com.eventbooking.dao.ReviewDAO;
import com.eventbooking.model.Review;
import com.eventbooking.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ReviewServlet")
public class ReviewServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if(session==null){

            response.sendRedirect("login.jsp");

            return;

        }

        User user=(User)session.getAttribute("user");

        if(user==null){

            response.sendRedirect("login.jsp");

            return;

        }

        int eventId=Integer.parseInt(request.getParameter("eventId"));

        int rating=Integer.parseInt(request.getParameter("rating"));

        String reviewText=request.getParameter("review");

        Review review=new Review();

        review.setUserId(user.getUserId());
        review.setEventId(eventId);
        review.setRating(rating);
        review.setReview(reviewText);

        ReviewDAO dao=new ReviewDAO();

        dao.addReview(review);

        response.sendRedirect("review.jsp?eventId="+eventId);

    }

}