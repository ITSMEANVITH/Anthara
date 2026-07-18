package com.eventbooking.model;

public class Booking {

    private int bookingId;
    private int userId;
    private int eventId;
    private int tickets;
    private double totalAmount;
    private String bookingDate;
    private String status;

    public Booking() {
    }

    public Booking(int bookingId,
                   int userId,
                   int eventId,
                   int tickets,
                   double totalAmount,
                   String bookingDate,
                   String status) {

        this.bookingId = bookingId;
        this.userId = userId;
        this.eventId = eventId;
        this.tickets = tickets;
        this.totalAmount = totalAmount;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}