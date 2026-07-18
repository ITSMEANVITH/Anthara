package com.eventbooking.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.eventbooking.model.Event;

public class CalendarUtil {

    public static byte[] generateCalendarInvite(Event event) {

        try {

            LocalDate date =
                    LocalDate.parse(event.getEventDate());

            LocalTime time =
                    LocalTime.parse(event.getEventTime());

            LocalDateTime start =
                    LocalDateTime.of(date, time);

            // Default duration = 2 Hours
            LocalDateTime end =
                    start.plusHours(2);

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");

            StringBuilder ics = new StringBuilder();

            ics.append("BEGIN:VCALENDAR\r\n");
            ics.append("VERSION:2.0\r\n");
            ics.append("PRODID:-//Anthara//Event Booking//EN\r\n");

            ics.append("BEGIN:VEVENT\r\n");

            ics.append("UID:")
               .append(System.currentTimeMillis())
               .append("@anthara.com\r\n");

            ics.append("SUMMARY:")
               .append(event.getEventName())
               .append("\r\n");

            ics.append("DESCRIPTION:Booked via Anthara\r\n");

            ics.append("LOCATION:")
               .append(event.getVenue())
               .append("\r\n");

            ics.append("DTSTART:")
               .append(start.format(formatter))
               .append("\r\n");

            ics.append("DTEND:")
               .append(end.format(formatter))
               .append("\r\n");

            ics.append("STATUS:CONFIRMED\r\n");

            ics.append("END:VEVENT\r\n");

            ics.append("END:VCALENDAR");

            return ics.toString().getBytes();

        }

        catch(Exception e){

            e.printStackTrace();

        }

        return null;

    }

}