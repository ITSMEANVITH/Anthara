package com.eventbooking.model;

public class Event {

    private int eventId;
    private String eventName;
    private String eventDate;
    private String eventTime;
    private String venue;
    private double ticketPrice;
    private int availableSeats;
    private String eventImage;
    private String category;
    private boolean bookingOpen;
    private String homeSection;

    public Event() {
    }

    public Event(int eventId, String eventName, String eventDate,
                 String eventTime, String venue,
                 double ticketPrice, int availableSeats,
                 String eventImage, String category) {

        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.venue = venue;
        this.ticketPrice = ticketPrice;
        this.availableSeats = availableSeats;
        this.eventImage = eventImage;
        this.category = category;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public boolean isBookingOpen() {
        return bookingOpen;
    }

    public void setBookingOpen(boolean bookingOpen) {
        this.bookingOpen = bookingOpen;
    }
    public String getHomeSection() {
        return homeSection;
    }

    public void setHomeSection(String homeSection) {
        this.homeSection = homeSection;
    }
}