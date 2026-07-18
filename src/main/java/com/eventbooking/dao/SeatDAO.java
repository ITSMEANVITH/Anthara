package com.eventbooking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eventbooking.model.Seat;
import com.eventbooking.util.DBConnection;

public class SeatDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public ArrayList<Seat> getSeatsByEvent(int eventId){

        ArrayList<Seat> list = new ArrayList<>();

        try{

            con = DBConnection.getConnection();

            ps = con.prepareStatement(
                    "SELECT * FROM seats WHERE event_id=? ORDER BY seat_number");

            ps.setInt(1,eventId);

            rs = ps.executeQuery();

            while(rs.next()){

                Seat seat = new Seat();

                seat.setSeatId(rs.getInt("seat_id"));
                seat.setEventId(rs.getInt("event_id"));
                seat.setSeatNumber(rs.getString("seat_number"));
                seat.setStatus(rs.getString("status"));
                seat.setBookingId(rs.getInt("booking_id"));

                list.add(seat);

            }

        }catch(Exception e){

            e.printStackTrace();

        }

        return list;

    }
 // Book Selected Seats
    public void bookSeats(int eventId, String seatNumbers, int bookingId){

        try{

            con = DBConnection.getConnection();

            String seats[] = seatNumbers.split(",");

            for(String seat : seats){

                ps = con.prepareStatement(
                "UPDATE seats SET status='BOOKED', booking_id=? WHERE event_id=? AND seat_number=?");

                ps.setInt(1, bookingId);
                ps.setInt(2, eventId);
                ps.setString(3, seat.trim());

                ps.executeUpdate();

            }

        }catch(Exception e){

            e.printStackTrace();

        }

    }
 // Check if Seat Already Booked
    public boolean isSeatBooked(int eventId,String seatNumber){

        try{

            con=DBConnection.getConnection();

            ps=con.prepareStatement(
            "SELECT * FROM seats WHERE event_id=? AND seat_number=? AND status='BOOKED'");

            ps.setInt(1,eventId);
            ps.setString(2,seatNumber);

            rs=ps.executeQuery();

            return rs.next();

        }catch(Exception e){

            e.printStackTrace();

        }

        return false;

    }

}