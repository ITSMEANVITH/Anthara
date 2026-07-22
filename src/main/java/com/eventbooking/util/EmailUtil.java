package com.eventbooking.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class EmailUtil {

    // Set these as environment variables on Railway:
    //   BREVO_API_KEY   -> your Brevo API key
    //   FROM_EMAIL      -> the single sender email you verified in Brevo
    private static final String API_KEY = System.getenv("BREVO_API_KEY");
    private static final String FROM = System.getenv().getOrDefault("FROM_EMAIL", "onboarding@resend.dev");
    private static final String FROM_NAME = "Anthara";

    private static String escapeJson(String text) {
        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "")
                .replace("\n", "\\n");
    }

    private static HttpResponse<String> post(String json) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.brevo.com/v3/smtp/email"))
                .header("api-key", API_KEY)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Brevo Response: " + response.body());

        if (response.statusCode() >= 300) {
            throw new RuntimeException(response.body());
        }
        return response;
    }

    public static void sendEmail(String to, String subject, String body) throws Exception {

        String json = String.format("""
        {
          "sender": {"email":"%s","name":"%s"},
          "to": [{"email":"%s"}],
          "subject":"%s",
          "htmlContent":"%s"
        }
        """,
                FROM,
                FROM_NAME,
                to,
                escapeJson(subject),
                escapeJson(body));

        post(json);
    }

    public static void sendEmailWithAttachment(
            String to,
            String subject,
            String body,
            byte[] pdfBytes,
            byte[] calendarBytes) throws Exception {

        String pdfBase64 = Base64.getEncoder().encodeToString(pdfBytes);
        String icsBase64 = Base64.getEncoder().encodeToString(calendarBytes);

        String json = String.format("""
        {
          "sender": {"email":"%s","name":"%s"},
          "to": [{"email":"%s"}],
          "subject":"%s",
          "htmlContent":"%s",
          "attachment": [
            {
              "name":"Anthara_Ticket.pdf",
              "content":"%s"
            },
            {
              "name":"Anthara_Event.ics",
              "content":"%s"
            }
          ]
        }
        """,
                FROM,
                FROM_NAME,
                to,
                escapeJson(subject),
                escapeJson(body),
                pdfBase64,
                icsBase64);

        post(json);
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
                        + "<h2 style='color:#dc3545;'>Booking Cancelled</h2>"
                        + "<p>Dear <b>" + userName + "</b>,</p>"
                        + "<p>Your booking has been cancelled successfully.</p>"
                        + "<hr>"
                        + "<h3>Booking Details</h3>"
                        + "<table style='border-collapse:collapse;'>"
                        + "<tr><td><b>Booking ID</b></td><td>: " + bookingId + "</td></tr>"
                        + "<tr><td><b>Event</b></td><td>: " + eventName + "</td></tr>"
                        + "<tr><td><b>Tickets</b></td><td>: " + tickets + "</td></tr>"
                        + "<tr><td><b>Total Amount</b></td><td>: ₹" + amount + "</td></tr>"
                        + "<tr><td><b>Status</b></td><td><span style='color:red;font-weight:bold;'>CANCELLED</span></td></tr>"
                        + "</table>"
                        + "<br>"
                        + "<p>If this cancellation was not made by you, please contact Anthara Support immediately.</p>"
                        + "<br>"
                        + "<p>Thank you for choosing <b>Anthara</b>.</p>"
                        + "</div>";

        sendEmail(to, subject, body);
    }
}
