package com.eventbooking.controller;

import java.io.IOException;

import org.json.JSONObject;

import com.eventbooking.dao.PaymentDAO;
import com.eventbooking.model.Payment;
import com.razorpay.Utils;

import com.eventbooking.util.RazorpayConfig;

import com.razorpay.RazorpayClient;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.eventbooking.dao.BookingDAO;
import com.eventbooking.dao.EventDAO;
import com.eventbooking.dao.UserDAO;

import com.eventbooking.model.Booking;
import com.eventbooking.model.Event;
import com.eventbooking.model.User;

import com.eventbooking.util.EmailUtil;

import com.eventbooking.util.TicketPDFUtil;

import com.eventbooking.util.CalendarUtil;

@WebServlet("/PaymentSuccessServlet")
public class PaymentSuccessServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int bookingId =
                    Integer.parseInt(request.getParameter("bookingId"));

            String razorpayPaymentId =
                    request.getParameter("razorpay_payment_id");

            String razorpayOrderId =
                    request.getParameter("razorpay_order_id");

            String razorpaySignature =
                    request.getParameter("razorpay_signature");

            JSONObject options = new JSONObject();

            options.put("razorpay_order_id", razorpayOrderId);
            options.put("razorpay_payment_id", razorpayPaymentId);
            options.put("razorpay_signature", razorpaySignature);

            boolean verified =
                    Utils.verifyPaymentSignature(
                            options,
                            com.eventbooking.util.RazorpayConfig.KEY_SECRET);

            if (!verified) {

                response.sendRedirect("paymentFailed.jsp");
                return;

            }
            
            RazorpayClient client =
                    new RazorpayClient(
                            RazorpayConfig.KEY_ID,
                            RazorpayConfig.KEY_SECRET);

            com.razorpay.Payment razorpayPayment =
                    client.payments.fetch(razorpayPaymentId);
            

            String paymentMethod =
                    razorpayPayment.get("method");
            
           

            Payment payment = new Payment();

            payment.setBookingId(bookingId);
            payment.setPaymentMethod(paymentMethod.toUpperCase());
            payment.setPaymentStatus("SUCCESS");

            payment.setRazorpayOrderId(razorpayOrderId);
            payment.setRazorpayPaymentId(razorpayPaymentId);
            payment.setRazorpaySignature(razorpaySignature);

            PaymentDAO dao = new PaymentDAO();

            if (dao.savePayment(payment)) {

                try {

                    BookingDAO bookingDAO = new BookingDAO();

                    Booking booking = bookingDAO.getBookingById(bookingId);

                    if (booking != null) {

                        UserDAO userDAO = new UserDAO();

                        User user = userDAO.getUserById(booking.getUserId());

                        EventDAO eventDAO = new EventDAO();
                        
                       

                        Event event = eventDAO.getEventById(booking.getEventId());
                        
                        System.out.println("Booking Event ID : " + booking.getEventId());

                        System.out.println("Event Name : " + event.getEventName());

                        System.out.println("Event Date : " + event.getEventDate());

                        System.out.println("Event Time : " + event.getEventTime());

                        if (user != null && event != null) {

                            String body =
                            "<div style='font-family:Arial,sans-serif;'>"

                            + "<h2 style='color:green;'> Booking Confirmed!</h2>"

                            + "<p>Hello <b>" + user.getFullName() + "</b>,</p>"

                            + "<p>Your booking has been confirmed successfully.</p>"

                            + "<hr>"

                            + "<table style='border-collapse:collapse;'>"

                            + "<tr><td><b>Booking ID</b></td><td>&nbsp;&nbsp;" + booking.getBookingId() + "</td></tr>"

                            + "<tr><td><b>Event</b></td><td>&nbsp;&nbsp;" + event.getEventName() + "</td></tr>"

                            + "<tr><td><b>Date</b></td><td>&nbsp;&nbsp;" + event.getEventDate() + "</td></tr>"

                            + "<tr><td><b>Venue</b></td><td>&nbsp;&nbsp;" + event.getVenue() + "</td></tr>"

                            + "<tr><td><b>Tickets</b></td><td>&nbsp;&nbsp;" + booking.getTickets() + "</td></tr>"

                            + "<tr><td><b>Total Paid</b></td><td>&nbsp;&nbsp;Rs" + booking.getTotalAmount() + "</td></tr>"

                            + "</table>"

                            + "<br>"

                            + "<span style='color:green;font-weight:bold;'>Payment Successful </span>"

                            + "<hr>"

                            + "<p>Thank you for choosing <b>Anthara</b>.</p>"

                            + "<p>We look forward to seeing you at the event.</p>"

                            + "<br>"

                            + "<b>Team Anthara </b>"

                            + "</div>";

                            byte[] pdf =
                                    TicketPDFUtil.generateTicket(
                                            booking,
                                            event,
                                            user);

                            byte[] calendar =
                                    CalendarUtil.generateCalendarInvite(
                                            event);

                            EmailUtil.sendEmailWithAttachment(

                                    user.getEmail(),

                                    "🎟 Booking Confirmed - Anthara",

                                    body,

                                    pdf,

                                    calendar

                            );

                            System.out.println("Email method completed.");
                        }
                        
                        

                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }

                response.sendRedirect("paymentSuccess.jsp?bookingId=" + bookingId);

            } else {

                response.sendRedirect("paymentFailed.jsp");

            }

        }

        catch(Exception e) {

            e.printStackTrace();

            response.sendRedirect("paymentFailed.jsp");

        }
        

    }
    

}