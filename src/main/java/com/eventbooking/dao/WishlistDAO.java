package com.eventbooking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eventbooking.model.Event;
import com.eventbooking.util.DBConnection;

public class WishlistDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // Add Event to Wishlist
    public boolean addToWishlist(int userId, int eventId) {

        boolean status = false;

        try {

            con = DBConnection.getConnection();

            String sql =
            "INSERT INTO wishlist(user_id,event_id) VALUES(?,?)";

            ps = con.prepareStatement(sql);

            ps.setInt(1, userId);
            ps.setInt(2, eventId);

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return status;

    }

    // Remove Event
    public boolean removeFromWishlist(int userId, int eventId) {

        boolean status = false;

        try {

            con = DBConnection.getConnection();

            String sql =
            "DELETE FROM wishlist WHERE user_id=? AND event_id=?";

            ps = con.prepareStatement(sql);

            ps.setInt(1, userId);
            ps.setInt(2, eventId);

            status = ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return status;

    }

    // Check Already Added
    public boolean isWishlisted(int userId, int eventId) {

        try {

            con = DBConnection.getConnection();

            String sql =
            "SELECT * FROM wishlist WHERE user_id=? AND event_id=?";

            ps = con.prepareStatement(sql);

            ps.setInt(1, userId);
            ps.setInt(2, eventId);

            rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;

    }

    // View Wishlist
    public ArrayList<Event> getWishlist(int userId) {

        ArrayList<Event> list = new ArrayList<>();

        try {

            con = DBConnection.getConnection();

            String sql =
            "SELECT e.* FROM wishlist w " +
            "JOIN events e ON w.event_id=e.event_id " +
            "WHERE w.user_id=? ORDER BY w.added_on DESC";

            ps = con.prepareStatement(sql);

            ps.setInt(1, userId);

            rs = ps.executeQuery();

            while(rs.next()){

                Event event = new Event();

                event.setEventId(rs.getInt("event_id"));
                event.setEventName(rs.getString("event_name"));
                event.setEventDate(rs.getString("event_date"));
                event.setVenue(rs.getString("venue"));
                event.setTicketPrice(rs.getDouble("ticket_price"));
                event.setAvailableSeats(rs.getInt("available_seats"));

                list.add(event);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return list;

    }

}