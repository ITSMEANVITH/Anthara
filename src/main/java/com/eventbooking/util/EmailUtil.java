package com.eventbooking.util;

import java.util.Properties;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.activation.DataHandler;
import jakarta.mail.util.ByteArrayDataSource;

public class EmailUtil {

    private static final String FROM = "antharaevents@gmail.com";
    private static final String PASSWORD = "trig lhrl vhpz emvf";

    public static void sendEmail(String to,
                                 String subject,
                                 String body) throws Exception {

        Properties props = new Properties();

        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.port","587");

        Session session = Session.getInstance(props,
            new Authenticator() {

                protected PasswordAuthentication getPasswordAuthentication() {

                    return new PasswordAuthentication(FROM,PASSWORD);

                }

            });

        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress(FROM));

        message.setRecipients(
            Message.RecipientType.TO,
            InternetAddress.parse(to));

        message.setSubject(subject);

        message.setContent(body, "text/html");

        Transport.send(message);

    }
    public static void sendEmailWithAttachment(
            String to,
            String subject,
            String body,
            byte[] pdfBytes,
            byte[] calendarBytes) throws Exception {

        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {

                    protected PasswordAuthentication getPasswordAuthentication() {

                        return new PasswordAuthentication(FROM, PASSWORD);

                    }

                });

        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(FROM));

        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(to));

        message.setSubject(subject);

        MimeBodyPart htmlPart = new MimeBodyPart();

        htmlPart.setContent(body, "text/html");

        MimeBodyPart pdfPart = new MimeBodyPart();

        pdfPart.setFileName("Anthara_Ticket.pdf");

        pdfPart.setDataHandler(

                new DataHandler(

                        new ByteArrayDataSource(

                                pdfBytes,

                                "application/pdf"

                        )

                )

        );

        MimeBodyPart calendarPart = new MimeBodyPart();

        calendarPart.setFileName("Anthara_Event.ics");

        calendarPart.setDataHandler(

                new DataHandler(

                        new ByteArrayDataSource(

                                calendarBytes,

                                "text/calendar"

                        )

                )

        );

        Multipart multipart = new MimeMultipart();

        multipart.addBodyPart(htmlPart);

        multipart.addBodyPart(pdfPart);

        multipart.addBodyPart(calendarPart);

        message.setContent(multipart);

        Transport.send(message);

    }
    public static void sendCancellationEmail(
            String to,
            String userName,
            String eventName,
            int bookingId,
            int tickets,
            double amount) throws Exception {

        String subject = "Booking Cancelled | Anthara";

        String body =
            "<div style='font-family:Arial,sans-serif;padding:20px;'>"

            + "<h2 style='color:#dc3545;'> Booking Cancelled</h2>"

            + "<p>Dear <b>" + userName + "</b>,</p>"

            + "<p>Your booking has been cancelled successfully.</p>"

            + "<hr>"

            + "<h3>Booking Details</h3>"

            + "<table style='border-collapse:collapse;'>"

            + "<tr><td><b>Booking ID</b></td><td>: " + bookingId + "</td></tr>"

            + "<tr><td><b>Event</b></td><td>: " + eventName + "</td></tr>"

            + "<tr><td><b>Tickets</b></td><td>: " + tickets + "</td></tr>"

            + "<tr><td><b>Total Amount</b></td><td>: Rs" + amount + "</td></tr>"

            + "<tr><td><b>Status</b></td><td>: <span style='color:red;font-weight:bold;'>CANCELLED</span></td></tr>"

            + "</table>"

            + "<br>"

            + "<p>If this cancellation was not made by you, please contact Anthara Support immediately.</p>"

            + "<br>"

            + "<p>Thank you for choosing <b>Anthara</b>.</p>"

            + "</div>";

        sendEmail(to, subject, body);
    }
    

}