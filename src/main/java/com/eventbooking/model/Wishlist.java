package com.eventbooking.model;

public class Wishlist {

    private int wishlistId;
    private int userId;
    private int eventId;
    private String addedOn;

    public Wishlist() {
    }

    public Wishlist(int wishlistId, int userId, int eventId, String addedOn) {

        this.wishlistId = wishlistId;
        this.userId = userId;
        this.eventId = eventId;
        this.addedOn = addedOn;

    }

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
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

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

}