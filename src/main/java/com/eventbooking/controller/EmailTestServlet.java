package com.eventbooking.controller;

import java.io.IOException;

import com.eventbooking.util.EmailUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EmailTestServlet")
public class EmailTestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            EmailUtil.sendEmail(

                "anvithcd_csd@ksit.edu.in",

                "Anthara Email Test",

                "<h2>🎉 Anthara is Working!</h2>"
                + "<p>Your email service is successfully configured.</p>"
                + "<br>"
                + "<h3>Regards,</h3>"
                + "<b>Anthara Team</b>"

            );

            response.getWriter().println("Email Sent Successfully!");

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(e.getMessage());

        }

    }

}