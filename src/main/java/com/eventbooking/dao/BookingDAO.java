package com.eventbooking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;



import com.eventbooking.model.Booking;
import com.eventbooking.util.DBConnection;

public class BookingDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // Insert Booking and Return Booking ID
    public int bookTicket(Booking booking) {

        int bookingId = -1;

        try {

            con = DBConnection.getConnection();

            String sql =
            		"INSERT INTO bookings(user_id,event_id,tickets,total_amount) VALUES(?,?,?,?)";

            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, booking.getUserId());
            ps.setInt(2, booking.getEventId());
            ps.setInt(3, booking.getTickets());
            ps.setDouble(4, booking.getTotalAmount());
            int rows = ps.executeUpdate();

            if (rows > 0) {

                ResultSet generatedKeys = ps.getGeneratedKeys();

                if (generatedKeys.next()) {
                    bookingId = generatedKeys.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookingId;
    }

    // View User Bookings
    public ArrayList<Booking> getBookingsByUser(int userId) {

        ArrayList<Booking> list = new ArrayList<>();

        try {

            con = DBConnection.getConnection();

            String sql = "SELECT * FROM bookings WHERE user_id=?";

            ps = con.prepareStatement(sql);

            ps.setInt(1, userId);

            rs = ps.executeQuery();

            while (rs.next()) {

                Booking booking = new Booking();

                booking.setBookingId(rs.getInt("booking_id"));
                booking.setUserId(rs.getInt("user_id"));
                booking.setEventId(rs.getInt("event_id"));
                booking.setTickets(rs.getInt("tickets"));
                booking.setTotalAmount(rs.getDouble("total_amount"));
                booking.setBookingDate(rs.getString("booking_date"));
               
                booking.setStatus(rs.getString("status"));
                
                System.out.println("DB STATUS = " + rs.getString("status"));
                System.out.println("BOOKING STATUS = " + booking.getStatus());

                list.add(booking);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
 // Get Booking By Booking ID
    public Booking getBookingById(int bookingId) {

        Booking booking = null;

        try {

            con = DBConnection.getConnection();

            String sql = "SELECT * FROM bookings WHERE booking_id=?";

            ps = con.prepareStatement(sql);

            ps.setInt(1, bookingId);

            rs = ps.executeQuery();

            if (rs.next()) {

                booking = new Booking();

                booking.setBookingId(rs.getInt("booking_id"));
                booking.setUserId(rs.getInt("user_id"));
                booking.setEventId(rs.getInt("event_id"));
                booking.setTickets(rs.getInt("tickets"));
                booking.setTotalAmount(rs.getDouble("total_amount"));
                booking.setBookingDate(rs.getString("booking_date"));
             
                booking.setStatus(rs.getString("status"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return booking;
    }
 // Total Bookings
    public int getTotalBookings() {

        int total = 0;

        try {

            con = DBConnection.getConnection();

            String sql = "SELECT COUNT(*) FROM bookings";

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            if (rs.next()) {

                total = rs.getInt(1);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return total;

    }
 // Total Revenue
    public double getTotalRevenue() {

        double revenue = 0;

        try {

            con = DBConnection.getConnection();

            String sql =
            		"SELECT SUM(total_amount) FROM bookings WHERE status='CONFIRMED'";
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            if (rs.next()) {

                revenue = rs.getDouble(1);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return revenue;

    }
 // View All Bookings
    public ArrayList<Booking> getAllBookings() {

        ArrayList<Booking> list = new ArrayList<>();

        try {

            con = DBConnection.getConnection();

            String sql = "SELECT * FROM bookings ORDER BY booking_id DESC";

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {

                Booking booking = new Booking();

                booking.setBookingId(rs.getInt("booking_id"));
                booking.setUserId(rs.getInt("user_id"));
                booking.setEventId(rs.getInt("event_id"));
                booking.setTickets(rs.getInt("tickets"));
                booking.setTotalAmount(rs.getDouble("total_amount"));
                booking.setBookingDate(rs.getString("booking_date"));
               
                booking.setStatus(rs.getString("status"));

                list.add(booking);

            }

        } catch(Exception e) {

            e.printStackTrace();

        }

        return list;

    }
 // Admin Booking Details
    public ResultSet getBookingDetails() {

        try {

            con = DBConnection.getConnection();

            String sql =
            		"SELECT " +
            		"b.booking_id, " +
            		"u.full_name, " +
            		"e.event_name, " +
            		"b.tickets, " +
            		"b.total_amount, " +
            		"b.booking_date, " +
            		"COALESCE(p.payment_method,'-') AS payment_method, " +
            		"COALESCE(p.payment_status,'PENDING') AS payment_status " +
            		"FROM bookings b " +
            		"JOIN users u ON b.user_id=u.user_id " +
            		"JOIN events e ON b.event_id=e.event_id " +
            		"LEFT JOIN (" +
            		"SELECT payment_id,booking_id,payment_method,payment_status " +
            		"FROM payments p1 " +
            		"WHERE payment_id=(" +
            		"SELECT MAX(payment_id) FROM payments p2 WHERE p2.booking_id=p1.booking_id" +
            		")" +
            		") p ON b.booking_id=p.booking_id " +
            		"ORDER BY b.booking_id DESC";

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            return rs;

        } catch(Exception e) {

            e.printStackTrace();

        }

        return null;

    }
    public ArrayList<Double> getMonthlyRevenue(){

        ArrayList<Double> revenue=new ArrayList<>();

        try{

            con=DBConnection.getConnection();

            String sql =
            		"SELECT MONTH(booking_date),SUM(total_amount) " +
            		"FROM bookings " +
            		"WHERE status='CONFIRMED' " +
            		"GROUP BY MONTH(booking_date)";
            ps=con.prepareStatement(sql);

            rs=ps.executeQuery();

            double months[]=new double[12];

            while(rs.next()){

                months[rs.getInt(1)-1]=rs.getDouble(2);

            }

            for(int i=0;i<12;i++){

                revenue.add(months[i]);

            }

        }catch(Exception e){

            e.printStackTrace();

        }

        return revenue;

    }
 // Cancel Booking
    public boolean cancelBooking(int bookingId){

        boolean status=false;

        try{

            con=DBConnection.getConnection();

            String sql=
            "UPDATE bookings SET status='CANCELLED' WHERE booking_id=?";

            ps=con.prepareStatement(sql);

            ps.setInt(1,bookingId);

            status=ps.executeUpdate()>0;

        }catch(Exception e){

            e.printStackTrace();

        }

        return status;

    }
    
    public ArrayList<Integer> getMonthlyBookings(){

        ArrayList<Integer> bookings = new ArrayList<>();

        try{

            con = DBConnection.getConnection();

            String sql =
            "SELECT MONTH(booking_date), COUNT(*) " +
            "FROM bookings " +
            "WHERE status='CONFIRMED' " +
            "GROUP BY MONTH(booking_date)";

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            int months[] = new int[12];

            while(rs.next()){

                months[rs.getInt(1)-1] = rs.getInt(2);

            }

            for(int i=0;i<12;i++){

                bookings.add(months[i]);

            }

        }catch(Exception e){

            e.printStackTrace();

        }

        return bookings;

    }
    public int getPendingBookings(){

        int total = 0;

        try{

            con = DBConnection.getConnection();

            String sql =
            "SELECT COUNT(*) FROM bookings WHERE status='CANCELLED'";

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            if(rs.next()){

                total = rs.getInt(1);

            }

        }catch(Exception e){

            e.printStackTrace();

        }

        return total;

    }
    public int getSuccessfulBookings(){

        int total = 0;

        try{

            con = DBConnection.getConnection();

            String sql =
            "SELECT COUNT(*) FROM bookings WHERE status='CONFIRMED'";

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            if(rs.next()){

                total = rs.getInt(1);

            }

        }catch(Exception e){

            e.printStackTrace();

        }

        return total;

    }
    
    
}