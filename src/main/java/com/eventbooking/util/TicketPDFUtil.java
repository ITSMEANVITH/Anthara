package com.eventbooking.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import com.eventbooking.model.Booking;
import com.eventbooking.model.Event;
import com.eventbooking.model.User;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class TicketPDFUtil {

    public static byte[] generateTicket(

            Booking booking,

            Event event,

            User user

    ) throws Exception {

        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        String qrData =
                "http://localhost:8080/Anthara/VerifyTicketServlet?bookingId="
                + booking.getBookingId();

        BitMatrix matrix =
                new MultiFormatWriter().encode(
                        qrData,
                        BarcodeFormat.QR_CODE,
                        220,
                        220);

        BufferedImage qrImage =
                MatrixToImageWriter.toBufferedImage(matrix);

        ByteArrayOutputStream qrOutput =
                new ByteArrayOutputStream();

        ImageIO.write(qrImage, "png", qrOutput);

        Image qr =
                Image.getInstance(qrOutput.toByteArray());

        qr.scaleAbsolute(150,150);

        qr.setAlignment(Image.ALIGN_CENTER);

        Document document = new Document();

        PdfWriter.getInstance(document, output);

        document.open();

        Font title =
                new Font(
                        Font.FontFamily.HELVETICA,
                        24,
                        Font.BOLD,
                        BaseColor.BLUE);

        Font heading =
                new Font(
                        Font.FontFamily.HELVETICA,
                        16,
                        Font.BOLD);

        Font normal =
                new Font(
                        Font.FontFamily.HELVETICA,
                        13);

        Font green =
                new Font(
                        Font.FontFamily.HELVETICA,
                        14,
                        Font.BOLD,
                        BaseColor.GREEN);
        document.add(new Paragraph(" "));
        document.add(new Paragraph("ANTHARA", title));
        document.add(new Paragraph("EVENT TICKET", heading));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("=============================================="));

        document.add(new Paragraph("Booking ID : " + booking.getBookingId(), normal));

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Customer", heading));
        document.add(new Paragraph(user.getFullName(), normal));

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Event Name", heading));
        document.add(new Paragraph(event.getEventName(), normal));

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Venue", heading));
        document.add(new Paragraph(event.getVenue(), normal));

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Event Date", heading));
        document.add(new Paragraph(event.getEventDate(), normal));

        document.add(new Paragraph(" "));

        if(event.getEventTime()!=null){

            document.add(new Paragraph("Event Time", heading));
            document.add(new Paragraph(event.getEventTime(), normal));
            document.add(new Paragraph(" "));

        }

        document.add(new Paragraph("Tickets", heading));
        document.add(new Paragraph(String.valueOf(booking.getTickets()), normal));

        document.add(new Paragraph(" "));

        document.add(new Paragraph("Amount", heading));
        document.add(new Paragraph("Rs. " + booking.getTotalAmount(), normal));

        document.add(new Paragraph(" "));

        document.add(new Paragraph("Booking Date", heading));
        document.add(new Paragraph(booking.getBookingDate(), normal));

        document.add(new Paragraph(" "));

        document.add(new Paragraph("Payment Status", heading));
        document.add(new Paragraph("SUCCESS", green));

        document.add(new Paragraph(" "));
        document.add(qr);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Scan this QR Code at the event entrance", heading));

        document.add(new Paragraph(" "));
        document.add(new Paragraph("=============================================="));

        document.add(new Paragraph("Thank you for booking with Anthara!", heading));

        document.add(new Paragraph("Enjoy your event!", normal));
        
        document.close();

        return output.toByteArray();
    }
}
        